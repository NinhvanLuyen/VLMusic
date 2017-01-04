package kenhlaptrinh.net.vlmusic.views.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import kenhlaptrinh.net.vlmusic.R;
import kenhlaptrinh.net.vlmusic.models.HandlerButton;

/**
 * Created by ninhv on 12/23/2016.
 */

public class Frm_Player extends Fragment {


    private kenhlaptrinh.net.vlmusic.models.HandlerButton HandlerButton;

    @BindView(R.id.tv_name_song)
    TextView tv_name_song;
    @BindView(R.id.tv_name_artis)
    TextView tv_name_artis;
    @BindView(R.id.tv_next_time)
    TextView tv_next_time;
    @BindView(R.id.tv_pass_time)
    TextView tv_pass_time;
    @BindView(R.id.btn_next)
    Button btn_next;
    @BindView(R.id.btn_play)
    Button btn_play;
    @BindView(R.id.btn_previous)
    Button btn_pre;
    @BindView(R.id.seekBar)
    SeekBar seekBar;
    @BindView(R.id.im_album_images)
    ImageView im_album;

    private Handler myhandler;
    GregorianCalendar gregorianCalendar;
    SimpleDateFormat sdateFormat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.frm_step2, container, false);

        sdateFormat = new SimpleDateFormat("HH:mm:ss");
        ButterKnife.bind(this, v);
        myhandler = new Handler();
        myhandler.postDelayed(runnable, 100);
        return v;
    }

    Runnable runnable = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void run() {
            gregorianCalendar = new GregorianCalendar();
            tv_name_song.setText(sdateFormat.format(gregorianCalendar.getTime()));
            myhandler.postDelayed(this, 100);
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        HandlerButton = (HandlerButton) context;
    }

    public Frm_Player() {
        // Required empty public constructor
    }
}
