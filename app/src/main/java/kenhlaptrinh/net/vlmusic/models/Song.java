package kenhlaptrinh.net.vlmusic.models;

import java.lang.ref.PhantomReference;

/**
 * Created by ninhv on 12/31/2016.
 */
public class Song {

    private String name;
    private String art;
    private String duration,path;
    private long time;

    public Song(String name, String art, String duration,long time, String path) {
        this.name = name;
        this.art = art;
        this.duration = duration;
        this.path =path;
        this.time =time;
    }


    public String getPath() {
        return path;
    }

    public Song(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
