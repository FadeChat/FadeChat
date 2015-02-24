package com.example.fadechat;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import android.os.AsyncTask;

public class Send extends AsyncTask<String, Void, Void> {

	
	private String exchange;
	private String queue;
	
	public Send(String exchange, String queue ){
		
		this.exchange=exchange;
		this.queue=queue;
	}
	
	@Override
	protected Void doInBackground(String... Message) {
		// TODO Auto-generated method stub
		try {

			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("");

		
			factory.setUsername("");
			factory.setPassword("");
			
			factory.setPort(5672);
			
			Connection connection = factory.newConnection();
			
			Channel channel = connection.createChannel();
			
			
			channel.exchangeDeclare(exchange, "fanout", true);
			channel.queueDeclare(queue, false, false, false, null);
			
			String tmp = "";
			for (int i = 0; i < Message.length; i++)
				tmp += Message[i];

			channel.basicPublish(exchange,queue, null,
					tmp.getBytes());

			
			channel.close();
			
			connection.close();
			
			

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		
		return null;
	}

}
