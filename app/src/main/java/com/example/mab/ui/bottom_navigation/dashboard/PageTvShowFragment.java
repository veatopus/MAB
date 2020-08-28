package com.example.mab.ui.bottom_navigation.dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;

import com.example.mab.App;
import com.example.mab.R;
import com.example.mab.data.TvMazeService;
import com.example.mab.ui.bottom_navigation.adapters.AdapterAnilistData;
import com.example.mab.ui.bottom_navigation.adapters.AdapterAnilistType;
import com.example.mab.ui.bottom_navigation.models.Cast;
import com.example.mab.ui.bottom_navigation.models.Crew;
import com.example.mab.ui.bottom_navigation.models.DataModel;
import com.example.mab.ui.bottom_navigation.models.EpisodeModel;
import com.example.mab.ui.bottom_navigation.models.FilmModel;
import com.example.mab.ui.bottom_navigation.models.Image;
import com.example.mab.ui.bottom_navigation.models.ImageOfGetImageById;
import com.example.mab.ui.bottom_navigation.models.Show;
import com.example.mab.ui.bottom_navigation.models.ShowModel;
import com.example.mab.ui.bottom_navigation.models.ShowModelGetFullSchedule;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;


public class PageTvShowFragment extends Fragment implements TvMazeService.TvMazeCallback, AdapterAnilistData.AfishaClickListener, AdapterView.OnItemSelectedListener, AdapterAnilistType.GiveMeInformationNigga{
    private static final String TAG = "tag";
    private AdapterAnilistData adapter;
    private int page = 1;
    private ProgressBar progressBar;
    private String formatData;
    private NavController navController;

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static PageTvShowFragment newInstance(String format) {
        PageTvShowFragment fragment = new PageTvShowFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_SECTION_NUMBER, format);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        if (getArguments() != null) {
            formatData = getArguments().getString(ARG_SECTION_NUMBER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_page_tv_show, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.recyclerview);
        List<DataModel> dataModels = new ArrayList<>();
        adapter = new AdapterAnilistData(dataModels, this);
        progressBar = root.findViewById(R.id.progress_bar);
        adapter.setGiveMeInformationNigga(this);
        recyclerView.setAdapter(adapter);
        updateInfo();
        return root;
    }


    @Override
    public void onResponseList(@NotNull Response<List<FilmModel>> response) {

    }

    @Override
    public void onResponseListGetAll(@NotNull Response<List<Show>> response) {
        List<DataModel> data = new ArrayList<>();
        assert response.body() != null;
        for (Show show : response.body()) {
            if (show.getImage() == null) {
                Image image = new Image();
                image.setOriginal("https://upload.wikimedia.org/wikipedia/commons/9/9a/%D0%9D%D0%B5%D1%82_%D1%84%D0%BE%D1%82%D0%BE.png");
                show.setImage(image);
            }
            data.add(new DataModel(show.getImage().getOriginal(), show.getName(), show.getId()));

        }
        progressBar.setVisibility(View.GONE);
        adapter.addData(data);
    }

    @Override
    public void onResponseListSchedule(@NotNull Response<List<ShowModel>> response) {
        assert response.body() != null;
        List<DataModel> data = new ArrayList<>();
        for (ShowModel showModel : response.body()) {
            if (showModel.getShow().getImage() == null) {
                Image image = new Image();
                image.setOriginal("https://www.google.com/imgres?imgurl=https%3A%2F%2Fupload.wikimedia.org%2Fwikipedia%2Fcommons%2F9%2F9a%2F%25D0%259D%25D0%25B5%25D1%2582_%25D1%2584%25D0%25BE%25D1%2582%25D0%25BE.png&imgrefurl=https%3A%2F%2Fru.wikipedia.org%2Fwiki%2F%25D0%25A4%25D0%25B0%25D0%25B9%25D0%25BB%3A%25D0%259D%25D0%25B5%25D1%2582_%25D1%2584%25D0%25BE%25D1%2582%25D0%25BE.png&tbnid=ClFWNn3252vH-M&vet=12ahUKEwjV97LU95_rAhXL6CoKHQ9JCi0QMygBegUIARDRAQ..i&docid=eoqmhDiNLo3LDM&w=300&h=300&q=%D0%BD%D0%B5%D1%82%20%D1%84%D0%BE%D1%82%D0%BE%20image&ved=2ahUKEwjV97LU95_rAhXL6CoKHQ9JCi0QMygBegUIARDRAQ");
                showModel.getShow().setImage(image);
            }
            data.add(new DataModel(showModel.getShow().getImage().getOriginal(), showModel.getShow().getName(), showModel.getShow().getId()));

        }
        progressBar.setVisibility(View.GONE);
        adapter.addData(data);
    }

    @Override
    public void onResponseListScheduleFull(@NotNull Response<List<ShowModelGetFullSchedule>> response) {
        assert response.body() != null;
        List<DataModel> data = new ArrayList<>();
        for (ShowModelGetFullSchedule showModel : response.body()) {
            if (showModel.getEmbedded().getShow().getImage() == null) {
                Image image = new Image();
                image.setOriginal("https://www.google.com/imgres?imgurl=https%3A%2F%2Fupload.wikimedia.org%2Fwikipedia%2Fcommons%2F9%2F9a%2F%25D0%259D%25D0%25B5%25D1%2582_%25D1%2584%25D0%25BE%25D1%2582%25D0%25BE.png&imgrefurl=https%3A%2F%2Fru.wikipedia.org%2Fwiki%2F%25D0%25A4%25D0%25B0%25D0%25B9%25D0%25BB%3A%25D0%259D%25D0%25B5%25D1%2582_%25D1%2584%25D0%25BE%25D1%2582%25D0%25BE.png&tbnid=ClFWNn3252vH-M&vet=12ahUKEwjV97LU95_rAhXL6CoKHQ9JCi0QMygBegUIARDRAQ..i&docid=eoqmhDiNLo3LDM&w=300&h=300&q=%D0%BD%D0%B5%D1%82%20%D1%84%D0%BE%D1%82%D0%BE%20image&ved=2ahUKEwjV97LU95_rAhXL6CoKHQ9JCi0QMygBegUIARDRAQ");
                showModel.getEmbedded().getShow().setImage(image);
            }
            data.add(new DataModel(showModel.getEmbedded().getShow().getImage().getOriginal(), showModel.getEmbedded().getShow().getName(), showModel.getEmbedded().getShow().getId()));
        }
        progressBar.setVisibility(View.GONE);
        adapter.addData(data);
    }

    @Override
    public void onResponseListEpisode(@NotNull Response<List<EpisodeModel>> response) {
        assert response.body() != null;
    }

    @Override
    public void onResponseImagesByIdTVShow(@NotNull Response<List<ImageOfGetImageById>> response) {

    }

    @Override
    public void onFailure(Throwable t) {
        t.printStackTrace();
        Log.e(TAG, "DashboardFragment: onFailure: ", t);
    }

    @Override
    public void onResponseCastList(@NotNull Response<List<Cast>> response) {

    }

    @Override
    public void onResponseListCrew(@NotNull Response<List<Crew>> response) {

    }

    @Override
    public void onResponseTvShowById(@NotNull Response<Show> response) {

    }

    @Override
    public void onAfishaClick(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        navController.navigate(R.id.tvShowInfoFragment, bundle);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        formatData = parent.getItemAtPosition(position).toString();
        adapter.clear();
        updateInfo();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void updateInfo() {
        switch (formatData){
            case "schedule to day, USA":
                Log.e(TAG, "updateInfo: 1");
                App.getInstance().getTvMazeService().getSchedule(this);
                progressBar.setVisibility(View.VISIBLE);
                break;

            case "schedule full":
                Log.e(TAG, "updateInfo: 2");
                App.getInstance().getTvMazeService().getScheduleFull(this);
                progressBar.setVisibility(View.VISIBLE);
                break;

            case "all":
                Log.e(TAG, "updateInfo: 3");
                App.getInstance().getTvMazeService().getAll(page++, this);
                progressBar.setVisibility(View.VISIBLE);
                break;
        }
    }
}