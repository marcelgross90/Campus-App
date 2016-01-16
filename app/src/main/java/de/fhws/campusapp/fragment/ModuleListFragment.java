package de.fhws.campusapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.fhws.campusapp.R;


public class ModuleListFragment extends Fragment {

    private RecyclerView modulesRecyclerView;
    private RecyclerView.Adapter modulesAdapter;
    private RecyclerView.LayoutManager modulesLayoutMgr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View moduleView = inflater.inflate( R.layout.fragment_module_list, container, false );
        modulesRecyclerView = (RecyclerView) moduleView.findViewById(R.id.module_list_rv);

        return moduleView;
    }
}
