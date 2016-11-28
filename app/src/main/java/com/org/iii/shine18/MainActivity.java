package com.org.iii.shine18;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void start(View v){
        Intent it = new Intent(this, MyService.class);
        startService(it);
    }
    public void pause(View v){

    }
    public void stop(View v){
        Intent it = new Intent(this, MyService.class);
        stopService(it);
    }
}
