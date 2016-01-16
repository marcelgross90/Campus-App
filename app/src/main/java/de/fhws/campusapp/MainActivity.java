package de.fhws.campusapp;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.MenuItem;

import de.fhws.campusapp.fragment.LecturersFragment;
import de.fhws.campusapp.fragment.ModuleListFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fm;

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
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        fm = getSupportFragmentManager();

        setUpActionBar();

        if( savedInstanceState == null ) {
            replaceFragment( fm, new LecturersFragment() );
        }

    }

    private void setUpActionBar() {
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close );
        drawer.setDrawerListener( toggle );
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById( R.id.nav_view );
        navigationView.setNavigationItemSelectedListener( new MyDrawerClickListener() );
    }

    private class MyDrawerClickListener implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected( MenuItem item ) {
            switch ( item.getItemId() ) {
                case R.id.navigation_lecturers:
                    LecturersFragment fragment = new LecturersFragment();
                    MainActivity.replaceFragmentPopBackStack( fm, fragment );
                    break;
                case R.id.navigation_modules:
                    android.util.Log.wtf( "mgr", "modules" );
                    ModuleListFragment moduleListFragment = new ModuleListFragment();
                    MainActivity.replaceFragmentPopBackStack( fm, moduleListFragment );
                    break;
            }
            DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
            drawer.closeDrawer( GravityCompat.START );
            return true;
        }
    }

}