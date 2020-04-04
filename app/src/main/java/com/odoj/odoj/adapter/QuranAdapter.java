package com.odoj.odoj.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.odoj.odoj.QuranActivity;
import com.odoj.odoj.R;
import com.odoj.odoj.db.QuranHelper;
import com.odoj.odoj.model.QuranModel;
import com.odoj.odoj.preference.AppPreference;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.odoj.odoj.QuranActivity.EXTRA_JUZ;
import static com.odoj.odoj.QuranActivity.EXTRA_JUZ_ID;
import static com.odoj.odoj.QuranActivity.EXTRA_SURAH;
import static com.odoj.odoj.QuranActivity.EXTRA_SURAH_ID;

public class QuranAdapter extends RecyclerView.Adapter<QuranAdapter.QuranViewHolder> {
    private ArrayList<QuranModel> models = new ArrayList<>();
    private Context context;
    private String quranIn;
    private int arabTextSize;
    private int translationTextSize;
    private int next;
    private boolean last;

    public QuranAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<QuranModel> getModels() {
        return models;
    }

    public void setModels(ArrayList<QuranModel> models) {
        this.models = models;
    }

    public String getQuranIn() {
        return quranIn;
    }

    public void setQuranIn(String quranIn) {
        this.quranIn = quranIn;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    @NonNull
    @Override
    public QuranViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_quran, viewGroup, false);
        return new QuranViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuranViewHolder quranViewHolder, int i) {
        AppPreference preference = new AppPreference(context);
        QuranModel quranModel = models.get(i);
        //Log.e("translation", quranModel.getTranslation());
        int lastAyat = getModels().size() - 1;
        int midAyat;

        if (getModels().size() % 2 == 0) {
            midAyat = getModels().size() / 2 - 1;
        } else {
            midAyat = getModels().size() / 2;
        }

        if (isLast()) {
            quranViewHolder.tvNext.setVisibility(View.INVISIBLE);
        } else {
            if (i == getItemCount() - 1) {
                quranViewHolder.tvNext.setVisibility(View.VISIBLE);
                quranViewHolder.tvNext.setOnClickListener(v -> {
                    if (getQuranIn().equals("surah")) {
                        Intent intent = new Intent(context, QuranActivity.class);
                        intent.putExtra(EXTRA_SURAH, true);
                        intent.putExtra(EXTRA_SURAH_ID, getNext());
                        context.startActivity(intent);
                        ((Activity)context).finish();

                    } else if (getQuranIn().equals("juz")) {
                        Intent intent = new Intent(context, QuranActivity.class);
                        intent.putExtra(EXTRA_JUZ, true);
                        intent.putExtra(EXTRA_JUZ_ID, getNext());
                        context.startActivity(intent);
                        ((Activity)context).finish();
                    }
                });
            } else {
                quranViewHolder.tvNext.setVisibility(View.INVISIBLE);
            }
        }

        if (quranModel == null) {
            QuranHelper quranHelper = new QuranHelper(context);
            quranViewHolder.tvSubTitle.setVisibility(View.VISIBLE);
            quranViewHolder.tvSubTitle.setText(quranHelper.getSurahById(models.get(i + 1).getSuratId()).getSurahText());
            quranViewHolder.tvQuran.setVisibility(View.GONE);
            quranViewHolder.tvTranslation.setVisibility(View.GONE);
            quranViewHolder.tvAyat.setVisibility(View.GONE);
            quranViewHolder.tvAyat.setBackground(null);
            quranViewHolder.ivItem.setVisibility(View.GONE);
            quranViewHolder.itemView.setOnClickListener(null);
        } else {
            quranViewHolder.tvSubTitle.setVisibility(View.GONE);
            quranViewHolder.tvQuran.setVisibility(View.VISIBLE);
            quranViewHolder.tvTranslation.setVisibility(View.VISIBLE);
            quranViewHolder.tvAyat.setVisibility(View.VISIBLE);
            quranViewHolder.tvQuran.setText(quranModel.getAyatText());
            quranViewHolder.tvQuran.setTextSize(TypedValue.COMPLEX_UNIT_SP, arabTextSize);
            quranViewHolder.tvTranslation.setText(quranModel.getTranslation());
            quranViewHolder.tvTranslation.setTextSize(TypedValue.COMPLEX_UNIT_SP, translationTextSize);
            quranViewHolder.tvAyat.setBackgroundResource(R.drawable.quran_num);
            quranViewHolder.ivItem.setVisibility(View.VISIBLE);
            if (preference.getLastRead() == (int) getItemId(i)) {
                quranViewHolder.tvAyat.setText(R.string.star);
            } else {
                quranViewHolder.tvAyat.setText(String.valueOf(quranModel.getVerseId()));
            }

            if (getQuranIn().equals("juz")) {
                if (i == midAyat) {
                    quranViewHolder.tvHalf.setVisibility(View.VISIBLE);
                } else {
                    quranViewHolder.tvHalf.setVisibility(View.INVISIBLE);
                }
            }

            quranViewHolder.itemView.setOnClickListener(v -> {
                preference.setLastRead((int) getItemId(i));
                notifyItemRangeChanged(0, getItemCount());

                String message = "Kamu telah menyelesaikan target harianmu!";

                Log.e("quran", "quran: " + getQuranIn() + " daily: " + preference.getDaily());
                if (getQuranIn().equals(preference.getDaily())) {
                    if (i == lastAyat) {
                        if (!preference.getDailyMessage().equals(message)) {
                            preference.setDailyMessage(message);
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }
                    }

                } else if (getQuranIn().equals("juz")) {
                    if (preference.getDaily().equals("setengah juz")) {
                        if (i >= midAyat || i == lastAyat) {
                            if (!preference.getDailyMessage().equals(message)) {
                                preference.setDailyMessage(message);
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            });
        }
    }

    public void setArabTextSize(int size) {
        arabTextSize = size;
        notifyDataSetChanged();
    }

    public void setTranslationTextSize(int size) {
        translationTextSize = size;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    public long getItemId(int position) {
        return models.get(position).getId();
    }

    public class QuranViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvQuran)
        TextView tvQuran;
        @BindView(R.id.tvTranslation)
        TextView tvTranslation;
        @BindView(R.id.tvAyat)
        TextView tvAyat;
        @BindView(R.id.tvSubTitle)
        TextView tvSubTitle;
        @BindView(R.id.ivItem)
        ImageView ivItem;
        @BindView(R.id.tvHalf)
        TextView tvHalf;
        @BindView(R.id.tvNext)
        TextView tvNext;

        public QuranViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
