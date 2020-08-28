package com.example.mab.ui.bottom_navigation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mab.R;
import com.example.mab.ui.bottom_navigation.models.Cast;

import java.util.List;

public class AdapterTvShowCast extends RecyclerView.Adapter<AdapterTvShowCast.ViewHolder> {
    List<Cast> data;

    public AdapterTvShowCast(List<Cast> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cast, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void onBind(Cast cast){
            
        }
    }
}
