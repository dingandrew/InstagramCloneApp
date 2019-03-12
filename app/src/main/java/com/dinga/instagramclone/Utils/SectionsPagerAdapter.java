package com.dinga.instagramclone.Utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


/*
class that stores fragments for tabs
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = "SectionsPagerAdapter";

    private final List<Fragment> mFragmentList = new ArrayList<>();

    /*
    default constructor
     */
    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /*
    default methods
     */
    /*
    returns an fragment at index position
     */
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    /*
    returns size of fragment list
     */
    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    /*
    adds a fragment at the end of list
     */
    public void addFragment(Fragment fragment){
        mFragmentList.add(fragment);
    }
}
