package com.application.iitkharagpurproject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {

    private int numbertabs;


    public PagerAdapter(@NonNull FragmentManager fm, int behavior, int numbertabs) {
        super(fm, behavior);
        this.numbertabs = numbertabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch(position){
            case 0:
                return new LoginFragment();
            case 1:
                return new RegisterFragment();
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return numbertabs;
    }
}
