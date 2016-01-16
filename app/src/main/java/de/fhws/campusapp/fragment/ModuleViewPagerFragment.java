package de.fhws.campusapp.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Toast;

import de.fhws.campusapp.R;
import de.fhws.campusapp.adapter.ModulesPagerAdapter;

public class ModuleViewPagerFragment extends Fragment {

    private ViewPager viewPager;
    private boolean[] checkedBoxes = new boolean[3];
    private final String PREFNAME = "event_box";

    @Override
    public void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setHasOptionsMenu( true );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        View pagerView = inflater.inflate(R.layout.fragment_module_view_pager, container, false);
        viewPager = (ViewPager) pagerView.findViewById(R.id.module_view_pager);

        viewPager.setAdapter(new ModulesPagerAdapter(getChildFragmentManager(), getActivity()));

        return pagerView;
    }

    @Override
    public void onStart() {
        loadSavedPreferences();
        super.onStart();
    }

    @Override
    public void onCreateOptionsMenu( Menu menu, MenuInflater inflater ) {
        inflater.inflate( R.menu.module_menu, menu );
        menu.getItem( 0 ).setChecked( checkedBoxes[0] );
        menu.getItem( 1 ).setChecked( checkedBoxes[1] );
        menu.getItem( 2 ).setChecked( checkedBoxes[2] );

    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        item.setChecked( !item.isChecked() );

        switch ( item.getItemId() ) {
            case R.id.action_bin:
                checkedBoxes[0] = item.isChecked();
                break;
            case R.id.action_win:
                checkedBoxes[1] = item.isChecked();
                break;
            case R.id.action_ec:
                checkedBoxes[2] = item.isChecked();
                break;
            default:
                break;
        }

        savePreferences();

        return super.onOptionsItemSelected( item );
    }

    private void savePreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences( getActivity() );
        SharedPreferences.Editor editor = sharedPreferences.edit();

        for ( int i = 0; i < checkedBoxes.length; i++ ) {
            editor.putBoolean( PREFNAME + i, checkedBoxes[i] );
        }

        editor.apply();
    }

    private void loadSavedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences( getActivity() );

        for ( int i = 0; i < checkedBoxes.length; i++ ) {
            checkedBoxes[i] = sharedPreferences.getBoolean( PREFNAME + i, true );
        }
    }
}
