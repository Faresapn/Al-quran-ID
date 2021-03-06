package com.faresa.alquran.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.faresa.alquran.R;
import com.faresa.alquran.model.QuranMurottal;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MurottalAdapter extends RecyclerView.Adapter<MurottalAdapter.MurottalViewHolder> {

    private Context context;
    private List<QuranMurottal> quranMurottals;
    public MurottalAdapter(Context context, List<QuranMurottal> quranMurottals) {
        this.context = context;
        this.quranMurottals = quranMurottals;
    }

    @NonNull
    @Override
    public MurottalAdapter.MurottalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_murottal,parent,false);
        return new MurottalViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MurottalAdapter.MurottalViewHolder holder, int position) {

        QuranMurottal quranMurottal = quranMurottals.get(position);
        holder.nomor.setText(quranMurottal.getNomor());
        holder.name.setText(quranMurottal.getNama());
        holder.play.setOnClickListener(v -> {
            final MediaPlayer mediaPlayer = MediaPlayer.create(holder.itemView.getContext(),Uri.parse(quranMurottal.getAudio()));
            mediaPlayer.start();
            holder.stop.setVisibility(View.VISIBLE);
            holder.play.setVisibility(View.INVISIBLE);
            holder.stop.setOnClickListener(v12 -> {
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    holder.play.setVisibility(View.VISIBLE);
                    holder.stop.setVisibility(View.INVISIBLE);
                }else {
                    mediaPlayer.start();
                    holder.stop.setVisibility(View.INVISIBLE);
                    holder.play.setVisibility(View.VISIBLE);

                }
            });
            mediaPlayer.setOnCompletionListener(mp -> {
                holder.seekBar.setProgress(0);
                holder.stop.setVisibility(View.INVISIBLE);
                holder.play.setVisibility(View.VISIBLE);
                holder.play.setOnClickListener(v1 -> {
                    if (mediaPlayer.isPlaying()){
                        mediaPlayer.stop();
                        holder.stop.setVisibility(View.VISIBLE);
                        holder.play.setVisibility(View.INVISIBLE);
                    }else {
                        mediaPlayer.start();
                        holder.seekBar.setProgress(0);
                        holder.stop.setVisibility(View.INVISIBLE);
                        holder.play.setVisibility(View.VISIBLE);
                    }
                });
            });
            holder.seekBar.setMax(mediaPlayer.getDuration());
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    holder.seekBar.setProgress(mediaPlayer.getCurrentPosition());
                }
            },0,1000);
            holder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser){
                        mediaPlayer.seekTo(progress);
                        holder.seekBar.setProgress(progress);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return quranMurottals.size();
    }

    public static class MurottalViewHolder extends RecyclerView.ViewHolder{
        SeekBar seekBar;
        Button play,stop;
        TextView name,nomor;
        public MurottalViewHolder(@NonNull View itemView) {
            super(itemView);
            nomor = itemView.findViewById(R.id.tv_nomor);
            name = itemView.findViewById(R.id.tv_name);
            stop = itemView.findViewById(R.id.bt_stop);
            play = itemView.findViewById(R.id.bt_play);
            seekBar = itemView.findViewById(R.id.seekbar_audio);
        }
    }
}
