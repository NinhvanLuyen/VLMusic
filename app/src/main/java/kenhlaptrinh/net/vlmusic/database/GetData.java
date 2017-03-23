package kenhlaptrinh.net.vlmusic.database;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.util.LinkedList;

import id.zelory.compressor.Compressor;
import kenhlaptrinh.net.vlmusic.models.Song;
import kenhlaptrinh.net.vlmusic.unities.Unities;

/**
 * Created by ninhv on 1/10/2017.
 */

public class GetData {
    private Context context;


    public GetData(Context context) {
        this.context = context;
    }

    public LinkedList<Song> getMusic() {
        Cursor mCursor;
        mCursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ARTIST, "(" + MediaStore.Audio.Media.DURATION + ")ASC", MediaStore.Audio.Media.ALBUM_KEY, "(" + MediaStore.Audio.Media.DATA + ")ASC", MediaStore.Audio.Media.ALBUM_ID, MediaStore.Audio.Media._ID}, null, null,
                "" + MediaStore.Audio.Media.TITLE + "");
        int count = mCursor.getCount();
        LinkedList<Song> ls = new LinkedList<>();
        Song s;
        int i = 0;
        if (mCursor.moveToFirst()) {
            do {
                String arg2 = (mCursor.getString(2) != null) ? mCursor.getString(2) : "0";
                String arg3 = (mCursor.getString(4)) != null ? mCursor.getString(4) : "0";
                Integer arg6 = mCursor.getInt(6);

                s = new Song(mCursor.getString(0), mCursor.getString(1), new Unities().milliSecondsToTimer(Long.parseLong(arg2)), Long.parseLong(arg2), arg3, getAlbumImages(mCursor.getString(5)), arg6);
                ls.add(s);
                i++;
            } while (mCursor.moveToNext());
        }

        mCursor.close();
        return ls;
    }

    private String getAlbumImages(String album_id) {
        String path = "";
        Cursor cursor;
        cursor = context.getContentResolver().query(
                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                MediaStore.Audio.Albums._ID + " = ? ", new String[]{String.valueOf(album_id)}, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
            }
        }
        return (path == null) ? null : pathCongreess(path);
    }

    private String pathCongreess(String photoPath) {

        File file = new File(photoPath);
        long fileSizeInKB = file.length() / 1024;
        if (fileSizeInKB > 50) {
            File compressToFile = new Compressor.Builder(context)
                    .setMaxWidth(200)
                    .setMaxHeight(200)
                    .setQuality(100)
                    .setCompressFormat(Bitmap.CompressFormat.PNG)
                    .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES).getAbsolutePath())
                    .build().compressToFile(file);
            return compressToFile.getPath();
        }
        return photoPath;
    }
}
