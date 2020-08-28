package com.example.mab.ui.bottom_navigation.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.mab.App;
import com.example.mab.GetCharacterQuery;
import com.example.mab.GetLislAnimePagenationQuery;
import com.example.mab.MediaByIdQuery;
import com.example.mab.R;
import com.example.mab.SearchQuery;
import com.example.mab.data.AnilistService;
import com.example.mab.type.MediaFormat;
import com.example.mab.type.MediaSeason;
import com.example.mab.ui.bottom_navigation.adapters.AdapterAnilistData;
import com.example.mab.ui.bottom_navigation.adapters.AdapterAnilistType;
import com.example.mab.ui.bottom_navigation.models.AnilistTypeModel;
import com.example.mab.ui.bottom_navigation.models.DataModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PageFragment extends Fragment implements AnilistService.AnilistCallback, AdapterAnilistData.AfishaClickListener, AdapterAnilistType.GiveMeInformationNigga {

    private AdapterAnilistType adapterAnilistType;
    private int year;
    private String mediaFormat;
    private NavController navController;
    private int searchPage = 1;
    private int searchPerPage = 50;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static PageFragment newInstance(String format) {
        PageFragment fragment = new PageFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_SECTION_NUMBER, format);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mediaFormat = getArguments().getString(ARG_SECTION_NUMBER);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.page_fragment_anilist, container, false);
        ButterKnife.bind(this,root);
        year = Calendar.getInstance().get(Calendar.YEAR);
        //progressBar = root.findViewById(R.id.progress_bar);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        RecyclerView recyclerView = root.findViewById(R.id.recyclerview);
        adapterAnilistType = new AdapterAnilistType(this, this);
        recyclerView.setAdapter(adapterAnilistType);
        updateInfo();
        return root;
    }

    @Override
    public void onResponse(@NotNull Response<GetLislAnimePagenationQuery.Data> response) {
        List<GetLislAnimePagenationQuery.Medium> media = Objects.requireNonNull(Objects.requireNonNull(response.getData()).Page()).media();
        List<DataModel> data = new ArrayList<>();
        assert media != null;
        for (GetLislAnimePagenationQuery.Medium medium : media) {
            DataModel model = new DataModel(Objects.requireNonNull(Objects.requireNonNull(medium.coverImage()).large()), Objects.requireNonNull(medium.title()).userPreferred(), medium.id());
            data.add(model);
        }
        final List<AnilistTypeModel> typeModels = new ArrayList<>();
        if (media.size() > 0) {
            AnilistTypeModel anilistTypeModel = new AnilistTypeModel("Популярно в " + Objects.requireNonNull(media.get(0).season()).name() + " сезоне: " + media.get(0).seasonYear(), data);
            typeModels.add(anilistTypeModel);
            requireActivity().runOnUiThread(() -> {
                progressBar.setVisibility(View.GONE);
                adapterAnilistType.addData(typeModels);
            });
        }
    }

    @Override
    public void onResponseSearch(@NotNull Response<SearchQuery.Data> response) {
        progressBar.setVisibility(View.GONE);
        requireActivity().runOnUiThread(() -> adapterAnilistType.clear());
        List<SearchQuery.Medium> media = Objects.requireNonNull(Objects.requireNonNull(response.getData()).Page()).media();
        List<DataModel> data = new ArrayList<>();
        assert media != null;
        for (SearchQuery.Medium medium : media) {
            DataModel model = new DataModel(Objects.requireNonNull(Objects.requireNonNull(medium.coverImage()).large()), Objects.requireNonNull(medium.title()).userPreferred(), medium.id());
            data.add(model);
        }
        final List<AnilistTypeModel> typeModels = new ArrayList<>();
        if (media.size() > 0) {
            AnilistTypeModel anilistTypeModel = new AnilistTypeModel("", data);
            typeModels.add(anilistTypeModel);
            requireActivity().runOnUiThread(() -> {
                adapterAnilistType.addData(typeModels);
                progressBar.setVisibility(View.GONE);
            });
        }
    }

    @Override
    public void onResponseCharacter(@NotNull Response<GetCharacterQuery.Data> response) {

    }

    @Override
    public void onResponseAnimeById(@NotNull Response<MediaByIdQuery.Data> response) {

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
    public void updateInfo() {
        progressBar.setVisibility(View.VISIBLE);
        App.getInstance().getAnilistService().getPage(1, 50, MediaFormat.safeValueOf(mediaFormat), year, MediaSeason.WINTER, this);
        App.getInstance().getAnilistService().getPage(1, 50, MediaFormat.safeValueOf(mediaFormat), year, MediaSeason.SPRING, this);
        App.getInstance().getAnilistService().getPage(1, 50, MediaFormat.safeValueOf(mediaFormat), year, MediaSeason.SUMMER, this);
        App.getInstance().getAnilistService().getPage(1, 50, MediaFormat.safeValueOf(mediaFormat), year, MediaSeason.FALL, this);
        year--;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.toolbar_search_menu, menu);
        MenuItem.OnActionExpandListener onActionExpandListener = new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                return true;
            }
        };
        menu.findItem(R.id.action_search).setOnActionExpandListener(onActionExpandListener);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnCloseListener(() -> {
            year = Calendar.getInstance().get(Calendar.YEAR);
            updateInfo();
            return false;
        });
        searchView.setQueryHint("Поиск");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equals("")) return false;
                App.getInstance().getAnilistService().search(searchPage, searchPerPage, newText, MediaFormat.safeValueOf(mediaFormat) ,PageFragment.this);
                return false;
            }
        });
    }
}