package kenhlaptrinh.net.vlmusic.views.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.LinkedList;

import id.zelory.compressor.Compressor;
import kenhlaptrinh.net.vlmusic.R;
import kenhlaptrinh.net.vlmusic.models.setOnClickListSong;
import kenhlaptrinh.net.vlmusic.models.Song;
import kenhlaptrinh.net.vlmusic.unities.GlobalSong;

/**
 * Created by ninhv on 12/31/2016.
 */

public class Adapter_ListSong  extends RecyclerView.Adapter<Adapter_ListSong.ViewHolder>{

private LinkedList<Song> m_listSong;
    private Activity activity;
    private setOnClickListSong setOnClickListSong;




    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView tv_name_Song;
        TextView tv_art;
        TextView tv_timeduration;
        ImageView imageView;
        LinearLayout ll_item_song;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_name_Song = (TextView) itemView.findViewById(R.id.name_song);
            tv_art = (TextView) itemView.findViewById(R.id.name_art);
            tv_timeduration = (TextView) itemView.findViewById(R.id.time_duration);
            ll_item_song = (LinearLayout) itemView.findViewById(R.id.item_song);
            imageView = (ImageView) itemView.findViewById(R.id.hinhanh);
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
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tv_name_Song.setText(m_listSong.get(position).getName());
        holder.tv_art.setText(m_listSong.get(position).getArt());
        holder.tv_timeduration.setText(m_listSong.get(position).getDuration());
        GlobalSong globalSong =GlobalSong.getInstance();
        if (globalSong.getPosition() ==position)
        {
            holder.ll_item_song.setBackgroundResource(R.color.YellowGreen_50);
        }
        else {
            holder.ll_item_song.setBackgroundResource(R.color.Black_50);
        }
        if (m_listSong.get(position).getPath_album()!=null) {
            File file =new File(m_listSong.get(position).getPath_album());
//            long fileSizeInKB = file.length() / 1024;
//            if (fileSizeInKB > 50) {
//                Bitmap compressToFile = new Compressor.Builder(activity)
//                        .setMaxWidth(50)
//                        .setMaxHeight(50)
//                        .setQuality(10)
//                        .setCompressFormat(Bitmap.CompressFormat.WEBP)
//                        .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
//                                Environment.DIRECTORY_PICTURES).getAbsolutePath())
//                        .build().compressToBitmap(file);
//                holder.imageView.setImageBitmap(compressToFile);
//                holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            }else {

                Uri uri = Uri.fromFile(file);
                Glide.with(activity).load(uri).crossFade().placeholder(R.drawable.icon_cd).centerCrop().into(holder.imageView);
//            }
        }
//        if (m_listSong.get(position).getPath_album()!=null) {
//            Bitmap bitmap = BitmapFactory.decodeFile(m_listSong.get(position).getPath_album());
//            holder.imageView.setImageBitmap(bitmap);
//        }
        else {
            holder.imageView.setImageResource(R.drawable.icon_cd);
        }
        holder.ll_item_song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnClickListSong.setOnclickSong(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return m_listSong.size();
    }

    public void setSetOnClickListSong(setOnClickListSong setOnClickListSong) {
        this.setOnClickListSong = setOnClickListSong;
    }

}
