package com.example.fadechat;

import java.util.List;

public class MsgRemover extends Thread{
	private int startNumber;  		//삭제 시작할 번호
	private List<Msg> arr;
	
	MsgRemover(List<Msg> arr){
		this.arr = arr;
		startNumber=0;
	}
	
	public void run() {
	
		startNumber = arr.size()-1;
		arr.remove(startNumber);
	}		
		
	
		
		
}
	