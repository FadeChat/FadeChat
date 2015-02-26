package com.example.fadechat;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import android.os.AsyncTask;

public class Send extends AsyncTask<String, Void, Void> {

	

	
	public Send(String exchange, String queue ){
		
		ServerInfo.EXCHANGE=exchange;
		ServerInfo.Queue=queue;
	}
	
	@Override
	protected Void doInBackground(String... Message) {
		// TODO Auto-generated method stub
		try {

			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(ServerInfo.HOST);

		
			factory.setUsername(ServerInfo.UserName);
			factory.setPassword(ServerInfo.PassWord);
			
			factory.setPort(ServerInfo.Port);
			
			Connection connection = factory.newConnection();
			
			Channel channel = connection.createChannel();
			
			
			channel.exchangeDeclare(ServerInfo.EXCHANGE, "fanout", true);
			channel.queueDeclare(ServerInfo.Queue, false, false, false, null);
			
			String tmp = "";
			for (int i = 0; i < Message.length; i++)
				tmp += Message[i];

			channel.basicPublish(ServerInfo.EXCHANGE,ServerInfo.Queue, null,
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
