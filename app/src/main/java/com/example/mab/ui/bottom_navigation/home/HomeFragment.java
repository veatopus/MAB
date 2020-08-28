package com.example.mab.ui.bottom_navigation.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

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
import com.example.mab.ui.bottom_navigation.models.DataModel;
import com.example.mab.ui.bottom_navigation.models.AnilistTypeModel;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PageAdapter sectionsPagerAdapter = new PageAdapter(requireContext(), getChildFragmentManager());
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = view.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(8);
    }
}