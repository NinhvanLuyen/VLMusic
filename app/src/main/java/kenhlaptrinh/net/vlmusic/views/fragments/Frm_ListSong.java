package kenhlaptrinh.net.vlmusic.views.fragments;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;
import kenhlaptrinh.net.vlmusic.R;
import kenhlaptrinh.net.vlmusic.unities.GlobalSong;
import kenhlaptrinh.net.vlmusic.models.setOnClickListSong;
import kenhlaptrinh.net.vlmusic.models.Song;
import kenhlaptrinh.net.vlmusic.services.Bind_Services_Play_Controll;
import kenhlaptrinh.net.vlmusic.views.activities.MainActivity;
import kenhlaptrinh.net.vlmusic.views.adapters.Adapter_ListSong;

/**
 * Created by ninhv on 12/23/2016.
 */

public class Frm_ListSong extends Fragment {
    @BindView(R.id.rev_list_song)
    RecyclerView rev_listSong;

    LinkedList<Song> list_song;
    private Bind_Services_Play_Controll bind_services_play_controll;
    private ServiceConnection myconnection;
    private boolean isbounder = false;
    private MainActivity activity;
    private BroadcastReceiver broadcastReceiver;
    private Adapter_ListSong adapter_listSong;
    private final String TAG =Frm_ListSong.class.getName();

    public Frm_ListSong() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (MainActivity) getActivity();
        list_song = activity.getData();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.frm_step1, container, false);
        ButterKnife.bind(this, v);
        adapter_listSong = new Adapter_ListSong(list_song, getActivity());
//        rev_listSong.setItemAnimator(new DefaultItemAnimator());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()) {

            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                LinearSmoothScroller smoothScroller = new LinearSmoothScroller(getActivity()) {

                    private static final float SPEED = 30f;// Change this value (default=25f)

                    @Override
                    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                        return SPEED / displayMetrics.densityDpi;
                    }

                };
                smoothScroller.setTargetPosition(position);
                startSmoothScroll(smoothScroller);
            }

        };
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rev_listSong.setLayoutManager(layoutManager);
        rev_listSong.setNestedScrollingEnabled(false);
        rev_listSong.setAdapter(adapter_listSong);
        rev_listSong.setHasFixedSize(true);
        rev_listSong.setItemViewCacheSize(50);
        rev_listSong.setDrawingCacheEnabled(true);
        rev_listSong.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        myconnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Bind_Services_Play_Controll.MyBinder binder = (Bind_Services_Play_Controll.MyBinder) service;
                bind_services_play_controll = binder.getServices();
                Log.e(TAG,"connect to services connected");
                isbounder = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                isbounder = false;
                Log.e(TAG,"connect to services disconnect");

            }
        };
        startServices();
        broadcastReceiver = new BroadcastReceiver() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onReceive(Context context, Intent intent) {
                GlobalSong globalSong = GlobalSong.getInstance();
                if (globalSong.getPosition() != globalSong.getCurrent_player_list()) {
                    Message message = handler.obtainMessage(0);
                    handler.sendMessage(message);
                    globalSong.setCurrent_player_list(globalSong.getPosition());
                }
            }
        };
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter(Bind_Services_Play_Controll.MY_FILLTER));

        adapter_listSong.setSetOnClickListSong(new setOnClickListSong() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void setOnclickSong(int position) {
                Song song = list_song.get(position);
                GlobalSong globalSong = GlobalSong.getInstance();
                globalSong.setSong(song);
                globalSong.setPosition(position);
                playSong(song);
//                activity.changeBackground(song.getPath_album());
                adapter_listSong.notifyDataSetChanged();
            }
        });
        return v;
    }

    private void startServices() {
        Intent intent = new Intent(getActivity(), Bind_Services_Play_Controll.class);
        if (isbounder) {
            getActivity().unbindService(myconnection);
            getActivity().bindService(intent, myconnection, Context.BIND_AUTO_CREATE);
            isbounder = false;
        } else {
            getActivity().bindService(intent, myconnection, Context.BIND_AUTO_CREATE);
            isbounder = true;
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            adapter_listSong.notifyDataSetChanged();
        }
    };

    private void playSong(Song song) {
        bind_services_play_controll.playSong(song);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG, "stiop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        if (isbounder) {
//            getActivity().unregisterReceiver(broadcastReceiver);
//            isbounder= false;
//        }
        getActivity().unregisterReceiver(broadcastReceiver);
        getActivity().unbindService(myconnection);
        Log.e(TAG, "destroy");
        GlobalSong.removeInstance();
    }
}
