package kenhlaptrinh.net.vlmusic.views.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;

import kenhlaptrinh.net.vlmusic.R;
import kenhlaptrinh.net.vlmusic.models.Song;

/**
 * Created by ninhv on 12/31/2016.
 */

public class Adapter_ListSong  extends RecyclerView.Adapter<Adapter_ListSong.ViewHolder>{

private LinkedList<Song> m_listSong;
    private Activity activity;


    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView tv_name_Song;
        TextView tv_art;
        TextView tv_timeduration;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_name_Song = (TextView) itemView.findViewById(R.id.name_song);
            tv_art = (TextView) itemView.findViewById(R.id.name_art);
            tv_timeduration = (TextView) itemView.findViewById(R.id.time_duration);
        }

    }


    public Adapter_ListSong(LinkedList<Song> m_listSong, Activity activity) {
        this.m_listSong = m_listSong;
        this.activity = activity;
    }

    @Override
    public Adapter_ListSong.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song,parent,false);
        Adapter_ListSong.ViewHolder viewHolder =new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_name_Song.setText(m_listSong.get(position).getName());
        holder.tv_art.setText(m_listSong.get(position).getArt());
        holder.tv_timeduration.setText(m_listSong.get(position).getDuration());
    }

    @Override
    public int getItemCount() {
        return m_listSong.size();
    }

}
