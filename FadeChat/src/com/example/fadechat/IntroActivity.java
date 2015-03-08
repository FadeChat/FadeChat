package com.example.fadechat;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
 
 
public class IntroActivity extends Activity {
    /** Called when the activity is first created. */


	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_fadechat);	//인트로 (로고)화면을 설정
        
 

        Handler handler = new Handler();		
        handler.postDelayed(new Runnable() {
            public void run() {
            	//intent 를 이용하여 현재 introActivity에서 MainActivity로 이동하는 객체생성

            	Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                //인텐트 실행(화면전환)

                startActivity(intent);
                finish();
            }
        }, 3000);		//타이머를 지정 (3초로 지정)   
    }
}