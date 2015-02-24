package com.example.fadechat;

public class Msg {
	
	public String exchange = "";
	public String routingKey = "";
	
	public static final int TYPE_RECEIVED = 0;
	public static final int TYPE_SENT=1;
	public String content;
	private int type;
	
	public Msg(String routingKey, String content) {
		this.routingKey = routingKey;
		this.content = content;
	}

	public Msg(String exchange, String routingKey, String content) {
		this.exchange = exchange;
		this.routingKey = routingKey;
		this.content = content;
	}

	public String toString() {
		if (exchange.length() > 0) {
			return String.format("Exchange='%s', Key='%s', '%s'", exchange,
					routingKey, content);
		} else {
			return String.format("Key='%s', '%s'", routingKey, content);
		}
	}
	public String getContent(){
		return content;
	}
	
	
	public void setType(int type) {
		this.type = type;
	}
	
	public int getType(){
		return type;
	}
	
	
}
