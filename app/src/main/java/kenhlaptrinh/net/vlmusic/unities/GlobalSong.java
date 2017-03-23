package kenhlaptrinh.net.vlmusic.unities;

import kenhlaptrinh.net.vlmusic.models.Song;

/**
 * Created by ninhv on 1/7/2017.
 */
public class GlobalSong {
    private Song song;
    private int position;
    private int current_position;
    private int current_player;
    private int current_player_list;
    private static GlobalSong globalSong ;
    public static synchronized GlobalSong getInstance() {

        if (globalSong !=null) {
            return globalSong;
        }else
        {
            globalSong =new GlobalSong();
            return globalSong;
        }
    }
    public static synchronized GlobalSong removeInstance() {

        if (globalSong != null) {
            return null;
        }
        return null;
    }

    public int getCurrent_player() {
        return current_player;
    }

    public int getCurrent_player_list() {
        return current_player_list;
    }

    public void setCurrent_player_list(int current_player_list) {
        this.current_player_list = current_player_list;
    }

    public void setCurrent_player(int current_player) {
        this.current_player = current_player;
    }

    public int getCurrent_position() {
        return current_position;
    }

    public void setCurrent_position(int current_position) {
        this.current_position = current_position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }
}
