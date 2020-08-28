package com.example.mab.ui.bottom_navigation.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.mab.App;
import com.example.mab.R;
import com.example.mab.data.TvMazeService;
import com.example.mab.ui.bottom_navigation.adapters.AdapterAnilistData;
import com.example.mab.ui.bottom_navigation.adapters.AdapterAnilistType;
import com.example.mab.ui.bottom_navigation.models.DataModel;
import com.example.mab.ui.bottom_navigation.models.EpisodeModel;
import com.example.mab.ui.bottom_navigation.models.FilmModel;
import com.example.mab.ui.bottom_navigation.models.Image;
import com.example.mab.ui.bottom_navigation.models.Show;
import com.example.mab.ui.bottom_navigation.models.ShowModel;
import com.example.mab.ui.bottom_navigation.models.ShowModelGetFullSchedule;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class DashboardFragment extends Fragment{


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PageAdapter sectionsPagerAdapter = new PageAdapter(requireContext(), getChildFragmentManager());
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = view.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }
}