package de.fhws.campusapp.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;

import de.fhws.campusapp.R;
import de.fhws.campusapp.database.ModuleDBHelper;
import de.fhws.campusapp.entity.Module;

public class StatisticFragment extends Fragment {

    private String myChoice = "";
    private List<Module> modules;
    private ModuleDBHelper moduleDBHelper;

    private PieChart pieChart;
    private CheckBox apparenticeShip;
    private CheckBox choise;
    private TextView finised;


    public StatisticFragment() {
        this.moduleDBHelper = ModuleDBHelper.getInstance( getContext() );
        this.modules = new ArrayList<>(  );
        addVisitedModules();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistic, container, false);
        pieChart = (PieChart) view.findViewById(R.id.chart);

        apparenticeShip = (CheckBox) view.findViewById( R.id.apprenticeship_cb );
        choise = (CheckBox) view.findViewById( R.id.choise_cb );
        finised = (TextView) view.findViewById( R.id.finished );

        initCheckboxes();
        initPieChart();

        return view;
    }

    private void initCheckboxes() {
        int ects = calculateECTS();
        finised.setText( getString( R.string.finished_etcs, ects ) );
        if( ects >= 91 ) {
            apparenticeShip.setChecked( true );
        }
        if( ects >= 110 ) {
            choise.setChecked( true );
        }
    }

    private int calculateECTS() {
        int ects = 0;
        if( modules.size() > 0 ) {
            for ( Module module : modules ) {
                ects += module.getEcts();
            }
        }

        return ects;
    }

    private void initPieChart(){
        ArrayList<Entry> dataEntrys = new ArrayList<>();
        dataEntrys.add(new Entry(30,0));
        dataEntrys.add(new Entry(50, 0));

        PieDataSet dataSet = new PieDataSet(dataEntrys, getResources().getString(R.string.modules));
        dataSet.setColors(new int[] { R.color.charColorOne, R.color.charColorTwo }, getContext());

        ArrayList<String>titles = new ArrayList<String>();
        titles.add(getResources().getString(R.string.done_modules)); titles.add(getResources().getString(R.string.to_do_modules));
        PieData data = new PieData(titles, dataSet );
        pieChart.setData(data);
    }

    
    private int calculateNumbersOfCourses() {
        loadSavedPreferences();
        if( myChoice.equals( Module.Program.BEC ) )
            return 36;
        return 35;
    }

    private void loadSavedPreferences(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        myChoice = sharedPreferences.getString( "mychoice", Module.Program.BIN );
    }

    private void addVisitedModules() {

        for ( Module current: moduleDBHelper.readAllModules() ) {
            if( current.isVisited() )
                modules.add( current );
        }
    }

}
