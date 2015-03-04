package com.example.fadechat;

import android.os.AsyncTask;

public class Recv extends AsyncTask<Void, Void, Void> {


	// 백그라운드작업으로 Consumer통해서 메세지를 받아오면 UI수정 
	@Override
	protected Void doInBackground(Void... arg0) {
		// TODO Auto-generated method stub
		
		
		// 서버와 연결되면 쓰레드 시작 
		if(Consumer.consumer.connectServer())
			Consumer.consumer.start();
		
		
		
		return null;
	}
	

	
	
}