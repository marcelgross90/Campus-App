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

public class ModuleDetailActivity extends AppCompatActivity
{
    // Components
    private Toolbar toolbar;
    private RecyclerView recyclerView;

    // Content
    private Module module;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_detail);

        loadComponents();
        setUpView();

        loadContent();
        fillView();
    }

    private void loadComponents()
    {
        toolbar      = (Toolbar)      findViewById( R.id.toolbar );
        recyclerView = (RecyclerView) findViewById( R.id.rvModuleDetails );
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
        loadModule();
    }

    private void loadModule()
    {
        Intent intent = getIntent();
        String lvId = intent.getStringExtra( "lvId" );

        module = ModuleNetwork.getByLvId( lvId );
    }

    private void fillView()
    {
        setTitle(module.getLvnameGerman());
        recyclerView.setAdapter(new ModuleDetailAdapter(module));
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
}
