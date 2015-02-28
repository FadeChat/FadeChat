package com.example.fadechat;

import com.rabbitmq.client.Channel;

import android.os.AsyncTask;

public class Send extends AsyncTask<String, Void, Void> {

	
	Channel channel;
	
	public Send(String exchange, String queue ){
		
		ServerInfo.EXCHANGE=exchange;
		ServerInfo.Queue=queue;
		this.channel=Consumer.consumer.getChannel();
	}
	
	@Override
	protected Void doInBackground(String... Message) {
		// TODO Auto-generated method stub
		try {

			
			
			String tmp = "";
			for (int i = 0; i < Message.length; i++)
				tmp += Message[i];

			channel.basicPublish(ServerInfo.EXCHANGE,ServerInfo.Queue, null,
					tmp.getBytes());
	

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		
		return null;
	}

}
