package com.example.fadechat;

import java.io.IOException;

import android.os.Handler;



import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class Consumer {
	
	private Connection mConnection;   // AMQP를 이용하여 서버에 설치되어있는 RabbitMQ에 Connection 연결 
	private Channel mChannel;   // AMQP를 이용하여 서버에 설치되어있는 RabbitMQ에 Channel 연결 
	
	
	private Consumer(){};

	public Channel getChannel()   
	{
		return mChannel;
	}

	private QueueingConsumer mConsumer;   // Queue를 사용하는 Consumer
	private boolean running;     // 메세지를 받아오는 thread의 flag
	
	 private Handler mConsumeHandler = new Handler();    // 메세지를 받아오는 쓰레드를 실행시키기위한 핸들러 
	  private Handler mMessageHandler = new Handler();    // 쓰레드가 메세지를 받아오면 UI변경하기위한 핸들러 
	 
	
	public static Consumer consumer=new Consumer();    // 싱글톤 디자인으로 프로그램 consumer를 한개로 만든다.
	
	public static Consumer getConsumer(){
		
		if(consumer==null)
			consumer=new Consumer();
		
		return consumer;
		
	}
	
	private byte[] mLastMessage;   // 최근에 받아온 메세지 
	
					
	public interface OnReceiveMessageHandler{            // 쓰레드가 메세지를 받아오면 핸들러가 실행할 메세드를 정의한 인터페이스 
		  
	      public void onReceiveMessage(byte[] message);
	  };
	
	  
	  private OnReceiveMessageHandler mOnReceiveMessageHandler=null;
	  
	  // 인터페이스의 메세드를 구현한 것을 할당 
	  public void setOnReceiveMessageHandler(OnReceiveMessageHandler handler){
	      mOnReceiveMessageHandler = handler;
	  };

	  
	  // 메세지가왔을때 핸들러에 보낼 쓰레드 
	  final Runnable mReturnMessage = new Runnable() {
	      public void run() {
	          mOnReceiveMessageHandler.onReceiveMessage(mLastMessage);
	      }
	  };
	  
	  // 프로그램이 시작했을때 핸들러에보낼 메서드 
	  final Runnable mConsumeRunner = new Runnable() {
	      public void run() {
	          Consume();
	      }
	  };
	
	
	// 서버에 연결 
	public boolean connectServer(){
		
		if(mConnection!= null && mChannel.isOpen() )
	  		  return true;
	        try
	        {
	      	  ConnectionFactory factory = new ConnectionFactory();
	      	  factory.setHost(ServerInfo.HOST);
	            
	      	  	// 서버에 연결할 아이디, 비번 설정 
	            factory.setUsername(ServerInfo.UserName);
				factory.setPassword(ServerInfo.PassWord);
	            
				// connection,channel  연
	            mConnection = factory.newConnection();
	            mChannel = mConnection.createChannel();
	            
	           // "fanout"방식에 채팅방 생성 
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
		 
		 //서버에 연결되었으면 바인딩 

	         try {
	        	 // 큐 생성 
	        	 mChannel.queueDeclare(ServerInfo.Queue, false, false, false, null);
	     		 
	        	 // consumer 생성 및 channel연결 
	        	 
	        	 if(mChannel.getDefaultConsumer()==null)
	        	 { 
	     		 mConsumer= new QueueingConsumer(mChannel);
	     		 mChannel.basicConsume(ServerInfo.Queue, true, mConsumer);
	        	 }
	     		
	          } catch (IOException e) {
	              e.printStackTrace();
	             
	          }
	          
	          try {
	        	  
	        	  //큐바인딩 
				mChannel.queueBind(ServerInfo.Queue,ServerInfo.EXCHANGE, "");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	          // 메세지를 받아오는 쓰레드 실행을 위해 핸들러에 쓰레드를 보내줌 
	          running = true;
	          mConsumeHandler.post(mConsumeRunner);

	        
	     
	    
	  }
	
	// 메세지를 받아오는 쓰레드를 실행하는 메세드 
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
	                
	                      // 새로운 메세지가 오면 핸들러에 쓰레드를 보내서 UI변경 
	                      mMessageHandler.post(mReturnMessage);
	                    
	                  } catch (InterruptedException ie) {
	                      ie.printStackTrace();
	                  }
	               }
	           }
	      };
	      
	      thread.start();

	  }
	
	
	// 연결종료  
	 public void dispose()
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
