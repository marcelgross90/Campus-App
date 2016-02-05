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
import com.github.mikephil.charting.charts.PieChart;

import de.fhws.campusapp.R;
import de.fhws.campusapp.database.ModuleDBHelper;
import de.fhws.campusapp.entity.Module;

public class StatisticFragment extends Fragment {

    private String myChoice = "";
    private List<Module> modules;
    private ModuleDBHelper moduleDBHelper;

    private PieChart pieChart;

    public StatisticFragment() {
        this.moduleDBHelper = ModuleDBHelper.getInstance( getActivity() );
        this.modules = new ArrayList<>(  );
        addVisitedModules();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistic, container, false);
        pieChart = (PieChart) view.findViewById(R.id.chart);
        return view;
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
