package de.fhws.campusapp;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import de.fhws.campusapp.fragment.LecturersFragment;
import de.fhws.campusapp.fragment.ModuleViewPagerFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fm;
    private DrawerLayout drawerLayout;
    public static ActionBarDrawerToggle drawerToggle;

    public static void replaceFragment( FragmentManager fm, Fragment fragment ) {
        fm.beginTransaction().setCustomAnimations(R.anim.slide_in_left,
                R.anim.slide_out_right,
                R.anim.slide_in_left,
                R.anim.slide_out_right
        )
                .replace(
                        R.id.content_container,
                        fragment, fragment.getClass().getName())
                .addToBackStack(null)
                .commit();
    }

    public static void replaceFragmentPopBackStack( FragmentManager fm, Fragment fragment ) {
        fm.popBackStack();
        replaceFragment( fm, fragment );
    }

    public static void startDialogFragment(FragmentManager fragmentManager, DialogFragment fragment) {
        fragment.show( fragmentManager, "" );
    }

    @Override
    public boolean onSupportNavigateUp() {
        fm.popBackStack();

        return false;
    }

    @Override
    public void onBackPressed() {
        if( drawerLayout.isDrawerOpen( GravityCompat.START ) ) {
            drawerLayout.closeDrawer( GravityCompat.START );
        } else {
            if( fm.getBackStackEntryCount() > 1 )
                super.onBackPressed();
            else
                finish();
        }
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

        drawerLayout = (DrawerLayout) findViewById( R.id.drawer_layout );
        drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close );
        drawerLayout.setDrawerListener( drawerToggle );
        drawerToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById( R.id.navigation_view );
        navigationView.setNavigationItemSelectedListener( new MyDrawerClickListener() );

    }


    private class MyDrawerClickListener implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected( MenuItem item ) {
            switch ( item.getItemId() ) {
                case R.id.navigation_lecturers:
                    replaceFragmentPopBackStack( fm, new LecturersFragment() );
                    break;
                case R.id.navigation_modules:
                    replaceFragmentPopBackStack( fm, new ModuleViewPagerFragment() );
                    break;
            }
            drawerLayout.closeDrawer( GravityCompat.START );
            return true;
        }
    }
}