package de.fhws.campusapp.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import de.fhws.campusapp.R;
import de.fhws.campusapp.entity.Module;
import de.fhws.campusapp.fragment.ModuleListFragment;


public class ModulesPagerAdapter extends FragmentStatePagerAdapter {

    private Fragment[] fragments;
    private String[] levels;
    private Context context;
    private Resources res;

    public ModulesPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        res = context.getResources();
    }

    private void initiatePagerAttr(String program) {
        levels = new String[5];
        fragments = new Fragment[5];

        levels[0] = res.getString(R.string.overview);
        levels[1] = Module.Level.ONE;
        levels[2] = Module.Level.TWO;
        levels[3] = Module.Level.THREE;
        levels[4] = Module.Level.FOUR;

        fragments[0] = new ModuleListFragment();
        for (int i = 1; i < fragments.length; ++i) {
            fragments[i] = new ModuleListFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Module.Program.class.getCanonicalName(), program);
            bundle.putString(Module.Level.class.getCanonicalName(), levels[i]);
        }


    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0){
            return levels[0];
        }
        else{
            return(String.format(res.getString(R.string.level, levels[position])));
        }
    }
}
