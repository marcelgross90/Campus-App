package de.fhws.campusapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import de.fhws.campusapp.adapter.LecturerDetailAdapter;
import de.fhws.campusapp.entity.Lecturer;
import de.fhws.campusapp.network.LecturerNetwork;

public class LecturerDetailActivity extends AppCompatActivity implements View.OnClickListener
{
    private Lecturer lecturer;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_detail);
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        String lecturerFullName = getIntent().getExtras().getString("fullName");
        this.lecturer = LecturerNetwork.getById( lecturerFullName );

        final Uri uri = Uri.parse( lecturer.getPictureUrl() );
        ImageView imageView = (ImageView) findViewById( R.id.ivLecturerPicture );
        Picasso.with( getApplicationContext() ).load( uri ).into( imageView );

        setTitle( lecturer.getName() + " " + lecturer.getLastName() );

        RecyclerView recyclerView = (RecyclerView) findViewById( R.id.rvLecturerDetails );
        recyclerView.setLayoutManager( new LinearLayoutManager( this ) );
        recyclerView.setHasFixedSize( true );
        recyclerView.setAdapter( new LecturerDetailAdapter( lecturer, this ) );
    }

    @Override
    public void onClick( View view )
    {
        switch ( view.getId() )
        {
            case R.id.tvEmailValue :
                openEmail();
                break;

            case R.id.tvPhoneValue :
                openPhonecall();
                break;

            case R.id.tvWebsiteValue :
                openWebsite();
                break;
        }
    }

    private void openEmail()
    {
        Intent intent = new Intent( Intent.ACTION_SEND );
        intent.setType( "plain/text" );
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{lecturer.getEmail()});
        startActivity( intent );
    }

    private void openPhonecall()
    {
        Intent intent = new Intent( Intent.ACTION_DIAL );
        String p = "tel:" + lecturer.getPhoneNumber();
        intent.setData( Uri.parse( p ) );
        startActivity( intent );
    }

    private void openWebsite()
    {
        Intent intent = new Intent( Intent.ACTION_VIEW );
        intent.setData( Uri.parse( lecturer.getWebsite() ) );
        startActivity(intent);
    }
}