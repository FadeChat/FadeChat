package com.example.fadechat;


import java.util.ArrayList;
import java.util.List;





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

public class MainActivity extends Activity {
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
		
		initMsgs();

		
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
					Msg msg = new Msg(content, Msg.TYPE_SENT);
					msgList.add(msg);	//메세지 추가
					adapter.notifyDataSetChanged();
					msgListView.setSelection(msgList.size());
					inputText.setText("");
				}
			}
		});
	

	}
				
	private void initMsgs() {
		Msg msg1 = new Msg("Start Fade Chat", Msg.TYPE_RECEIVED);
		msgList.add(msg1);
		Msg msg2 = new Msg("Fade Chat 은 당신의 정보를 최우선으로 생각합니다.", Msg.TYPE_SENT);
		msgList.add(msg2);
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
