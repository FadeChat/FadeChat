package com.example.fadechat;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
 
 
public class IntroActivity extends Activity {
    /** Called when the activity is first created. */


	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_fadechat);	//인트로 (로고)화면을 설
        
 

        Handler handler = new Handler(){
        	public void handleMessage(Message msg){
        		super.handleMessage(msg);
        		startActivity(new Intent(IntroActivity.this,LoginActivity.class));
        		finish();
        	}
        };
        
        handler.sendEmptyMessageDelayed(0, 3000);
     
	}
}