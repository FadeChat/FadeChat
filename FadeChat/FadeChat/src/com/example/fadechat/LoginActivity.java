package com.example.fadechat;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
		
		Intent intent = new Intent(LoginActivity.this, MainActivity.class);
		
		
		startActivity(intent);
	}
    
    
        });
    
    }
}