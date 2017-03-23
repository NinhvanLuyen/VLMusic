//package kenhlaptrinh.net.vlmusic.services_controller;
//
//import android.content.ComponentName;
//import android.content.ServiceConnection;
//import android.os.IBinder;
//import android.util.Log;
//
//import kenhlaptrinh.net.vlmusic.services.Bind_Services_Play_Controll;
//
///**
// * Created by ninhv on 1/6/2017.
// */
//
//public class Services_Connection_Spect {
//
//    private ServiceConnection serviceConnection;
//    private boolean isBound;
//    Bind_Services_Play_Controll bind_services_play_controll;
//
//    private Bind_Services_Play_Controll new_Connection() {
//
//        serviceConnection = new ServiceConnection() {
//            @Override
//            public void onServiceConnected(ComponentName name, IBinder service) {
//                Bind_Services_Play_Controll.MyBinder binder = (Bind_Services_Play_Controll.MyBinder) service;
//                bind_services_play_controll = binder.getServices();
//                Log.e("connect to services", "connected");
//                isBound = true;
//            }
//
//            @Override
//            public void onServiceDisconnected(ComponentName name) {
//
//            }
//        };
//        return bind_services_play_controll;
//    }
//
//
//    @Override
//    void onServiceConnected(ComponentName name, IBinder service);
//
//    @Override
//    void onServiceDisconnected(ComponentName name);
//}
