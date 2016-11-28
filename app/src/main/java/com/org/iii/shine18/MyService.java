package com.org.iii.shine18;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {
    private MediaPlayer mp;
    private Timer timer;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();


        mp = MediaPlayer.create(this, R.raw.cat);
        //得到長度
        int len = mp.getDuration();
        //Log.v("shine","len:" + len);
        Intent it = new Intent("shine");
        //放進去
        it.putExtra("len",len);
        //發送資料出去
        sendBroadcast(it);


        timer = new Timer();
        timer.schedule(new MyTask(),0,500);
    }

    private class MyTask extends TimerTask{
        @Override
        public void run() {
            if(mp !=null&&mp.isPlaying()){
                Intent it = new Intent("shine");
                //放進去
                it.putExtra("now",mp.getCurrentPosition());
                //發送資料出去
                sendBroadcast(it);

            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean isPause = intent.getBooleanExtra("isPause",false);
        if(mp !=null&& !mp.isPlaying()){
            if(!isPause) {
                mp.start();
            }
        }else if(mp!=null && mp.isPlaying()){
            if(isPause){
                mp.pause();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mp!=null){
            if(mp.isPlaying()){
                mp.stop();
            }
            mp.release();
            mp = null;
        }
        if (timer != null) {
            timer.purge();
            timer.cancel();
            timer = null;
        }
    }
}
