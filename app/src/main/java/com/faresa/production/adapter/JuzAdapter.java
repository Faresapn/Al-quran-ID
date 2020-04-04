package com.faresa.production.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.faresa.production.R;

import java.util.ArrayList;

public class JuzAdapter extends BaseAdapter {
    Context context;
    ArrayList<Integer> list;

    public JuzAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<Integer> getList() {
        return list;
    }

    public void setList(ArrayList<Integer> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Integer getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_juz, parent, false);
        }

        ((TextView) convertView.findViewById(R.id.tvJuzId)).setText(String.valueOf(getItem(position)));
        ((TextView) convertView.findViewById(R.id.tvJuz)).setText("Juz " + String.valueOf(getItem(position)));
        return convertView;
    }
}
