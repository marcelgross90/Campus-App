package de.fhws.campusapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;

import de.fhws.campusapp.R;

public class StatisticFragment extends Fragment {


    private PieChart pieChart;

    public StatisticFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistic, container, false);
        pieChart = (PieChart) view.findViewById(R.id.chart);
        initPieChart();
        return view;
    }

    private void initPieChart(){
        ArrayList<Entry> dataEntrys = new ArrayList<>();
        dataEntrys.add(new Entry(30,0));
        dataEntrys.add(new Entry(50,0));

        PieDataSet dataSet = new PieDataSet(dataEntrys, "ECTS");
        dataSet.setColors(new int[] { R.color.colorAccent, R.color.colorPrimary }, getContext());

        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("Absolviert"); xVals.add("Ausstehend");
        PieData data = new PieData(xVals, dataSet );
        pieChart.setData(data);
    }

}
