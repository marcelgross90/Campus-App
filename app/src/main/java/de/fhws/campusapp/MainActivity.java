package de.fhws.campusapp;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import de.fhws.campusapp.fragment.LecturersFragment;
import de.fhws.campusapp.fragment.ModuleViewPagerFragment;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private FragmentManager fm;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    public static void replaceFragment( FragmentManager fm, Fragment fragment ) {
        fm.beginTransaction()
                .replace( R.id.content_container,
                        fragment )
                .addToBackStack( null )
                .commit();
    }

    public static void replaceFragmentPopBackStack( FragmentManager fm, Fragment fragment ) {
        fm.popBackStack();
        replaceFragment( fm, fragment );
    }

    @Override
    public boolean onSupportNavigateUp() {
        fm.popBackStack();

        return false;
    }

    @Override
    public void onBackPressed() {
        if( fm.getBackStackEntryCount() > 1 )
            super.onBackPressed();
        else
            finish();
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        NavigationView nv = (NavigationView) findViewById( R.id.navigation_view );
        nv.setNavigationItemSelectedListener( new MyDrawerClickListener() );

        fm = getSupportFragmentManager();

        initToolbar();

        if( savedInstanceState == null ) {
            replaceFragment( fm, new LecturersFragment() );
        }

        initOpenCloseEventHandling();

    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        fm.addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    @Override
                    public void onBackStackChanged() {
                        canBack();
                    }
                }
        );
        canBack();
    }

    private void canBack() {
        ActionBar actionBar = getSupportActionBar();
        if( actionBar != null ) {
            actionBar.setDisplayHomeAsUpEnabled(
                    fm.getBackStackEntryCount() > 1
            );
        }
    }

    private void initOpenCloseEventHandling() {
        drawerLayout = (DrawerLayout) findViewById( R.id.drawer_layout );
        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close ) {
            private ActionBar actionBar = getSupportActionBar();
            public void onDrawerClosed( View view ) {
                super.onDrawerClosed( view );

                if( actionBar != null )
                    actionBar.setTitle( "Campus App" );}

            public void onDrawerOpened( View drawerView ) {
                super.onDrawerOpened( drawerView );
                if( actionBar != null )
                    actionBar.setTitle( "" );
            }
        };

        drawerLayout.setDrawerListener( drawerToggle );

        }
        private class MyDrawerClickListener implements NavigationView.OnNavigationItemSelectedListener {
            @Override
            public boolean onNavigationItemSelected( MenuItem item ) {
                switch ( item.getItemId() ) {
                    case R.id.navigation_lecturers:
                        android.util.Log.wtf( "mgr", "lectuerers" );
                        break;
                    case R.id.navigation_modules:
                        android.util.Log.wtf( "mgr", "modules" );
                        replaceFragment(fm, new ModuleViewPagerFragment());
                        break;
                }
                return false;
            }
        }

    }