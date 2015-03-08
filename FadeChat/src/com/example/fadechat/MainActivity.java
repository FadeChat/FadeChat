package com.example.fadechat;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;



import java.util.StringTokenizer;

import com.example.fadechat.Consumer.OnReceiveMessageHandler;
import com.example.fadechat.MsgRemover.OnRemoveMessageHandler;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity {
	

	private ListView msgListView;

	private EditText inputText;		//메세지를 입력하는 텍스트

	private Button send;		//send 버튼 
	
	private MsgAdapter adapter;	//메세지 받는 변수

	public static List<Msg> msgList = new ArrayList<Msg>();	//어레이리스트로 생성한 메세지 리스트
	
	public static int not_fadeNumber=0;
	

	ToggleButton toggle;					//togglebutton 생성
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_main);				//메인엑티비티 레이아웃을 불러드림
		adapter = new MsgAdapter(MainActivity.this, R.layout.msg_item, msgList);
		
		Recv receiver=new Recv();    // 백그라운드에서 Consumer가 메세지를 받아오고 ui에 적용하기 위한 Recv 객체 
		
		receiver.execute();   // 실행시켜서 Consumer 객체에 있는 쓰레드 실행 
		
		//메인엑티비티 실행시 바로 fade chat service 라는 toast 설정
		/*Toast.makeText(MainActivity.this, "Copyright(c) 동욱,민규 All rights reserved!",
				Toast.LENGTH_LONG).show();
		 	*/
	
		
		MsgRemover remover=new MsgRemover();  // fade메세지 삭제를 위해 MsgRemover객체 생성 
		
		// MsgRemover에 있는 OnRemoveMessageHandler에 있는  interface 구현 
		remover.setOnRemoveMessageHandler(new OnRemoveMessageHandler() {
			
			
			@Override
			public void OnRemoveMessage(List<Msg> rMsgs) {
				// TODO Auto-generated method stub
				
				for(Msg msg:rMsgs){
					
				      msgList.remove(msg);  // 메세지 삭제 
				
					adapter.notifyDataSetChanged();
				    msgListView.setSelection(msgList.size()); 
				
				}
				
			
			}

			@Override  // 제한시간표시 
			public void OnRemoveTimer() {
				// TODO Auto-generated method stub
				adapter.notifyDataSetChanged();	
			    msgListView.setSelection(msgList.size()); 
			}
			
			
			
		});
	
		
		remover.start();  // Remover 실
		
		
		inputText = (EditText) findViewById(R.id.input_text);
		send = (Button) findViewById(R.id.send);
		msgListView = (ListView) findViewById(R.id.msg_list_view);
		msgListView.setAdapter(adapter);
		

		
		toggle = (ToggleButton) findViewById(R.id.toggleButton1);
      
	

		
		send.setOnClickListener(new OnClickListener() {
			
			
			@Override
			public void onClick(View v) {
				
				String content = inputText.getText().toString();
				if (!"".equals(content)) {
					
					
					int timer=0;   // 처음은 not fade로 설
					
					// 메세지 생성 
					Msg msg = new Msg(ServerInfo.EXCHANGE,"",content);
					
					//타입은 sent 로 잡는다. (메세지 보내기)
					msg.setType(Msg.TYPE_SENT);
					
					
					// fade가 선택되었을때 타이머를 9초로 설정 
					if(toggle.isChecked())
						timer=9;

					
					// 메세지에 시간적용 
					msg.setTimer(timer);
					
					// 방이름과 큐 정보를 보내고 ( 여러개 방 생성했을 때를 고려하여), 메세지에는 아이디,메세지내용,시간을 보낸다.  
				    new Send(ServerInfo.EXCHANGE,ServerInfo.Queue).execute(ServerInfo.ClientId+"///"+content+"///"+timer);

					
					msgList.add(msg);	//메세지 추가
					adapter.notifyDataSetChanged();
					msgListView.setSelection(msgList.size());
					inputText.setText("");
				}
			}
		});
		
		// 메세지가 왔을때 ui에 적용하기위해 consumer객체에 interface 내부에 메세드 구현 
		Consumer.consumer.setOnReceiveMessageHandler(new OnReceiveMessageHandler() {
			
			@Override
			public void onReceiveMessage(byte[] message) {
				// TODO Auto-generated method stub
				
				String text = "";
				try {
					text = new String(message, "UTF8");  // message를 유니코드로 변경 (byte 단위로 왔음으로)
					
					// Tokenizer를 생성하여 "///" 로 아이디, 메세지, 시간을 구분한다. 
					StringTokenizer tokenizer = new StringTokenizer(text, "///");  
				
					String id=tokenizer.nextToken();
					String word=tokenizer.nextToken();
					int timer= Integer.parseInt(tokenizer.nextToken());
					
					
					// fade 가 아닌 메세지 갯수 추가 (remover 사용을 위해)
					 if(timer==0)
			           not_fadeNumber++;
					  
					 // 받아온 메세지가 내 아이디랑 같으면 추가하지 않고 다르면 추
					if(!id.equals(ServerInfo.ClientId))
					{
					
					Msg msg = new Msg(ServerInfo.EXCHANGE,"",word);
					msg.setType(Msg.TYPE_RECEIVED);
					msg.setTimer(timer);
					
					
					
					msgList.add(msg);	//메세지 추가
					adapter.notifyDataSetChanged();
					msgListView.setSelection(msgList.size());
					}
					
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				
			}
		});
		
		
		

		
	}
				
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	
	
	
	
}

