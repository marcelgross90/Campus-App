package de.fhws.campusapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import de.fhws.campusapp.adapter.LecturerDetailAdapter;
import de.fhws.campusapp.entity.Lecturer;
import de.fhws.campusapp.network.LecturerNetworkConnectionHandler;

public class LecturerDetailActivity extends AppCompatActivity implements View.OnClickListener
{
    // Components
    private Toolbar      toolbar;
    private ImageView    imageView;
    private RecyclerView recyclerView;

    // Content
    private Lecturer lecturer;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_lecturer_detail );

        loadComponents();
        setUpView();

        loadContent();
        fillView();
    }

    private void loadComponents()
    {
        toolbar      = (Toolbar)      findViewById( R.id.toolbar );
        imageView    = (ImageView)    findViewById( R.id.ivLecturerPicture );
        recyclerView = (RecyclerView) findViewById( R.id.rvLecturerDetails );
    }

    private void setUpView()
    {
        setUpToolbar();
        setUpRecyclerView();
    }

    private void setUpToolbar()
    {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if ( actionBar != null )
        {
            actionBar.setDisplayHomeAsUpEnabled( true );
        }
    }

    private void setUpRecyclerView()
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize( true );
    }

    private void loadContent()
    {
        loadLecturer();
    }

    private void loadLecturer()
    {
        String lecturerFullName = getIntent().getExtras().getString( "fullName" );
        lecturer = LecturerNetworkConnectionHandler.getById(lecturerFullName);
    }

    private void fillView()
    {
        setTitle(lecturer.getName() + " " + lecturer.getLastName());
        displayLecturerPicture();
        recyclerView.setAdapter(new LecturerDetailAdapter(lecturer, this));
    }

    private void displayLecturerPicture()
    {
        Target target = new Target()
        {
            @Override
            public void onPrepareLoad( Drawable placeHolderDrawable ) {}

            @Override
            public void onBitmapLoaded( Bitmap bitmap, Picasso.LoadedFrom from )
            {
                Bitmap editedBitmap = Bitmap.createBitmap( bitmap, 0, 50, bitmap.getWidth(), 300 );
                BitmapDrawable drawable = new BitmapDrawable( getResources(), editedBitmap );
                imageView.setImageDrawable( drawable );
            }

            @Override
            public void onBitmapFailed( Drawable errorDrawable ) {}
        };
        Picasso.with( getApplicationContext() ).load( lecturer.getPictureUrl() ).into(target);
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item )
    {
        switch ( item.getItemId() )
        {
            case android.R.id.home:
                onBackPressed();
                overridePendingTransition( R.anim.fade_out, R.anim.fade_in );
                return true;
            default:
                return super.onOptionsItemSelected( item );
        }
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
                openPhoneCall();
                break;

            case R.id.tvWebsiteValue :
                openWebsite();
                break;

            case R.id.tvAddressValue :
                openMap();
                break;

            case R.id.tvSubjectValue :
                animationShowOff();         // TODO: Delete after exam
                break;
        }
    }

    private void openEmail()
    {
        Intent intent = new Intent( Intent.ACTION_SEND );
        intent.setType( "plain/text" );
        intent.putExtra( Intent.EXTRA_EMAIL, new String[] { lecturer.getEmail() } );
        startActivity( intent );
    }

    private void openPhoneCall()
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
        startActivity( intent );
    }

    private void openMap()
    {
        Uri gmUri = Uri.parse( "https://www.google.de/maps/place/" + lecturer.getStreet() );    // TODO: Change to lecturer.getAddress() as soon as API is fixed (special characters problem)
        Intent intent = new Intent( Intent.ACTION_VIEW, gmUri );
        intent.setPackage( "com.google.android.apps.maps" );
        startActivity( intent );
    }

    private void animationShowOff()
    {
        CardView card = (CardView) findViewById( R.id.cardViewSpecial );
        Animation animation = AnimationUtils.loadAnimation( getApplicationContext(), R.anim.awesome_animation );
        card.startAnimation( animation );
    }
}