package de.fhws.campusapp.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class ModulesPagerAdapter extends FragmentStatePagerAdapter {

    private Fragment[] fragments;
    private Context context;

    public ModulesPagerAdapter(FragmentManager fm, Context context){
        super(fm);
        this.context = context;
    }
    
    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
