package com.odoj.odoj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.odoj.odoj.R;
import com.odoj.odoj.model.SurahModel;

import java.util.ArrayList;

public class SurahAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<SurahModel> surahData;

    public SurahAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<SurahModel> getSurahData() {
        return surahData;
    }

    public void setSurahData(ArrayList<SurahModel> surahData) {
        this.surahData = surahData;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return surahData.size();
    }

    @Override
    public SurahModel getItem(int position) {
        return surahData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_surah, parent, false);
        }

        ((TextView) convertView.findViewById(R.id.tvSurah)).setText(getItem(position).getSurahText());
        ((TextView) convertView.findViewById(R.id.tvSurahId)).setText(String.valueOf(getItemId(position)));
        return convertView;
    }
}
