package com.example.fadechat;

public class ServerInfo {
	
	public static final String HOST="52.0.7.146";				// 서버주소 
	public static final int Port=5672;						// AMQP 를 사용하는 포트주


	public static final String ClientId="MK";				//User name 을 가질 String type 의 ID

	public static final String UserName="DM";				//Rabbitmq 의 username
	public static final String PassWord="DM";				//Rabbitmq 의 password
	

	public static  String EXCHANGE="room1";					//Server Exchange 이름


	public static  String Queue="mkQ2";						//Server Queue 이름
	

	
}
