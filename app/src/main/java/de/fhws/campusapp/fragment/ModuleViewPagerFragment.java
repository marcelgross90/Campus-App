package de.fhws.campusapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.fhws.campusapp.R;
import de.fhws.campusapp.adapter.ModulesPagerAdapter;

public class ModuleViewPagerFragment extends Fragment {

    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View pagerView = inflater.inflate(R.layout.fragment_module_view_pager, container, false);
        viewPager = (ViewPager) pagerView.findViewById(R.id.module_view_pager);

        viewPager.setAdapter(new ModulesPagerAdapter(getChildFragmentManager(), getActivity()));

        return pagerView;
    }
}
