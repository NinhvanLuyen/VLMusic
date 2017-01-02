package kenhlaptrinh.net.vlmusic.views.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.Button;

import kenhlaptrinh.net.vlmusic.views.fragments.Frm_ListSong;
import kenhlaptrinh.net.vlmusic.views.fragments.Frm_Player;

/**
 * Created by ninhv on 12/19/2016.
 */

public class Adapter_ViewPagerFragment extends FragmentStatePagerAdapter {

    private int page_num;
    private Button btn_next;

    public Adapter_ViewPagerFragment(FragmentManager fm, int page_num) {
        super(fm);
        this.page_num = page_num;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Frm_ListSong frm_listSong = new Frm_ListSong();

                return frm_listSong;
            case 1:
                return new Frm_Player();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "Tracks";
            case 1:
                return "Player";
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return page_num;
    }


}
