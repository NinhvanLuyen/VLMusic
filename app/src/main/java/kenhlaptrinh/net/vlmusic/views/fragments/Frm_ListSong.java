package kenhlaptrinh.net.vlmusic.views.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;
import kenhlaptrinh.net.vlmusic.R;
import kenhlaptrinh.net.vlmusic.models.HandlerButton;
import kenhlaptrinh.net.vlmusic.models.Song;
import kenhlaptrinh.net.vlmusic.models.Unities;
import kenhlaptrinh.net.vlmusic.views.adapters.Adapter_ListSong;

/**
 * Created by ninhv on 12/23/2016.
 */

public class Frm_ListSong extends Fragment {
    private kenhlaptrinh.net.vlmusic.models.HandlerButton HandlerButton;

    @BindView(R.id.rev_list_song)
    RecyclerView rev_listSong;
    LinkedList<Song> list_song;
    private Cursor mCursor ;


    public Frm_ListSong() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list_song =getMusic();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.frm_step1, container, false);
        ButterKnife.bind(this, v);

//        Button btn = (Button) v.findViewById(R.id.btn_next);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                HandlerButton.change(1);
//            }
//        });z
        Adapter_ListSong adapter_listSong = new Adapter_ListSong(list_song, getActivity());
        rev_listSong.setItemAnimator(new DefaultItemAnimator());
        rev_listSong.setLayoutManager(new LinearLayoutManager(getActivity()));
        rev_listSong.setAdapter(adapter_listSong);
        return v;

    }

    private LinkedList<Song> getMusic() {
       mCursor = getActivity().getContentResolver().query(
               MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Audio.Media.TITLE,MediaStore.Audio.Media.ARTIST,"("+MediaStore.Audio.Media.DURATION+")ASC",MediaStore.Audio.Media.ALBUM_KEY}, null, null,
               "" + MediaStore.Audio.Media.TITLE + "");

        int count = mCursor.getCount();

        LinkedList<Song >ls =new LinkedList<>();
        Song s ;
        int i = 0;
        if (mCursor.moveToFirst()) {
            do {

                String arg2 = mCursor.getString(2);
                s =new Song(mCursor.getString(0),mCursor.getString(1),new Unities().milliSecondsToTimer(Long.parseLong(arg2)));
                ls.add(s);
                i++;
            } while (mCursor.moveToNext());
        }

        mCursor.close();

        return ls;
    }

    @Override
    public void onStop() {
        super.onStop();
      Log.e("Stop","stiop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("Destroy","destroy");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        HandlerButton = (HandlerButton) context;
    }
}
