package com.example.fadechat;

import android.os.AsyncTask;

public class Recv extends AsyncTask<String, Void, Void> {

	@Override
	protected Void doInBackground(String... params) {
		// TODO Auto-generated method stub
		
		Consumer.consumer.start();
		
		return null;
	}
	
	
	
}