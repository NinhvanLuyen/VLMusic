package kenhlaptrinh.net.vlmusic.models;

import java.io.Serializable;

/**
 * Created by ninhv on 12/31/2016.
 */
public class Song implements Serializable{

    private String name;
    private String art;
    private String duration, path_song,path_album;
    private long time;
    private int id;


    public Song(String name, String art, String duration,long time, String path_song,String path_album,int id) {
        this.name = name;
        this.art = art;
        this.duration = duration;
        this.path_song = path_song;
        this.time =time;
        this.path_album =path_album;
        this.id =id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getPath_album() {
        return path_album;
    }

    public void setPath_album(String path_album) {
        this.path_album = path_album;
    }

    public void setPath_song(String path_song) {
        this.path_song = path_song;
    }

    public String getPath_song() {
        return path_song;
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

    @Override
    public String toString() {
        return "Song{" +
                "name='" + name + '\'' +
                ", art='" + art + '\'' +
                ", duration='" + duration + '\'' +
                ", path_song='" + path_song + '\'' +
                ", path_album='" + path_album + '\'' +
                ", time=" + time +
                ", id=" + id +
                '}';
    }
}
