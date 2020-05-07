package com.faresa.alquran.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.faresa.alquran.R;
import com.faresa.alquran.model.AsmaulHuzna.DataItem;

import java.util.List;

public class AsmaulHuznaAdapter extends RecyclerView.Adapter<AsmaulHuznaAdapter.AsmaulViewHolder> {
    private Context context;
    private List<DataItem> dataItemList;

    public AsmaulHuznaAdapter(Context context, List<DataItem> dataItemList) {
        this.context = context;
        this.dataItemList = dataItemList;
    }

    @NonNull
    @Override
    public AsmaulHuznaAdapter.AsmaulViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_asmaul_huzna,parent,false);
        return new AsmaulViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AsmaulHuznaAdapter.AsmaulViewHolder holder, int position) {

        DataItem dataItem = dataItemList.get(position);
        holder.arab.setText(dataItem.getName());
        holder.indo.setText(dataItem.getTransliteration());
    }

    @Override
    public int getItemCount() {
        return dataItemList.size();
    }

    public class AsmaulViewHolder extends RecyclerView.ViewHolder {
        TextView arab,indo;
        public AsmaulViewHolder(@NonNull View itemView) {
            super(itemView);
            arab = itemView.findViewById(R.id.tv_arab);
            indo = itemView.findViewById(R.id.tv_indo);
        }
    }
}
