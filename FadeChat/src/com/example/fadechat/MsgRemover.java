package com.example.fadechat;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;



public class MsgRemover {
	private int startNumber;  		        // 삭제 시작할 번호 
	private List<Msg> rMsgs;  				// 삭제할 메세지리스트 
	private List<Msg> arr;					// 채팅창에 보이는 전체 메세지들 
	
	private Handler removingHandler=new Handler();	// 메세지 삭제했을 때 와 제한시간 표시를 UI에 적용하기위한 핸들러 
	
	public interface OnRemoveMessageHandler{   // 메세지 삭제 및 제한시간 표시 를 위한 인터페이
		  
	      public void OnRemoveMessage(List<Msg> rMsgs);
	      public void OnRemoveTimer();
	    	  
	 
	  };
	
	  private OnRemoveMessageHandler mOnRemoveMessageHandler;

	  //인터페이스에 있는 메서드를 구현하기위한 메서드 
	  public void setOnRemoveMessageHandler(OnRemoveMessageHandler handler){
	      mOnRemoveMessageHandler = handler;
	  };
	
	  
	  // 메세지 삭제를 위한 쓰레드 
	final Runnable mRemoveMessage = new Runnable(){
		public void run(){
			mOnRemoveMessageHandler.OnRemoveMessage(rMsgs);
		}
		
	};
	
	// 타이머 표시를 위한 쓰레드 
	final Runnable mRemoveTimer = new Runnable(){
		public void run(){
			mOnRemoveMessageHandler.OnRemoveTimer();
		}
		
	};
	
	
	// 채팅방 정보가져오기 및 삭제할 데이터 초기화 
	MsgRemover(){
		arr=MainActivity.msgList;
		rMsgs=new ArrayList<Msg>();
		startNumber=0;
		
	}
	
   
	

	public void start(){
		
		// 메세지 삭제 및 제한시간 표시를 위한 쓰레드 시작 
		final Thread thread = new Thread()
	      {
		
			public void run() {
		
		 while(true) {
			//삭제할 메세지들을 담은 리스트를 초기화한다.	
			rMsgs.clear();
			
			
			// 지우지 않을 메세지 갯수 와 전체 메세지 갯수가 같으면 삭제를 시작할 인덱스를 바꾼다. 
			// (전체를 다 확인할 필요없이 지울것들만 확인하여 실행시간을 줄이기위해)
			if(MainActivity.not_fadeNumber==arr.size())
				startNumber=MainActivity.not_fadeNumber;
				  
				
			// 지울메세지들을 확인하여  메세지 삭제할 메세지를 리스트에 담거나 시간조정 .
			for(int cnt =startNumber; cnt < arr.size() ; cnt++) {
				
				int time=arr.get(cnt).getTimer();
				
				if(time > 0){
					
					if(time==1)
						rMsgs.add(arr.get(cnt));
					
					else{
					arr.get(cnt).setTimer(time-1);	
					removingHandler.post(mRemoveTimer);
					
					}
				}
				
				
				
			} 
			
		    // while 문이 한번 돌때마다 삭제할 메세지를 담은 리스트를 핸들러에 보내서 UI에 적용.
			 removingHandler.post(mRemoveMessage);
			
			 // 1초단위호 실행을 위해 쓰레드를 1초씩 멈추게한다. 
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
	