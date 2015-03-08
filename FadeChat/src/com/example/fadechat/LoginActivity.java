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
	
	public void onClick(View v) {
		
		EditText userName = (EditText) findViewById(R.id.editText1);
		EditText roomName = (EditText) findViewById(R.id.editText2);
		
		ServerInfo.ClientId=userName.getText().toString();
		ServerInfo.EXCHANGE=roomName.getText().toString();
		ServerInfo.Queue=ServerInfo.ClientId;
		
		if("".equals(ServerInfo.ClientId) || "".equals(ServerInfo.EXCHANGE))
		{
			Toast.makeText(LoginActivity.this, "You must fill the blank",
		    Toast.LENGTH_LONG).show();
			

		}
		else
		{
			Intent intent = new Intent(LoginActivity.this, MainActivity.class);
			startActivity(intent);
			
		}
		
	}
    
    
        });
    
    }
}