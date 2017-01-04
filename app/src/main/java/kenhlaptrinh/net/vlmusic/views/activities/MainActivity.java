package kenhlaptrinh.net.vlmusic.views.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import kenhlaptrinh.net.vlmusic.R;
import kenhlaptrinh.net.vlmusic.models.HandlerButton;
import kenhlaptrinh.net.vlmusic.views.adapters.Adapter_ViewPagerFragment;


public class MainActivity extends AppCompatActivity implements HandlerButton {

    private ViewPager viewPager;
    private PagerTabStrip pagerTabStrip;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        pagerTabStrip = (PagerTabStrip) findViewById(R.id.tab_layout);
        pagerTabStrip.setTextColor(getResources().getColor(R.color.White));
        pagerTabStrip.setTabIndicatorColor(getResources().getColor(R.color.White));
        pagerTabStrip.setTextSize(1,14f);

        Adapter_ViewPagerFragment adapter_viewPagerFragment =new Adapter_ViewPagerFragment(getSupportFragmentManager(),2);
        viewPager.setAdapter(adapter_viewPagerFragment);
        viewPager.setOffscreenPageLimit(3);
    }

    @Override
    public void change(int position) {
        viewPager.setCurrentItem(position);
    }

    @Override
    public void setOnclickSong(int position) {

    }
}
