package kenhlaptrinh.net.vlmusic.views.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import kenhlaptrinh.net.vlmusic.R;

public class PlashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plash_screen);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    finish();
                    startActivity(new Intent(PlashScreen.this, MainActivity.class));
//                    overridePendingTransition(R.anim.anim_slide_in_right,R.anim.anim_slide_out_left);
                }
            }, 3000);


    }
}
