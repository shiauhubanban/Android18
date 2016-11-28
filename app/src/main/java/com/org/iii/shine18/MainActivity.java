package com.org.iii.shine18;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    private SeekBar seekBar;
    private MyReceiver myReciver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar = (SeekBar)findViewById(R.id.seekbar);

        //註冊
        myReciver = new MyReceiver();
        registerReceiver(myReciver,new IntentFilter("shine"));
    }

    //按返回鍵也可結束
    @Override
    public void finish() {
        Intent it = new Intent(this, MyService.class);
        stopService(it);

        //結束註冊
        unregisterReceiver(myReciver);

        super.finish();
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
    public class MyReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            int len = intent.getIntExtra("len",-1);
            int now = intent.getIntExtra("now",-1);
            if(len>0){
                //找到最大值
                seekBar.setMax(len);
            }
            if(now>0){
                //進度條
                seekBar.setProgress(now);
            }
        }
    }

}
