package de.fhws.campusapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import de.fhws.campusapp.adapter.LecturerDetailAdapter;
import de.fhws.campusapp.entity.Lecturer;
import de.fhws.campusapp.network.LecturerNetwork;

public class LecturerDetailActivity extends AppCompatActivity
{
    private Lecturer lecturer;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_detail);
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar(toolbar);

        String lecturerFullName = getIntent().getExtras().getString( "fullName" );
        Lecturer lecturer = LecturerNetwork.getById(lecturerFullName);

        final Uri uri = Uri.parse( lecturer.getPictureUrl() );
        ImageView imageView = (ImageView) findViewById( R.id.ivLecturerPicture );
        Picasso.with( getApplicationContext() ).load( uri ).into( imageView );

        setTitle( lecturer.getName() + " " + lecturer.getLastName() );

        RecyclerView recyclerView = (RecyclerView) findViewById( R.id.rvLecturerDetails );
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new LecturerDetailAdapter(lecturer));
    }
}