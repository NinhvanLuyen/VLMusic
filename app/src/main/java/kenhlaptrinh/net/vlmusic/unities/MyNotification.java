package kenhlaptrinh.net.vlmusic.unities;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.NotificationTarget;

import java.io.File;

import kenhlaptrinh.net.vlmusic.R;
import kenhlaptrinh.net.vlmusic.models.Song;
import kenhlaptrinh.net.vlmusic.services_controller.MyReceiver;

/**
 * Created by ninhv on 3/17/2017.
 */

public class MyNotification {

    private final IntentFilter intenfillter;
    private Service activity;
    //    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    private Notification notification;
    private NotificationTarget notificationTarget;
    private Handler_notifi handler_notifi;
    public static final String my_fillter = "my_fillter";
    public static final String myClick_play = "myClick_play";
    public static final String myClick_next = "myClick_next";
    public static final String myClick_prev = "myClick_prev";

    public MyNotification(Service activity, final Handler_notifi handler_notifi) {
        this.activity = activity;
        this.handler_notifi = handler_notifi;
        intenfillter = new IntentFilter(my_fillter);
        intenfillter.addAction(myClick_prev);
        intenfillter.addAction(myClick_play);
        intenfillter.addAction(myClick_next);
        activity.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(MyNotification.myClick_next)) {

                }
                if (intent.getAction().equals(MyNotification.myClick_play)) {
                    Log.e("play", "play from notification");
                }
                if (intent.getAction().equals(MyNotification.myClick_prev)) {
                    Log.e("Prev", "Prev from notification");
                }
            }
        }, intenfillter);
    }

    public Notification showNotifi(Song song) {

        RemoteViews remoteViews = new RemoteViews(activity.getPackageName(),
                R.layout.item_song_notification);
        remoteViews.setTextViewText(R.id.tv_name_song, song.getName());

        Intent it_play = new Intent();
        it_play.setAction(myClick_play);
        PendingIntent pendingplay = PendingIntent.getBroadcast(activity, 0, it_play, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent it_next = new Intent();
        it_next.setAction(myClick_next);
        PendingIntent pendingNext = PendingIntent.getBroadcast(activity, 0, it_next, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent it_prev = new Intent();
        it_prev.setAction(myClick_prev);
        PendingIntent pendingPrev = PendingIntent.getBroadcast(activity, 0, it_prev, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.btn_play, pendingplay);
        remoteViews.setOnClickPendingIntent(R.id.btn_next, pendingNext);
        remoteViews.setOnClickPendingIntent(R.id.btn_previous, pendingPrev);

        mBuilder = new NotificationCompat.Builder(activity)
                .setSmallIcon(R.drawable.icon_cd)
                .setCustomContentView(remoteViews);
        notification = mBuilder.build();
        notificationTarget = new NotificationTarget(
                activity,
                remoteViews, R.id.im_album_images,
                notification,
                1);
        if (song.getPath_album() != null) {
            Glide.with(activity).load(new File(song.getPath_album())).asBitmap().into(notificationTarget);
        } else {
            Glide.with(activity).load(R.drawable.icon_cd).asBitmap().into(notificationTarget);
        }

        return notification;

    }

    public interface Handler_notifi {
        void next(Song song);

        void nextprevourse(Song song);

        void play(Song song);

    }
}
