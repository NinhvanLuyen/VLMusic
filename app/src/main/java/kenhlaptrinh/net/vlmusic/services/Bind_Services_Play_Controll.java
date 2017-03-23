package kenhlaptrinh.net.vlmusic.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import kenhlaptrinh.net.vlmusic.models.Song;
import kenhlaptrinh.net.vlmusic.unities.MyNotification;

public class Bind_Services_Play_Controll extends Service {

    private IBinder binder;
    private int lenght;
    private MediaPlayer mediaPlayer = null;
    private Handler myhandler;
    private Intent intentFilter;
    private final String TAG = Bind_Services_Play_Controll.class.getName();
    public static final String MY_FILLTER = "myfillter";
    private boolean isRepeat;
    private MyNotification myNotification;

    @Override
    public void onCreate() {
        super.onCreate();
        binder = new MyBinder();
        mediaPlayer = new MediaPlayer();
        intentFilter = new Intent(MY_FILLTER);
        myNotification = new MyNotification(this, new MyNotification.Handler_notifi() {
            @Override
            public void next(Song song) {
                next(song);
            }

            @Override
            public void nextprevourse(Song song) {
                nextprevourse(song);
            }

            @Override
            public void play(Song song) {
                playSong(song);
            }
        });

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        myhandler = new Handler();

        myhandler.postDelayed(runnable, 10);
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mediaPlayer.reset();
        return super.onUnbind(intent);
    }

    public void playSong(Song song) {
        String path = song.getPath_song();
        startForeground(1, myNotification.showNotifi(song));
        Log.e(TAG, path);

        Uri uri = Uri.parse(path);
        if (mediaPlayer.isPlaying() || mediaPlayer.getCurrentPosition() > 0) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer = null;
            mediaPlayer = MediaPlayer.create(this, uri);
            mediaPlayer.start();
            mediaPlayer.setLooping(isRepeat);
        } else {
            mediaPlayer = MediaPlayer.create(this, uri);
            mediaPlayer.start();
            mediaPlayer.setLooping(isRepeat);
        }

    }

    public void setRepeat(boolean repeat) {
        this.isRepeat = repeat;
    }

    public void pause(Song song) {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                lenght = mediaPlayer.getCurrentPosition();
                mediaPlayer.pause();
            } else {
                if (mediaPlayer.getCurrentPosition() == mediaPlayer.getDuration()) {
                    mediaPlayer.seekTo(0);
                    mediaPlayer.start();
                } else {
                    mediaPlayer.seekTo(lenght);
                    mediaPlayer.start();
                }
            }
        } else {
            playSong(song);
        }
    }

    public void repeat(boolean isRepeat) {
        setRepeat(isRepeat);
        mediaPlayer.setLooping(isRepeat);
    }

    public void seekTo(int lenght) {
        this.lenght = lenght;
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.seekTo(lenght);
                mediaPlayer.start();
            } else {
                mediaPlayer.seekTo(lenght);
                mediaPlayer.start();
                mediaPlayer.pause();
            }
        }
    }

    public void destroyMedia() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    @Override
    public void onDestroy() {
        Log.e("Destroy Services", "Destroyed");
    }

    private boolean issuccess;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer.isPlaying()) {
                int seek = mediaPlayer.getCurrentPosition();
                intentFilter.putExtra("seek_value", seek);
                intentFilter.putExtra("max_time", mediaPlayer.getDuration());
                intentFilter.putExtra("isplay", true);
            } else {
                intentFilter.putExtra("isplay", false);
            }
            if (!isRepeat) {
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        issuccess = true;
                    }
                });
                if (issuccess) {
                    intentFilter.putExtra("nextsong", true);
                    issuccess = false;
                } else {
                    intentFilter.putExtra("nextsong", false);
                }
            }
            sendBroadcast(intentFilter);
            myhandler.postDelayed(this, 10);
        }
    };




    public class MyBinder extends Binder {
        public Bind_Services_Play_Controll getServices() {
            return Bind_Services_Play_Controll.this;
        }
    }
}