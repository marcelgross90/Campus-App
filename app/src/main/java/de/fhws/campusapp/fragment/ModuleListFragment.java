package de.fhws.campusapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.fhws.campusapp.MainActivity;
import de.fhws.campusapp.R;
import de.fhws.campusapp.adapter.ModuleListAdapter;
import de.fhws.campusapp.adapter.ModulesPagerAdapter;
import de.fhws.campusapp.entity.Module;


public class ModuleListFragment extends Fragment implements ModuleListAdapter.OnCardClickListener {

    private RecyclerView modulesRecyclerView;
    private RecyclerView.Adapter modulesAdapter;
    private RecyclerView.LayoutManager modulesLayoutMgr;
    private String level;
    private String program;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        level = args.getString(Module.Level.class.getCanonicalName());
        program = args.getString(Module.Program.class.getCanonicalName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View moduleView = inflater.inflate(R.layout.fragment_module_list, container, false);
        modulesRecyclerView = (RecyclerView) moduleView.findViewById(R.id.module_list_rv);
        modulesAdapter = new ModuleListAdapter(getContext(),this, level, program);
        modulesLayoutMgr = new LinearLayoutManager(getContext());

        modulesRecyclerView.setLayoutManager(modulesLayoutMgr);
        modulesRecyclerView.setAdapter(modulesAdapter);

        return moduleView;
    }

    @Override
    public void onCardClick(Module module) {
        Bundle args = new Bundle();
        args.putString("lvId", module.getLvid());
        ModuleDetailFragment detailFragment = new ModuleDetailFragment();
        detailFragment.setArguments(args);
        Fragment frag = getParentFragment();
        MainActivity.replaceFragment(frag.getFragmentManager(), detailFragment);
    }

    public void filter(String searchString){
        ((ModuleListAdapter)modulesRecyclerView.getAdapter()).filter(searchString);
    }
}
