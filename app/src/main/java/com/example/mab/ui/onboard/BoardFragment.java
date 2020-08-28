package com.example.mab.ui.onboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import com.example.mab.App;
import com.example.mab.R;
import com.google.android.material.tabs.TabLayout;

public class BoardFragment extends Fragment {
    private PageAdapter pageAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TextView textViewSkip;
    private NavController navController;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialisation(view);
        setListeners();
        viewPager.setAdapter(pageAdapter);
        tabLayout.setupWithViewPager(viewPager, true);
        onBackPressedCallback();

    }

    private void onBackPressedCallback() {
        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        };
        requireActivity()
                .getOnBackPressedDispatcher()
                .addCallback(getViewLifecycleOwner(), onBackPressedCallback);
    }

    private void setListeners() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position > 1) textViewSkip.setVisibility(View.GONE);
                else textViewSkip.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        textViewSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.getInstance().getPrefs().isShown(true);
                navController.popBackStack();
            }
        });

        pageAdapter.setOnStartClickLListener(new PageAdapter.onStartClickLListener() {
            @Override
            public void onStart() {
                App.getInstance().getPrefs().isShown(true);
                navController.popBackStack();
            }
        });
    }

    private void initialisation(View view) {
        textViewSkip = view.findViewById(R.id.tv_skip);
        pageAdapter = new PageAdapter();
        viewPager = view.findViewById(R.id.viewPager);
        pageAdapter = new PageAdapter();
        tabLayout = view.findViewById(R.id.tab_layout);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
    }
}
