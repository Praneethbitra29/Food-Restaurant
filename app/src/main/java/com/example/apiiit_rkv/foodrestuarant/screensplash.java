package com.example.apiiit_rkv.foodrestuarant;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class screensplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_screensplash);
        getSupportActionBar().hide();
        LogoLauncher logoLauncher = new LogoLauncher();
        logoLauncher.start();
    }


    private class LogoLauncher extends Thread{

        public void run(){
            try{
                sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }
            Intent intent = new Intent(screensplash.this, MainActivity.class);
            startActivity(intent);
            screensplash.this.finish();
        }

    }
}
