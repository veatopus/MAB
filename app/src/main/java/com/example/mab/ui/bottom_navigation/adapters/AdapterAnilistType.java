package com.example.mab.ui.bottom_navigation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mab.R;
import com.example.mab.ui.bottom_navigation.models.DataModel;
import com.example.mab.ui.bottom_navigation.models.AnilistTypeModel;

import java.util.ArrayList;
import java.util.List;

public class AdapterAnilistType extends RecyclerView.Adapter<AdapterAnilistType.ViewHolder> {
    List<AnilistTypeModel> data;
    AdapterAnilistData.AfishaClickListener listener;
    GiveMeInformationNigga giveMeInformationNigga;

    public AdapterAnilistType(AdapterAnilistData.AfishaClickListener listener, GiveMeInformationNigga nigga) {
    data = new ArrayList<>();
    this.listener = listener;
    giveMeInformationNigga = nigga;
    }


    public void addData(List<AnilistTypeModel> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.item_anilist_type, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(data.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private RecyclerView recyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            recyclerView = itemView.findViewById(R.id.recyclerview);
        }

        void onBind(AnilistTypeModel model, AdapterAnilistData.AfishaClickListener listener){
            title.setText(model.getTitle());
            List<DataModel> data = model.getData();
            AdapterAnilistData adapterAnilistData = new AdapterAnilistData(data, listener);
            recyclerView.setAdapter(adapterAnilistData);
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (holder.getAdapterPosition() == data.size()-1){
            giveMeInformationNigga.updateInfo();
        }
    }

    public interface GiveMeInformationNigga{
        void updateInfo();
    }
}
