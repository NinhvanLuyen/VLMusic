package kenhlaptrinh.net.vlmusic.services_controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import kenhlaptrinh.net.vlmusic.unities.MyNotification;

/**
 * Created by ninhv on 3/18/2017.
 */

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("wtf", "Prev from notification");
    }
}
