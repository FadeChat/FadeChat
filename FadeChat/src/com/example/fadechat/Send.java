package com.example.fadechat;

import com.rabbitmq.client.Channel;

import android.os.AsyncTask;

public class Send extends AsyncTask<String, Void, Void> {

	
	Channel channel;
	
	// 지금은 1대 1 통신이지만 방이 여러개일경우 방과 큐를 변경하게만
	public Send(String exchange, String queue ){
		
		ServerInfo.EXCHANGE=exchange;
		ServerInfo.Queue=queue;
		this.channel=Consumer.consumer.getChannel();
	}
	
	// 백그라운드 작업으로 메세지를 보낸다. 여러개의 메세지를 작업가능하게 만듦 

	@Override
	protected Void doInBackground(String... Message) {
		// TODO Auto-generated method stub
		try {
			
			String tmp = "";
			for (int i = 0; i < Message.length; i++)
				tmp += Message[i];

			// 서버에 바이트단위로 쪼갠 메세지를 보낸다. 
			channel.basicPublish(ServerInfo.EXCHANGE,ServerInfo.Queue, null,
					tmp.getBytes());
	

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		
		return null;
	}

}
