package kenhlaptrinh.net.vlmusic.views.activities;


import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.zelory.compressor.Compressor;
import kenhlaptrinh.net.vlmusic.R;
import kenhlaptrinh.net.vlmusic.database.GetData;
import kenhlaptrinh.net.vlmusic.services.Controll_Receiver;
import kenhlaptrinh.net.vlmusic.unities.BlurBitmap;
import kenhlaptrinh.net.vlmusic.unities.BlurImages;
import kenhlaptrinh.net.vlmusic.unities.GlobalSong;
import kenhlaptrinh.net.vlmusic.models.Song;
import kenhlaptrinh.net.vlmusic.services.Bind_Services_Play_Controll;
import kenhlaptrinh.net.vlmusic.views.adapters.Adapter_ViewPagerFragment;
import kenhlaptrinh.net.vlmusic.views.fragments.Frm_ListSong;


public class MainActivity extends AppCompatActivity {


    @BindView(R.id.tv_name_song)
    TextView tv_name_song;
    @BindView(R.id.tv_name_artis)
    TextView tv_name_artis;

    @BindView(R.id.btn_next)
    ImageButton btn_next;
    @BindView(R.id.btn_play)
    ImageButton btn_play;
    @BindView(R.id.btn_previous)
    ImageButton btn_pre;
    @BindView(R.id.im_album_images)
    ImageView im_album;
    @BindView(R.id.item_song)
    LinearLayout linearLayout;
    @BindView(R.id.drawer_layout)
    CoordinatorLayout layout;
    @BindView(R.id.ll_detail)
    LinearLayout ll_detail;
    @BindView(R.id.ll_controll)
    LinearLayout ll_controll;
    @BindView(R.id.btn_favio)
    ImageButton btn_favio;
    @BindView(R.id.btn_repeat)
    ImageButton btn_repeat;


    private ViewPager viewPager;
    private PagerTabStrip pagerTabStrip;
    private LinkedList<Song> list_song;
    private Bitmap bm;
    private BitmapDrawable bitmapDrawable;
    //    private static final float SHAKE_THRESHOLD = 15.25f; // m/S**2
//    private static final int MIN_TIME_BETWEEN_SHAKES_MILLISECS = 1000;
//    private long mLastShakeTime;
//    private SensorManager mSensorMgr;

    private Controll_Receiver controll_receiver;
    private final String TAG =MainActivity.class.getName();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coodinator_main);
        ButterKnife.bind(this);
        setList_song();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        pagerTabStrip = (PagerTabStrip) findViewById(R.id.tab_layout);
        pagerTabStrip.setTextColor(getResources().getColor(R.color.White));
        pagerTabStrip.setTabIndicatorColor(getResources().getColor(R.color.White));
        pagerTabStrip.setTextSize(1, 14f);

        Adapter_ViewPagerFragment adapter_viewPagerFragment = new Adapter_ViewPagerFragment(getSupportFragmentManager(), 2);
        viewPager.setAdapter(adapter_viewPagerFragment);
        viewPager.setOffscreenPageLimit(3);
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (controll_receiver.isbound()) {
                    if (list_song.size() > 0) {
                        controll_receiver.pause(list_song.get(0));
                    }
                }
            }
        });
        btn_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controll_receiver.prevousSong();
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controll_receiver.nextSong();
            }
        });
        btn_repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean rp = loadData();
                if (rp) {
                    btn_repeat.setImageResource(R.drawable.ic_repeat_one_white_24dp);
                    saveData(false);
                } else {
                    btn_repeat.setImageResource(R.drawable.ic_repeat_white_24dp);
                    saveData(true);
                }
                controll_receiver.setRepeat(rp);
            }
        });
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (viewPager.getCurrentItem() == 1) {
                    ll_detail.setVisibility(View.GONE);
                    im_album.setVisibility(View.GONE);

                    btn_repeat.setVisibility(View.VISIBLE);
                    btn_favio.setVisibility(View.VISIBLE);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    ll_controll.setLayoutParams(layoutParams);
                } else {
                    ll_detail.setVisibility(View.VISIBLE);
                    im_album.setVisibility(View.VISIBLE);
                    btn_repeat.setVisibility(View.GONE);
                    btn_favio.setVisibility(View.GONE);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    ll_controll.setLayoutParams(layoutParams);
                }
            }
        });

        controll_receiver = new Controll_Receiver(this);
        controll_receiver.setView(this,tv_name_song, tv_name_artis, btn_play, im_album);
        controll_receiver.controll_ServiceConnection();
        controll_receiver.setHandler(handler);
        controll_receiver.setList_song(getData());


        registerReceiver(controll_receiver, new IntentFilter(Bind_Services_Play_Controll.MY_FILLTER));

//        mSensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Listen for shakes
//        Sensor accelerometer = mSensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        if (accelerometer != null) {
//            mSensorMgr.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
//        }
    }
//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
//    @Override
//    public void onSensorChanged(SensorEvent event) {
//        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
//            long curTime = System.currentTimeMillis();
//            if ((curTime - mLastShakeTime) > MIN_TIME_BETWEEN_SHAKES_MILLISECS) {
//
//                float x = event.values[0];
//                float y = event.values[1];
//                float z = event.values[2];
//
//                double acceleration = Math.sqrt(Math.pow(x, 2) +
//                        Math.pow(y, 2) +
//                        Math.pow(z, 2)) - SensorManager.GRAVITY_EARTH;
////                Log.e("lac", "Acceleration is " + acceleration + "m/s^2");
//
//                if (acceleration > SHAKE_THRESHOLD) {
//                    mLastShakeTime = curTime;
////                    nextSong();
//                }
//            }
//        }
//    }
//
//    @Override
//    public void onAccuracyChanged(Sensor sensor, int accuracy) {
//
//    }


    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String path = (String) msg.obj;
            setImageAlbumFloat(path);
            changeBackground(path);
            Log.e(TAG,"Handler nhan messages");
        }
    };

    public LinkedList<Song> getData() {
        return list_song;
    }

    public void setImageAlbumFloat(String path) {
        if (path != null) {
            File file = new File(path);
//                        Uri uri = Uri.fromFile(file);
//
//                        Picasso.with(MainActivity.this).load(uri)
//                                .centerCrop().fit().into(im_album);
//            long fileSizeInKB = file.length() / 1024;
//            if (fileSizeInKB > 50) {
//                Bitmap compressToFile = new Compressor.Builder(getApplicationContext())
//                        .setMaxWidth(100)
//                        .setMaxHeight(100)
//                        .setQuality(10)
//                        .setCompressFormat(Bitmap.CompressFormat.WEBP)
//                        .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
//                                Environment.DIRECTORY_PICTURES).getAbsolutePath())
//                        .build().compressToBitmap(file);
//                im_album.setImageBitmap(compressToFile);
//            } else {
//                Drawable drawable = Drawable.createFromPath(path);
//                im_album.setImageDrawable(drawable);
//            }

            Glide.with(this).load(file).crossFade().override(100,100).into(im_album);

        } else {
            im_album.setImageResource(R.drawable.icon_cd);
        }
    }

    public void changeBackground(String path) {
        BlurImages blurImages = new BlurImages();

        Log.e(TAG,"Changebackground changed");

        bm = BitmapFactory.decodeFile(path);
        Bitmap bm1 = blurImages.blurRenderScript(MainActivity.this, bm, 20);
//        Bitmap bm1 = new BlurBitmap(this).blur(bm);
        bitmapDrawable = new BitmapDrawable(bm1);
        if (bitmapDrawable != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                layout.setBackground(bitmapDrawable);
            }
        } else {
            layout.setBackgroundResource(R.color.Black_50);
        }
    }

    public void setList_song() {
        GetData getData = new GetData(this);
        this.list_song = getData.getMusic();

    }

    public SharedPreferences sharedPreferences;
    private boolean isrepeat;

    public void saveData(Boolean repeat) {
        sharedPreferences = getSharedPreferences("setting", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("repeat", repeat);
        editor.apply();
    }

    public boolean loadData() {
        sharedPreferences = getSharedPreferences("setting", 0);
        isrepeat = sharedPreferences.getBoolean("repeat", false);
        return isrepeat;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        controll_receiver.destroyMedia();
        unregisterReceiver(controll_receiver);

        GlobalSong.removeInstance();
        list_song =null;
        Log.e(TAG,"Destroy");

    }
}
