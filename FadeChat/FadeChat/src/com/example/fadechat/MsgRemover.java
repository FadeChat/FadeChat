package com.example.fadechat;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.util.Log;



public class MsgRemover {
	private int startNumber;  		        //삭제 시작할 번호 
	private List<Msg> rMsgs;  				//삭제할 메세지리스트 
	private List<Msg> arr;
	
	private Handler removingHandler=new Handler();
	
	public interface OnRemoveMessageHandler{
		  
	      public void OnRemoveMessage(List<Msg> rMsgs);
	      public void OnRemoveTimer();
	    	  
	 
	  };
	
	  private OnRemoveMessageHandler mOnRemoveMessageHandler;

	  public void setOnRemoveMessageHandler(OnRemoveMessageHandler handler){
	      mOnRemoveMessageHandler = handler;
	  };
	
	  
	final Runnable mRemoveMessage = new Runnable(){
		public void run(){
			mOnRemoveMessageHandler.OnRemoveMessage(rMsgs);
		}
		
	};
	
	final Runnable mRemoveTimer = new Runnable(){
		public void run(){
			mOnRemoveMessageHandler.OnRemoveTimer();
		}
		
	};
	
	
	
	MsgRemover(){
		arr=MainActivity.msgList;
		rMsgs=new ArrayList<Msg>();
		startNumber=0;
		
	}
	
   
	
	
	public void start(){
		
		final Thread thread = new Thread()
	      {
		
			public void run() {
		
		 while(true) {
			rMsgs.clear();
			
			
			Log.v("STARTNUMBER", "처음시작은 :"+MainActivity.not_fadeNumber);
			
			if(MainActivity.not_fadeNumber==arr.size())
				{ startNumber=MainActivity.not_fadeNumber;
				  
				}
			
			for(int cnt =startNumber; cnt < arr.size() ; cnt++) {
				
				
				
				
				int time=arr.get(cnt).getTimer();
				
				if(time > 0){
					
					if(time==1)
					{  
						
						rMsgs.add(arr.get(cnt));
						
						
					}
					else{
					
					arr.get(cnt).setTimer(time-1);	
					removingHandler.post(mRemoveTimer);
					
					}
				}
				
				
				
			} 
		  
			 removingHandler.post(mRemoveMessage);
			
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 } 
		 
	  } 
	 };
		
	
	 thread.start();
	 
	}	
	
	
		
}
	