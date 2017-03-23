package kenhlaptrinh.net.vlmusic.services;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;

import kenhlaptrinh.net.vlmusic.R;
import kenhlaptrinh.net.vlmusic.models.Song;
import kenhlaptrinh.net.vlmusic.unities.GlobalSong;

/**
 * Created by ninhv on 1/19/2017.
 */

public class Controll_Receiver extends BroadcastReceiver {
    private TextView tv_name_song;
    private TextView tv_name_artis;
    private ImageButton btn_play;
    private ImageView im_album;
    private Song song;
    private Handler handler;
    private LinkedList<Song> list_song;
    private Bind_Services_Play_Controll bind_services_play_controll;
    private Context context;
    private boolean isbound;
    private Activity activity;
    private final String TAG =getClass().getName();


    public void setView(Activity activity,TextView tv_name_song, TextView tv_name_artis, ImageButton btn_play, ImageView im_album) {
        this.tv_name_song = tv_name_song;
        this.tv_name_artis = tv_name_artis;
        this.btn_play = btn_play;
        this.im_album = im_album;
        this.activity =activity;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void setList_song(LinkedList<Song> list_song) {
        this.list_song = list_song;
    }

    public void setIsbound(boolean isbound) {
        this.isbound = isbound;
    }

    //--end setget

    private ServiceConnection serviceConnection;
    public Controll_Receiver(Context context) {
        this.context = context;

    }

    //regist
    public ServiceConnection controll_ServiceConnection() {
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {

                Bind_Services_Play_Controll.MyBinder myBinder = (Bind_Services_Play_Controll.MyBinder) service;
                bind_services_play_controll = myBinder.getServices();
                setIsbound(true);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                setIsbound(false);
            }
        };
        startServices();
        return serviceConnection;

    }

    private void startServices() {
        Intent intent = new Intent(context, Bind_Services_Play_Controll.class);
        if (isbound()) {
            context.unbindService(serviceConnection);
            context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
            setIsbound(false);
        } else {
            context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
            setIsbound(true);
        }
    }

    //handle Receiver
    @Override
    public void onReceive(Context context, Intent intent) {

        boolean isplay = intent.getBooleanExtra("isplay", false);
//        Log.e(TAG,"isplay ---"+isplay);
        if (isplay) {
            btn_play.setImageResource(R.drawable.ic_pause_white_24dp);
        } else {
            btn_play.setImageResource(R.drawable.ic_play_arrow_white_24dp);
        }
        GlobalSong globalSong = GlobalSong.getInstance();
        if (globalSong.getPosition() != globalSong.getCurrent_position()) {
            Message message = handler.obtainMessage(1, globalSong.getSong().getPath_album());
            handler.sendMessage(message);
            globalSong.setCurrent_position(globalSong.getPosition());
        }

        if (globalSong.getSong() != null) {
            song = globalSong.getSong();
            setSong(song);

            tv_name_song.setText(song.getName());
            tv_name_artis.setText(song.getArt());
        } else {
            tv_name_song.setText("...");
            tv_name_artis.setText("...");
            im_album.setImageResource(R.drawable.icon_cd);
        }
        boolean action_NextSong = intent.getBooleanExtra("nextsong", false);
        if (action_NextSong) {
            nextSong();
        }
    }

    public void nextSong() {
        if (list_song.size() > 0) {
            GlobalSong globalSong = GlobalSong.getInstance();
            int position = globalSong.getPosition();
            if (position < list_song.size() - 1) {
                position = position + 1;
                globalSong.setPosition(position);
            }
            Song song = list_song.get(position);
            globalSong.setSong(song);
//            changeBackground(song.getPath_album());
            bind_services_play_controll.playSong(song);
        }
    }
    public void prevousSong() {
        if (list_song.size() > 0) {
            GlobalSong globalSong = GlobalSong.getInstance();
            int position = globalSong.getPosition();
            if (position > 0) {
                position = position - 1;
                globalSong.setPosition(position);
            }
            Song song = list_song.get(position);
            globalSong.setSong(song);
//         changeBackground(song.getPath_album());
            bind_services_play_controll.playSong(song);
        }
    }
    public void pause(Song song)
    {
        bind_services_play_controll.pause(song);
    }

    public void setRepeat(boolean rp)
    {
        bind_services_play_controll.repeat(rp);
    }

    public boolean isbound() {
        return isbound;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }
    public void destroyMedia()
    {
//        bind_services_play_controll.destroyMedia();

        context.unbindService(serviceConnection);
    }
}
