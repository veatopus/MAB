package com.example.mab.ui.bottom_navigation.home;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mab.R;

import org.jetbrains.annotations.NotNull;

public class PageAdapter extends FragmentPagerAdapter {

    private String[] TAB_TITLES;

    public PageAdapter(Context context, FragmentManager fm) {
        super(fm);
        TAB_TITLES = context.getResources().getStringArray(R.array.spinner_format_anime_type);
    }

    @NotNull
    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(TAB_TITLES[position]);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES[position];
    }

    @Override
    public int getCount() {
        return TAB_TITLES.length;
    }
}
