package com.example.fadechat;

public class Msg {
	
	public String exchange = "";
	public String routingKey = "";
	
	public static final int TYPE_RECEIVED = 0;				//final로 메세지를 받을때는 0으로 설정
	public static final int TYPE_SENT=1;					//final로 메세지를 보낼때는 1로 설정
	

	
	private String content;					//내용을 가져올 변수
	
	private int type;						
	private int timer;						//fade기능을 위해 제한시간 기능을 넣은 변수
	
	public Msg(){							//기본 메시지는 시간을 0으로 지정
		this.timer =0;
	}

	

	public Msg(String routingKey, String content) {
		this.routingKey = routingKey;
		this.content = content;
	}

	public Msg(String exchange, String routingKey, String content) {
		this.exchange = exchange;
		this.routingKey = routingKey;
		this.content = content;
	}

	//timer의 getter setter 메서드
	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}

	//toString 메서드 오버라이드
	public String toString() {
		if (exchange.length() > 0) {
			return String.format("Exchange='%s', Key='%s', '%s'", exchange,
					routingKey, content);
		} else {
			return String.format("Key='%s', '%s'", routingKey, content);
		}
	}
	
	//내용을 갖고오는 컨텐트 getter 메서드
	public String getContent(){
		return content;
	}
	
	//type을 갖고오는 getter, setter 메서드
	public void setType(int type) {
		this.type = type;
	}
	
	public int getType(){
		return type;
	}
	
	
}
