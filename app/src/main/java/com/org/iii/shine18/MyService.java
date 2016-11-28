package com.org.iii.shine18;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    private MediaPlayer mp;


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
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(mp !=null&& !mp.isPlaying()){
            mp.start();
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
    }
}
