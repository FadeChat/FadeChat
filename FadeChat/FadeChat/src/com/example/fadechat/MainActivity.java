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
import android.widget.ToggleButton;

public class MainActivity extends Activity {
	

	private ListView msgListView;

	private EditText inputText;		//메세지를 입력하는 텍스트

	private Button send;		//send 버튼 
	
	private MsgAdapter adapter;	//메세지 받는 변수

	public static List<Msg> msgList = new ArrayList<Msg>();	//어레이리스트로 생성한 메세지 리스트
	
	public static int not_fadeNumber=0;
	

	ToggleButton toggle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_main);
		adapter = new MsgAdapter(MainActivity.this, R.layout.msg_item, msgList);
		
		Recv receiver=new Recv();
		
		receiver.execute();
		
	
		
		MsgRemover remover=new MsgRemover();
		
		remover.setOnRemoveMessageHandler(new OnRemoveMessageHandler() {
			
			
			@Override
			public void OnRemoveMessage(List<Msg> rMsgs) {
				// TODO Auto-generated method stub
				
				for(Msg msg:rMsgs){
					
				      msgList.remove(msg);
				
					adapter.notifyDataSetChanged();
				    msgListView.setSelection(msgList.size()); 
				
				}
				
			
			}

			@Override
			public void OnRemoveTimer() {
				// TODO Auto-generated method stub
				adapter.notifyDataSetChanged();
			    msgListView.setSelection(msgList.size()); 
			}
			
			
			
		});
		
	
		
		
		remover.start();
		
		
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
					//타입은 sent 로 잡는다. (메세지 보내기)
					
					int timer=0;
					
					
					Msg msg = new Msg(ServerInfo.EXCHANGE,"",content);
					msg.setType(Msg.TYPE_SENT);
					
					

					if(toggle.isChecked())
						timer=5;

					
					
					msg.setTimer(timer);
					

				    new Send(ServerInfo.EXCHANGE,ServerInfo.Queue).execute(ServerInfo.ClientId+"///"+content+"///"+timer);
					
				   
				   
					
					msgList.add(msg);	//메세지 추가
					adapter.notifyDataSetChanged();
					msgListView.setSelection(msgList.size());
					inputText.setText("");
				}
			}
		});
		
		// register for messages
		Consumer.consumer.setOnReceiveMessageHandler(new OnReceiveMessageHandler() {
			
			@Override
			public void onReceiveMessage(byte[] message) {
				// TODO Auto-generated method stub
				
				String text = "";
				try {
					text = new String(message, "UTF8");
					
					StringTokenizer tokenizer = new StringTokenizer(text, "///"); 
				
					String id=tokenizer.nextToken();
					String word=tokenizer.nextToken();
					int timer= Integer.parseInt(tokenizer.nextToken());
					
					
					
					 if(timer==0)
			           not_fadeNumber++;
					  
					 
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
		
		
		
		
		
		
		
	/*
		inputText.setOnKeyListener(new OnKeyListener() {


			public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
			// If the event is a key-down event on the "enter" button
			if ((arg2.getAction() == KeyEvent.ACTION_DOWN)
					&& (arg1 == KeyEvent.KEYCODE_ENTER)) {
				// Perform action on key press
				String content =clientID + inputText.getText().toString();

				Msg msg = new Msg(content, Msg.TYPE_SENT);
				msgList.add(msg);	//메세지 추가
				adapter.notifyDataSetChanged();
				msgListView.setSelection(msgList.size());
				inputText.setText("");
				new send().execute(content);
				return true;
			}
			return false;
		}
	});
		*/
		
		
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

