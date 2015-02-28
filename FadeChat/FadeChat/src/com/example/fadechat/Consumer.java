package com.example.fadechat;

import java.io.IOException;

import android.os.Handler;
import android.util.Log;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class Consumer {
	
	private Connection mConnection;
	private Channel mChannel;
	

	public Channel getChannel() {
		return mChannel;
	}

	private QueueingConsumer mConsumer;
	private boolean running;
	
	  private Handler mMessageHandler = new Handler();
	  private Handler mConsumeHandler = new Handler();
	
	public static Consumer consumer=new Consumer();
	
	private byte[] mLastMessage;
	
	public interface OnReceiveMessageHandler{
		  
	      public void onReceiveMessage(byte[] message);
	  };
	
	  
	  private OnReceiveMessageHandler mOnReceiveMessageHandler;

	  public void setOnReceiveMessageHandler(OnReceiveMessageHandler handler){
	      mOnReceiveMessageHandler = handler;
	  };

	  
	  
	  final Runnable mReturnMessage = new Runnable() {
	      public void run() {
	          mOnReceiveMessageHandler.onReceiveMessage(mLastMessage);
	      }
	  };

	  final Runnable mConsumeRunner = new Runnable() {
	      public void run() {
	          Consume();
	      }
	  };
	
	
	
	private boolean connectServer(){
		
		if(mConnection!= null && mChannel.isOpen() )
	  		  return true;
	        try
	        {
	      	  ConnectionFactory factory = new ConnectionFactory();
	      	  factory.setHost(ServerInfo.HOST);
	            
	            factory.setUsername(ServerInfo.UserName);
				factory.setPassword(ServerInfo.PassWord);
	            
	            mConnection = factory.newConnection();
	            mChannel = mConnection.createChannel();
	            mChannel.exchangeDeclare(ServerInfo.EXCHANGE, "fanout", true);

	            return true;
	        }
	        catch (Exception e)
	        {
	      	  e.printStackTrace();
	            return false;
	        }
		
	}
	
	 public void start()
	  {
	     if(connectServer())
	     {

	         try {
	        	 mChannel.queueDeclare(ServerInfo.Queue, false, false, false, null);
	     		 

	     		 mConsumer= new QueueingConsumer(mChannel);
	     		 mChannel.basicConsume(ServerInfo.Queue, true, mConsumer);

	          } catch (IOException e) {
	              e.printStackTrace();
	             
	          }
	          
	          try {
				mChannel.queueBind(ServerInfo.Queue,ServerInfo.EXCHANGE, "");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	          running = true;
	          mConsumeHandler.post(mConsumeRunner);

	        
	     }
	    
	  }
	
	
	 private void Consume()
	  {
	      Thread thread = new Thread()
	      {

	           @Override
	              public void run() {
	               while(running){
	                  QueueingConsumer.Delivery delivery;
	                  try {
	                      delivery = mConsumer.nextDelivery();
	                      mLastMessage = delivery.getBody();
	                
	                      mMessageHandler.post(mReturnMessage);
	                     /* try {
	                          mChannel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
	                      } catch (IOException e) {
	                          e.printStackTrace();
	                      }*/
	                  } catch (InterruptedException ie) {
	                      ie.printStackTrace();
	                  }
	               }
	           }
	      };
	      
	      thread.start();

	  }
	
	
	
	 public void Dispose()
	    {
	        this.running = false;

				try {
					if (mConnection!=null)
						mConnection.close();
					if (mChannel != null)
						mChannel.abort();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	    }
	
	
	

}
