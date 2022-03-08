package com.example.safetrail.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.safetrail.CustomerManagement;
import com.example.safetrail.Information;
import com.example.safetrail.R;
import com.example.safetrail.TrainManagement;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2,R.string.tab_text_3};
    private final Context mContext;
    private TrainManagement trainManagement;
    private CustomerManagement customerManagement;
    private Information information;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        trainManagement = new TrainManagement();
        customerManagement = new CustomerManagement();
        information = new Information();
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        if (position == 0){
            return trainManagement;
        }
        else if (position == 1){
            return customerManagement;
        }
        else{
            return information;
        }
        //return PlaceholderFragment.newInstance(position + 1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }


}