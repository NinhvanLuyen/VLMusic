package kenhlaptrinh.net.vlmusic.models;

import android.net.Uri;

/**
 * Created by ninhv on 1/4/2017.
 */

public interface Click_Controll_Player {

    void nextSongFromUri(Uri uri);
    void previousSongFromUri(Uri uri);
    void stop();
    void pause();
}
