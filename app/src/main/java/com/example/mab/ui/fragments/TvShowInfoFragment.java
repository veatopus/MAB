package com.example.mab.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mab.App;
import com.example.mab.R;
import com.example.mab.data.TvMazeService;
import com.example.mab.ui.bottom_navigation.models.Cast;
import com.example.mab.ui.bottom_navigation.models.Crew;
import com.example.mab.ui.bottom_navigation.models.EpisodeModel;
import com.example.mab.ui.bottom_navigation.models.FilmModel;
import com.example.mab.ui.bottom_navigation.models.ImageOfGetImageById;
import com.example.mab.ui.bottom_navigation.models.Show;
import com.example.mab.ui.bottom_navigation.models.ShowModel;
import com.example.mab.ui.bottom_navigation.models.ShowModelGetFullSchedule;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class TvShowInfoFragment extends Fragment implements TvMazeService.TvMazeCallback {
    int id;
    @BindView(R.id.image_view_banner)
    ImageView imageViewBanner;
    @BindView(R.id.image_view_afisha)
    ImageView imageViewAfisha;
    @BindView(R.id.text_view_media_name)
    TextView textViewName;
    @BindView(R.id.text_view_average)
    TextView textViewAverage;
    @BindView(R.id.text_view_type)
    TextView textViewType;
    @BindView(R.id.text_view_language)
    TextView textViewLanguage;
    @BindView(R.id.text_view_status)
    TextView textViewStatus;
    @BindView(R.id.text_view_runtime)
    TextView textViewRuntime;
    @BindView(R.id.text_view_schedule)
    TextView textViewSchedule;
    @BindView(R.id.text_view_genres)
    TextView textViewGenres;
    @BindView(R.id.text_view_description)
    TextView textViewDescription;
    @BindView(R.id.btn_open_characters)
    Button buttonOpenCharacters;
    @BindView(R.id.recyclerview_character)
    RecyclerView recyclerViewCharacter;
    @BindView(R.id.recyclerview_crew)
    RecyclerView recyclerViewCrew;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        assert bundle != null;
        id = bundle.getInt("id");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tv_show_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        progressBar.setVisibility(View.VISIBLE);
        App.getInstance().getTvMazeService().getShowById(id, this);
        App.getInstance().getTvMazeService().getImagesById(id, this);
        setListener();
    }

    private void setListener() {
        buttonOpenCharacters.setOnClickListener(v -> {

        });
    }


    @Override
    public void onResponseList(@NotNull Response<List<FilmModel>> response) {

    }

    @Override
    public void onResponseListGetAll(@NotNull Response<List<Show>> response) {

    }

    @Override
    public void onResponseListSchedule(@NotNull Response<List<ShowModel>> response) {

    }

    @Override
    public void onResponseListScheduleFull(@NotNull Response<List<ShowModelGetFullSchedule>> response) {

    }

    @Override
    public void onResponseListEpisode(@NotNull Response<List<EpisodeModel>> response) {

    }

    @SuppressLint("CheckResult")
    @Override
    public void onResponseImagesByIdTVShow(@NotNull Response<List<ImageOfGetImageById>> response) {
        if (response.body() != null)
        for (ImageOfGetImageById imageOfGetImageById : response.body()) {
            if (imageOfGetImageById.getMain()) {
                Glide.with(this).load(imageOfGetImageById.getResolutions().getOriginal()).into(imageViewAfisha);
            }
            if (imageOfGetImageById.getType().equals("banner")) {
                Glide.with(this).load(imageOfGetImageById.getResolutions().getOriginal()).into(imageViewBanner);
            }
        }
    }

    @Override
    public void onFailure(Throwable t) {
        t.printStackTrace();
    }

    @Override
    public void onResponseCastList(@NotNull Response<List<Cast>> response) {

    }

    @Override
    public void onResponseListCrew(@NotNull Response<List<Crew>> response) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onResponseTvShowById(@NotNull Response<Show> response) {
        Log.e("tag", "onResponseTvShowById: " + response.toString());
        if (response.isSuccessful()) {
            Show show = response.body();
            assert show != null;
            textViewDescription.setText(show.getSummary());
            if (show.getRating().getAverage() != null)
                textViewAverage.setText(show.getRating().getAverage().toString());
            textViewStatus.setText(show.getStatus());
            textViewLanguage.setText(show.getLanguage());
            textViewName.setText(show.getName());
            textViewType.setText(show.getType());
            textViewRuntime.setText(show.getRuntime().toString());
            textViewSchedule.setText(show.getSchedule().getTime() + "\n" + show.getSchedule().getDays());
            for (String genre : show.getGenres()) {
                textViewGenres.append(", " + genre);
            }
            Glide.with(this).load(show.getImage().getOriginal()).into(imageViewAfisha);
            Glide.with(this).load(show.getImage().getOriginal()).into(imageViewBanner);
        }
        progressBar.setVisibility(View.GONE);
    }
}