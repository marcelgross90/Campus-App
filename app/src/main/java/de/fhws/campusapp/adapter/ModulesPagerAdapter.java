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

    private Resources res;

    public ModulesPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        res = context.getResources();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment moduleFragment = new ModuleListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Module.Program.class.getCanonicalName(), Module.Program.BIN);
        String module = null;
        switch (position) {
            case 0:
                module =  null;
                break;
            case 1:
                module = Module.Level.ONE;
                break;
            case 2:
                module = Module.Level.TWO;
                break;
            case 3:
                module = Module.Level.THREE;
                break;
            case 4:
                module = Module.Level.FOUR;
                break;
        }
        bundle.putString(Module.Level.class.getCanonicalName(), module);
        moduleFragment.setArguments(bundle);
        return moduleFragment;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        switch (position) {
            case 0:
                title =  res.getString(R.string.overview);
                return title;
            case 1:
                title = Module.Level.ONE;
                break;
            case 2:
                title = Module.Level.TWO;
                break;
            case 3:
                title = Module.Level.THREE;
                break;
            case 4:
                title = Module.Level.FOUR;
                break;
        }

        return String.format(res.getString(R.string.level), title);
    }
}
