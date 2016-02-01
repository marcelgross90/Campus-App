package de.fhws.campusapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import de.fhws.campusapp.adapter.ModuleDetailAdapter;
import de.fhws.campusapp.entity.Module;
import de.fhws.campusapp.network.ModuleNetwork;

public class ModuleDetailActivity extends AppCompatActivity {

    private Module module;

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
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_module_detail );

        Intent intent = getIntent();
        String lvId = intent.getStringExtra( "lvId" );

        module = ModuleNetwork.getByLvId( lvId );

        setUpToolbar();

        setTitle( module.getLvnameGerman() );
        RecyclerView recyclerView = (RecyclerView) findViewById( R.id.rvModuleDetails );
        recyclerView.setLayoutManager( new LinearLayoutManager( this ) );
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter( new ModuleDetailAdapter( module ) );
    }

    private void setUpToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        ActionBar actionBar = getSupportActionBar();

        if ( actionBar != null )
        {
            actionBar.setDisplayHomeAsUpEnabled( true );
        }
    }
}
