package com.example.mab.ui.bottom_navigation.adapters;

import android.annotation.SuppressLint;
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
import com.example.mab.ui.bottom_navigation.models.CharacterAniList;

import java.util.List;
import java.util.Objects;

public class AdapterAnilistCharacters extends RecyclerView.Adapter<AdapterAnilistCharacters.ViewHolder>{
    private List<CharacterAniList> data;
    private Context context;
    private OnCharacterClickListeter listener;

    public AdapterAnilistCharacters(List<CharacterAniList> data, Context context, OnCharacterClickListeter listener) {
        this.data = data;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_character, parent, false), listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(data.get(position), context);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textViewTitle, textViewRole, textViewFavorite;
        private CharacterAniList characterAniList;

        public ViewHolder(@NonNull View itemView, final OnCharacterClickListeter listener){
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view_cowboy);
            textViewTitle = itemView.findViewById(R.id.text_view_name);
            textViewRole = itemView.findViewById(R.id.text_view_role);
            textViewFavorite = itemView.findViewById(R.id.text_view_favorite);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onCharacterClick(characterAniList.getNode().id());
                }
            });
        }

        @SuppressLint("SetTextI18n")
        void onBind(CharacterAniList characterAniList, Context context){
            this.characterAniList = characterAniList;
            Glide
                    .with(context)
                    .load(Objects.requireNonNull(characterAniList.getNode().image()).large())
                    .into(imageView);
            textViewTitle.setText(Objects.requireNonNull(characterAniList.getNode().name()).full());
            textViewRole.setText(Objects.requireNonNull(characterAniList.getEdge().role()).rawValue());
            textViewFavorite.setText(Objects.requireNonNull(characterAniList.getNode().favourites()).toString());
        }
    }

    public interface OnCharacterClickListeter{
        void onCharacterClick(int id);
    }
}