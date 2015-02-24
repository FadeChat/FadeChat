package com.example.fadechat;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;








import java.util.Random;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	
	public static String HOST = "52.0.7.146";// RabbitMQ Server.
	
	
	public static String EXCHANGE = "logs";// exchange name
	public static String QUEUE = "123123213";// queue name	
	
	
	public final static String ClientId="mk";
	
	private ListView msgListView;

	private EditText inputText;		//메세지를 입력하는 텍스트

	private Button send;		//send 버튼 
	
	private MsgAdapter adapter;	//메세지 받는 변

	private List<Msg> msgList = new ArrayList<Msg>();	//어레이리스트로 생성한 메세지 리스
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_main);
		adapter = new MsgAdapter(MainActivity.this, R.layout.msg_item, msgList);
		
		

		
		inputText = (EditText) findViewById(R.id.input_text);
		send = (Button) findViewById(R.id.send);
		msgListView = (ListView) findViewById(R.id.msg_list_view);
		msgListView.setAdapter(adapter);
		
		
		send.setOnClickListener(new OnClickListener() {
			
			
			@Override
			public void onClick(View v) {
				
				String content = inputText.getText().toString();
				if (!"".equals(content)) {
					//타입은 sent 로 잡는다. (메세지 보내기)
					Msg msg = new Msg(EXCHANGE,"",ClientId+"///Minkyu///"+content);
					msg.setType(Msg.TYPE_SENT);
					
				    new Send(EXCHANGE,QUEUE).execute(content);
					
					
					msgList.add(msg);	//메세지 추가
					adapter.notifyDataSetChanged();
					msgListView.setSelection(msgList.size());
					inputText.setText("");
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
