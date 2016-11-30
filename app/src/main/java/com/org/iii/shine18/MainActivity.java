package com.org.iii.shine18;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    private SeekBar seekBar;
    private MyReceiver myReciver;
    private SoundPool sp;
    private static int sound1, sound2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = (SeekBar)findViewById(R.id.seekbar);
        //最多"4"筆同時
        sp = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
        sound1 = sp.load(this, R.raw.bubbs, 1);
        sound2 = sp.load(this, R.raw.wate, 1);
        //註冊
        myReciver = new MyReceiver();
        registerReceiver(myReciver,new IntentFilter("shine"));

        //可調seekBar
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b){
                    Intent it = new Intent(MainActivity.this,MyService.class);
                    it.putExtra("seekto",i);
                    startService(it);

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
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
        Intent it = new Intent(this, MyService.class);
        it.putExtra("isPause", true);
        startService(it);
    }
    public void stop(View v){
        Intent it = new Intent(this, MyService.class);
        stopService(it);
    }

    public void sound1(View v){
        sp.play(sound1,0.5f,0.5f,1,0,1);
    }
    public void sound2(View v){
        sp.play(sound2,0.5f,0.5f,1,0,1);
    }

    //
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
