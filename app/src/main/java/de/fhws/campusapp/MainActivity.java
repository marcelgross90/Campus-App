package de.fhws.campusapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import de.fhws.campusapp.fragment.LecturersFragment;

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

        fm = getSupportFragmentManager();

        initToolbar();

        if( savedInstanceState == null ) {
            replaceFragment( fm, new LecturersFragment() );
        }

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
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
}