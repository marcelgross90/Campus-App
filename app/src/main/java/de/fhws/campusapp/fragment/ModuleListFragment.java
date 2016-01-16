package de.fhws.campusapp.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.fhws.campusapp.MainActivity;
import de.fhws.campusapp.R;
import de.fhws.campusapp.adapter.ModuleListAdapter;
import de.fhws.campusapp.entity.Module;


public class ModuleListFragment extends Fragment implements ModuleListAdapter.OnCardClickListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private RecyclerView modulesRecyclerView;
    private RecyclerView.Adapter modulesAdapter;
    private RecyclerView.LayoutManager modulesLayoutMgr;
    private String level;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        level = args.getString(Module.Level.class.getCanonicalName());
    }

    @Override
    public void onPause() {
        super.onPause();
        PreferenceManager.getDefaultSharedPreferences(getContext())
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        PreferenceManager.getDefaultSharedPreferences(getContext())
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View moduleView = inflater.inflate(R.layout.fragment_module_list, container, false);
        modulesRecyclerView = (RecyclerView) moduleView.findViewById(R.id.module_list_rv);
        modulesAdapter = new ModuleListAdapter(getContext(),this, level);
        modulesLayoutMgr = new LinearLayoutManager(getContext());

        modulesRecyclerView.setLayoutManager(modulesLayoutMgr);
        modulesRecyclerView.setAdapter(modulesAdapter);

        setTitle();
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

    private void setTitle(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String program = sharedPreferences.getString("mychoice", Module.Program.BIN);
        String title = null;

        switch(program){
            case Module.Program.BIN:
                title = getResources().getString(R.string.bin);
                break;
            case Module.Program.BWI:
                title = getResources().getString(R.string.win);
                break;
            case Module.Program.BEC:
                title = getResources().getString(R.string.ec);
                break;
        }

        getActivity().setTitle(title);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        String program = sharedPreferences.getString("mychoice", Module.Program.BIN);
        setTitle();
        ((ModuleListAdapter)modulesRecyclerView.getAdapter()).programChanged(program);
    }
}
