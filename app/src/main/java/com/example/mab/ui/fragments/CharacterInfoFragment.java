package com.example.mab.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.bumptech.glide.Glide;
import com.example.mab.App;
import com.example.mab.GetCharacterQuery;
import com.example.mab.GetLislAnimePagenationQuery;
import com.example.mab.MediaByIdQuery;
import com.example.mab.R;
import com.example.mab.SearchQuery;
import com.example.mab.data.AnilistService;
import com.example.mab.ui.bottom_navigation.adapters.AdapterAnilistData;
import com.example.mab.ui.bottom_navigation.models.DataModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CharacterInfoFragment extends Fragment implements AnilistService.AnilistCallback, AdapterAnilistData.AfishaClickListener {
    private ImageView imageViewImage, imageViewBanner;
    private TextView textViewFullName, textViewNativeName,
            textViewFavorite, textViewDescription, textViewSiteUrl;
    private RecyclerView recyclerView;
    private NavController navController;
    private int id;
    private ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        assert bundle != null;
        id = bundle.getInt("id");
        return inflater.inflate(R.layout.fragment_character_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialisation(view);
        App.getInstance().getAnilistService().getCharacterById(id, this);
        progressBar.setVisibility(View.VISIBLE);

    }
    private void initialisation(View view){
        imageViewImage = view.findViewById(R.id.image_view_iamage);
        imageViewBanner = view.findViewById(R.id.image_view_cowboy_banner);
        textViewFullName = view.findViewById(R.id.text_view_full_name);
        textViewNativeName = view.findViewById(R.id.text_view_native_name);
        textViewFavorite = view.findViewById(R.id.text_view_favorite);
        textViewDescription = view.findViewById(R.id.text_view_description);
        textViewSiteUrl = view.findViewById(R.id.text_view_site_url);
        recyclerView = view.findViewById(R.id.recyclerview_relations_anime);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        progressBar  = view.findViewById(R.id.progress_bar);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onResponseCharacter(@NotNull Response<GetCharacterQuery.Data> response) {
        final GetCharacterQuery.Character character = Objects.requireNonNull(response.getData()).Character();
        assert character != null;

        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Glide
                        .with(CharacterInfoFragment.this)
                        .load(Objects.requireNonNull(character.image()).large())
                        .into(imageViewImage);

                Glide
                        .with(CharacterInfoFragment.this)
                        .load(Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(character.media()).nodes()).get(0).bannerImage()))
                        .into(imageViewBanner);

                textViewFullName.setText(Objects.requireNonNull(character.name()).full());
                textViewNativeName.setText(Objects.requireNonNull(character.name()).native_());
                textViewFavorite.setText(Objects.requireNonNull(character.favourites()).toString());
                textViewDescription.setText(Html.fromHtml(character.description(), Html.FROM_HTML_MODE_COMPACT));
                textViewSiteUrl.setText(character.siteUrl());

                List<DataModel> dataShmata = new ArrayList<>();

                List<GetCharacterQuery.Node> nodes = Objects.requireNonNull(character.media()).nodes();

                assert nodes != null;
                for (int i = 0; i < nodes.size(); i++) {
                    dataShmata.add(new DataModel(Objects.requireNonNull(Objects.requireNonNull(nodes.get(i)).coverImage()).large(), Objects.requireNonNull(Objects.requireNonNull(nodes.get(i)).title()).userPreferred(), nodes.get(0).id()));
                }

                AdapterAnilistData adapterAnilistData = new AdapterAnilistData(dataShmata, CharacterInfoFragment.this);
                recyclerView.setAdapter(adapterAnilistData);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onResponse(@NotNull Response<GetLislAnimePagenationQuery.Data> response) {

    }

    @Override
    public void onResponseSearch(@NotNull Response<SearchQuery.Data> response) {

    }

    @Override
    public void onResponseAnimeById(@NotNull Response<MediaByIdQuery.Data> response) {

    }

    @Override
    public void onFailure(@NotNull ApolloException e) {
        Log.e("tag", "onFailure: ", e);
        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onAfishaClick(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        navController.navigate(R.id.mediaInfoFragment, bundle);
    }
}