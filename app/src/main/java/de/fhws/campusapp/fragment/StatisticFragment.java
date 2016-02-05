package de.fhws.campusapp.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import de.fhws.campusapp.R;
import de.fhws.campusapp.database.ModuleDBHelper;
import de.fhws.campusapp.entity.Module;

public class StatisticFragment extends Fragment {

    private String myChoice = "";
    private List<Module> modules;
    private ModuleDBHelper moduleDBHelper;

    public StatisticFragment() {
        this.moduleDBHelper = ModuleDBHelper.getInstance( getActivity() );
        this.modules = new ArrayList<>(  );
        addVisitedModules();
    }


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_statistic, container, false );
    }

    
    private int calculateNumbersOfCourses() {
        loadSavedPreferences();
        if( myChoice.equals( Module.Program.BEC ) )
            return 36;
        return 35;
    }

    private void loadSavedPreferences(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences( getActivity() );
        myChoice = sharedPreferences.getString( "mychoice", Module.Program.BIN );
    }

    private void addVisitedModules() {

        for ( Module current: moduleDBHelper.readAllModules() ) {
            if( current.isVisited() )
                modules.add( current );
        }
    }

}
