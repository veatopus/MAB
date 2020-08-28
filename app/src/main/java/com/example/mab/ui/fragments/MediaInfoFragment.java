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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.bumptech.glide.Glide;
import com.example.mab.GetCharacterQuery;
import com.example.mab.GetLislAnimePagenationQuery;
import com.example.mab.MediaByIdQuery;
import com.example.mab.R;
import com.example.mab.SearchQuery;
import com.example.mab.data.AnilistService;
import com.example.mab.ui.bottom_navigation.adapters.AdapterAnilistCharacters;
import com.example.mab.ui.bottom_navigation.adapters.AdapterAnilistData;
import com.example.mab.ui.bottom_navigation.models.CharacterAniList;
import com.example.mab.ui.bottom_navigation.models.DataModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MediaInfoFragment extends Fragment implements AnilistService.AnilistCallback, AdapterAnilistData.AfishaClickListener, AdapterAnilistCharacters.OnCharacterClickListeter {
    private int id;
    private ImageView imageViewBanner, imageViewAfisha;
    private TextView textViewMediaName,
            textViewMediaAverageScore,
            textViewMediaNameUserPreferredNative,
            textViewMediaType, textViewMediaFormat, textViewMediaStatus,
            textViewMediaSeason, textViewMediaDescription, textViewMediaEpisodes,
            textViewMediaDuration, textViewMediaGenres;
    private ProgressBar progressBar;
    private Button buttonOpenCharacters;
    private RecyclerView recyclerView, recyclerViewRelation;
    private NavController navController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        assert bundle != null;
        id = bundle.getInt("id");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_media_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialisation(view);
        setListener();
        AnilistService anilistService = new AnilistService();
        anilistService.getMediaById(id, this);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void setListener() {
        final boolean[] isFirst = {false};
        buttonOpenCharacters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFirst[0]) recyclerView.setVisibility(View.GONE);
                if (!isFirst[0]) recyclerView.setVisibility(View.VISIBLE);
                isFirst[0] = !isFirst[0];
            }
        });
    }

    private void initialisation(View view) {
        imageViewBanner = view.findViewById(R.id.image_view_cowboy_banner);
        imageViewAfisha = view.findViewById(R.id.image_view_coboy_afisha);
        textViewMediaName = view.findViewById(R.id.text_view_media_name_userPreferred);
        textViewMediaNameUserPreferredNative = view.findViewById(R.id.text_view_media_name_userPreferred_native);
        textViewMediaAverageScore = view.findViewById(R.id.text_view_average_score);
        textViewMediaType = view.findViewById(R.id.text_view_type);
        textViewMediaDuration = view.findViewById(R.id.text_view_duration);
        textViewMediaEpisodes = view.findViewById(R.id.text_view_episodes);
        textViewMediaFormat = view.findViewById(R.id.text_view_format);
        textViewMediaGenres = view.findViewById(R.id.text_view_genres);
        textViewMediaStatus = view.findViewById(R.id.text_view_status);
        textViewMediaSeason = view.findViewById(R.id.text_view_season);
        textViewMediaDescription = view.findViewById(R.id.text_view_media_description);
        progressBar = view.findViewById(R.id.progress_bar);
        buttonOpenCharacters = view.findViewById(R.id.btn_open_characters);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerViewRelation = view.findViewById(R.id.recyclerview_relations_anime);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

    }

    @Override
    public void onResponse(@NotNull Response<GetLislAnimePagenationQuery.Data> response) {

    }

    @Override
    public void onResponseSearch(@NotNull Response<SearchQuery.Data> response) {

    }

    @Override
    public void onResponseCharacter(@NotNull Response<GetCharacterQuery.Data> response) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onResponseAnimeById(@NotNull Response<MediaByIdQuery.Data> response) {
        final MediaByIdQuery.Media media = Objects.requireNonNull(response.getData()).Media();
        requireActivity().runOnUiThread(() -> {
            assert media != null;
            setMediaInMainView(media);
        });
    }

    @SuppressLint("SetTextI18n")
    private void setMediaInMainView(MediaByIdQuery.Media media) {
        if (media != null) {
            Glide.with(MediaInfoFragment.this).load(media.bannerImage()).into(imageViewBanner);
            Glide.with(MediaInfoFragment.this).load(Objects.requireNonNull(media.coverImage()).extraLarge()).into(imageViewAfisha);
            textViewMediaName.setText(Objects.requireNonNull(Objects.requireNonNull(media.title()).userPreferred()).trim());
            if (media.seasonYear() != null)
                textViewMediaName.append(": " + Objects.requireNonNull(media.seasonYear()).toString());
            if (media.averageScore() != null)
                textViewMediaAverageScore.setText(Objects.requireNonNull(media.averageScore()).toString());
            else
                textViewMediaAverageScore.setText("-");
            textViewMediaNameUserPreferredNative.setText(Objects.requireNonNull(media.title()).native_());
            textViewMediaType.setText(Objects.requireNonNull(media.type()).rawValue());
            textViewMediaFormat.setText(Objects.requireNonNull(media.format()).rawValue());
            textViewMediaStatus.setText(Objects.requireNonNull(media.status()).rawValue());
            if (media.season() != null)
                textViewMediaSeason.setText(Objects.requireNonNull(media.season()).rawValue());
            else
                textViewMediaSeason.setText("-");
            textViewMediaDescription.setText(Html.fromHtml(media.description(), Html.FROM_HTML_MODE_COMPACT));
            if (media.episodes() != null)
                textViewMediaEpisodes.setText(Objects.requireNonNull(media.episodes()).toString());
            else
                textViewMediaEpisodes.setText("-");
            if (media.duration() != null)
                textViewMediaDuration.setText(Objects.requireNonNull(media.duration()).toString());
            else
                textViewMediaDuration.setText("-");
            for (String s : Objects.requireNonNull(media.genres())) {
                textViewMediaGenres.append(s + ", ");
            }
            MediaByIdQuery.Characters characters = media.characters();
            progressBar.setVisibility(View.GONE);
            List<CharacterAniList> data = new ArrayList<>();
            assert characters != null;
            for (int i = 0; i < Objects.requireNonNull(characters.nodes()).size(); i++) {
                data.add(new CharacterAniList(Objects.requireNonNull(characters.nodes()).get(i), Objects.requireNonNull(characters.edges()).get(i)));
            }
            AdapterAnilistCharacters adapter = new AdapterAnilistCharacters(data, requireContext(), this);
            recyclerView.setAdapter(adapter);
            List<DataModel> dataShmata = new ArrayList<>();
            for (MediaByIdQuery.Node node : Objects.requireNonNull(Objects.requireNonNull(media.relations()).nodes())) {
                DataModel model = new DataModel(Objects.requireNonNull(Objects.requireNonNull(node.coverImage()).large()), Objects.requireNonNull(node.title()).userPreferred(), node.id());
                dataShmata.add(model);
            }
            AdapterAnilistData adapterAnilistData = new AdapterAnilistData(dataShmata, this);
            recyclerViewRelation.setAdapter(adapterAnilistData);
        }
    }

    @Override
    public void onFailure(@NotNull ApolloException e) {

    }

    @Override
    public void onAfishaClick(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        navController.navigate(R.id.mediaInfoFragment, bundle);
    }

    @Override
    public void onCharacterClick(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        navController.navigate(R.id.characterInfoFragment, bundle);
    }
}