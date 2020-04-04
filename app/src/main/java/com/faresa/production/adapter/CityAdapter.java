package com.faresa.production.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.faresa.production.db.CityHelper;
import com.faresa.production.model.city.CityModel;

import java.util.ArrayList;

public class CityAdapter extends BaseAdapter implements Filterable {
    private ArrayList<CityModel> cities = new ArrayList<>();
    private Context context;
    private CityHelper helper;

    public CityAdapter(Context context, CityHelper helper) {
        this.context = context;
        this.helper = helper;
    }

    @Override
    public int getCount() {
        return cities.size();
    }

    @Override
    public String getItem(int position) {
        return cities.get(position).getCityText();
    }

    @Override
    public long getItemId(int position) {
        /*return Long.parseLong(cities.get(position).getCityId());*/
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(android.R.layout.select_dialog_item, parent, false);
        }
        ((TextView) convertView.findViewById(android.R.id.text1)).setText(getItem(position));
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                return null;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (constraint != null) {
                    String query = constraint.toString().toUpperCase();
                    cities = helper.getCityByQuery(query);
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
    }
}
