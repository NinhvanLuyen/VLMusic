package kenhlaptrinh.net.vlmusic.views.fragments;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.zelory.compressor.Compressor;
import kenhlaptrinh.net.vlmusic.R;
import kenhlaptrinh.net.vlmusic.unities.GlobalSong;
import kenhlaptrinh.net.vlmusic.models.Song;
import kenhlaptrinh.net.vlmusic.unities.Unities;
import kenhlaptrinh.net.vlmusic.services.Bind_Services_Play_Controll;

/**
 * Created by ninhv on 12/23/2016.
 */

public class Frm_Player extends Fragment {

    @BindView(R.id.tv_name_song)
    TextView tv_name_song;
    @BindView(R.id.tv_name_artis)
    TextView tv_name_artis;
    @BindView(R.id.tv_next_time)
    TextView tv_next_time;
    @BindView(R.id.tv_pass_time)
    TextView tv_pass_time;
    @BindView(R.id.seekBar)
    SeekBar seekBar;
    @BindView(R.id.im_album_images)
    ImageView im_album;

    private BroadcastReceiver broadcastReceiver;
    private Bind_Services_Play_Controll bind_services_play_controll;
    private ServiceConnection serviceConnection;
    private boolean isbound = false;
    private Intent intent;
    private Unities unities;
    private Song song;
    private final String TAG = Frm_Player.class.getName();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.frm_step2, container, false);
        ButterKnife.bind(this, v);
        //intent services
        intent = new Intent(getActivity(), Bind_Services_Play_Controll.class);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
//                if (isbound) {
//                    bind_services_play_controll.seekTo(seekBar.getProgress());
//                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (isbound) {
                    bind_services_play_controll.seekTo(seekBar.getProgress());
                }

            }
        });


        //connection
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {

                Bind_Services_Play_Controll.MyBinder myBinder = (Bind_Services_Play_Controll.MyBinder) service;
                bind_services_play_controll = myBinder.getServices();
                isbound = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                isbound = false;
            }
        };
        startServices();
        unities = new Unities();
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                int current = intent.getIntExtra("seek_value", 0);
                int maxvalue = intent.getIntExtra("max_time", 0);
                String maxvl = unities.milliSecondsToTimer(maxvalue);
                String now = unities.milliSecondsToTimer(current);
                tv_pass_time.setText("" + now);
                tv_next_time.setText("" + maxvl);
                seekBar.setMax(maxvalue);
                seekBar.setProgress(current);
                if (GlobalSong.getInstance().getCurrent_player() != GlobalSong.getInstance().getPosition()) {
                    Message message = handler.obtainMessage(1, 1);
                    handler.sendMessage(message);
                    GlobalSong.getInstance().setCurrent_player(GlobalSong.getInstance().getPosition());
                }
            }
        };
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter(Bind_Services_Play_Controll.MY_FILLTER));
        return v;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            updateView();
            Log.e(TAG, "Player_Handler nhan messages");
        }
    };

    private void updateView() {
        GlobalSong globalSong = GlobalSong.getInstance();
        song = globalSong.getSong();
        if (song != null) {
            tv_name_song.setText(song.getName());
            tv_name_artis.setText(song.getArt());
            String path = song.getPath_album();
            if (path != null) {
                File file = new File(path);
// Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
//                long fileSizeInKB = file.length() / 1024;
//                if (fileSizeInKB > 50) {
//                    Bitmap compressToFile = new Compressor.Builder(getContext())
//                            .setMaxWidth(400)
//                            .setMaxHeight(400)
//                            .setQuality(80)
//                            .setCompressFormat(Bitmap.CompressFormat.WEBP)
//                            .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
//                                    Environment.DIRECTORY_PICTURES).getAbsolutePath())
//                            .build().compressToBitmap(file);
//                    im_album.setImageBitmap(compressToFile);
//                    im_album.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                } else {
                Uri uri = Uri.fromFile(file);
                Glide.with(getActivity()).load(uri)
                        .centerCrop().into(im_album);
//                }

            } else {
                im_album.setImageResource(R.drawable.icon_cd);
            }
        } else {
            im_album.setImageResource(R.drawable.icon_cd);
            tv_name_artis.setText("...");
            tv_name_song.setText("...");
            tv_pass_time.setText("...");
            tv_next_time.setText("...");
        }

    }

    private void startServices() {
        Intent intent = new Intent(getActivity(), Bind_Services_Play_Controll.class);
        if (isbound) {
            getActivity().unbindService(serviceConnection);
            getActivity().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
            isbound = false;
        } else {
            getActivity().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
            isbound = true;
        }
    }

    public Frm_Player() {
        // Required empty public constructor
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        Log.e(TAG,"Destroy");
//        if (isbound) {
//            getActivity().unregisterReceiver(broadcastReceiver);
//            isbound = false;
//        }
        getActivity().unregisterReceiver(broadcastReceiver);
        getActivity().unbindService(serviceConnection);
    }
}
