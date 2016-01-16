package de.fhws.campusapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import de.fhws.campusapp.adapter.LecturerDetailAdapter;
import de.fhws.campusapp.entity.Lecturer;

public class LecturerDetailActivity extends AppCompatActivity
{
    private Lecturer lecturer;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_lecturer_detail );
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        RecyclerView recyclerView = (RecyclerView) findViewById( R.id.rvLecturerDetails );
        recyclerView.setLayoutManager( new LinearLayoutManager( this ) );
        recyclerView.setHasFixedSize( true );
        recyclerView.setAdapter( new LecturerDetailAdapter( lecturer ) );
    }
}
