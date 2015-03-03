package com.example.fadechat;

public class ServerInfo {
	
	public static final String HOST="52.0.7.146";				//rabbitmq 호스트
	public static final int Port=5672;						//통신을 위한 port번호


	public static final String ClientId="DW";				//User name 을 가질 String type 의 ID

	public static final String UserName="DM";				//Rabbitmq 의 username
	public static final String PassWord="DM";				//Rabbitmq 의 password
	

	public static  String EXCHANGE="room1";					//Server Exchange 이름

	public static  String Queue="DwQ";						//Server Queue 이름
	

	
}
