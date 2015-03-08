package com.example.fadechat;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_fadechat);
        
     
        //Button
        Button btnCall = (Button)findViewById(R.id.sendButton);
        btnCall.setOnClickListener(new Button.OnClickListener() {
	
        	//start 버튼 클릭시
	public void onClick(View v) {
		
		EditText userName = (EditText) findViewById(R.id.editText1);
		EditText roomName = (EditText) findViewById(R.id.editText2);
		
		//아이디 , 방정보, 큐 이름 입력받기. 
		ServerInfo.ClientId=userName.getText().toString();
		ServerInfo.EXCHANGE=roomName.getText().toString();
		ServerInfo.Queue=ServerInfo.ClientId;
		
		
		//클라이언트아이디 와 방이름(exchange)가 입력되지않았을시 메세지를 보냄.
		
		if("".equals(ServerInfo.ClientId) || "".equals(ServerInfo.EXCHANGE))
		{
			Toast.makeText(LoginActivity.this, "You must fill the blank",
		    Toast.LENGTH_LONG).show();
			

		}
		else
		{
			//둘다 입력이 제대로 들어왔을시 화면전환
			Intent intent = new Intent(LoginActivity.this, MainActivity.class);
			startActivity(intent);
			
		}
		
	}
    
    
        });
    
    }
}