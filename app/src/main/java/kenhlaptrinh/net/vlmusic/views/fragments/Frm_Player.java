package kenhlaptrinh.net.vlmusic.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import kenhlaptrinh.net.vlmusic.R;
import kenhlaptrinh.net.vlmusic.models.HandlerButton;

/**
 * Created by ninhv on 12/23/2016.
 */

public class Frm_Player extends Fragment {

    private kenhlaptrinh.net.vlmusic.models.HandlerButton HandlerButton;
    public Frm_Player() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.frm_step2, container, false);
//        Button btn = (Button) v.findViewById(R.id.btn_next);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                HandlerButton.change(2);
//            }
//        });
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        HandlerButton =(HandlerButton)context;
    }
}
