package kenhlaptrinh.net.vlmusic.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import kenhlaptrinh.net.vlmusic.views.fragments.Frm_ListSong;

public class Services_Play_Controll extends Service {

    private MediaPlayer mediaPlayer;
    private Handler myhandler;
    public static final String MY_FILLTER ="val_intent_fillter";
    private Intent intent_fillter;
    private final String TAG =Services_Play_Controll.class.getName();



    public Services_Play_Controll() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        intent_fillter =new Intent(MY_FILLTER);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent!=null) {
            int start_from =intent.getIntExtra("from",0); //from =1 : from listSong --from =2
            if (start_from ==1) {
                String path = intent.getStringExtra("path");
                playSong(path);
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void playSong(String path)
    {
        Uri uri =Uri.parse(path);
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
        }
        else
        {
            mediaPlayer.reset();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), (uri));
            mediaPlayer.start();
        }
        //-send messages to activity
        myhandler =new Handler();
        myhandler.postDelayed(runnable,100);
    }

    SimpleDateFormat simpleDateFormat =new SimpleDateFormat("HH:mm:ss");
    Runnable runnable =new Runnable() {
        @Override
        public void run() {

            GregorianCalendar gregorianCalendar =new GregorianCalendar();
            intent_fillter.putExtra("data", mediaPlayer.getCurrentPosition()+"");
            sendBroadcast(intent_fillter);
            myhandler.postDelayed(this,100);
        }
    };

    @Override
    public void onDestroy() {
        Log.e(TAG,"Destroyed");
        super.onDestroy();
    }
}
