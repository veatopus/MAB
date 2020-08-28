package com.example.mab.ui.bottom_navigation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mab.R;
import com.example.mab.ui.bottom_navigation.models.DataModel;

import java.util.List;

public class AdapterAnilistData extends RecyclerView.Adapter<AdapterAnilistData.ViwHolder> {
    List<DataModel> data;
    AfishaClickListener listener;
    AdapterAnilistType.GiveMeInformationNigga giveMeInformationNigga;

    public AdapterAnilistData(List<DataModel> dataModels, AfishaClickListener listener) {
        this.data = dataModels;
        this.listener = listener;
    }

    public void setGiveMeInformationNigga(AdapterAnilistType.GiveMeInformationNigga giveMeInformationNigga) {
        this.giveMeInformationNigga = giveMeInformationNigga;
    }

    public void addData(List<DataModel> dataModels) {
        data.addAll(dataModels);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViwHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViwHolder(inflater.inflate(R.layout.item_anilist_data, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViwHolder holder, int position) {
        holder.onBind(data.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViwHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (holder.getAdapterPosition() == data.size() - 1 && giveMeInformationNigga != null) {
            giveMeInformationNigga.updateInfo();
        }
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    public static class ViwHolder extends RecyclerView.ViewHolder {
        private DataModel model;
        private ImageView afisha;
        private TextView title;
        private Context context;
        private AfishaClickListener listener;

        public ViwHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            afisha = itemView.findViewById(R.id.image_view);
            title = itemView.findViewById(R.id.title);
            itemView.setOnClickListener(v -> listener.onAfishaClick(model.getId()));
        }

        void onBind(DataModel model, AfishaClickListener listener) {
            this.listener = listener;
            this.model = model;
            Glide
                    .with(context)
                    .load(model.getAfishaUrl())
                    .into(afisha);
            title.setText(model.getTitle());
        }

    }

    public interface AfishaClickListener {
        void onAfishaClick(int id);
    }
}