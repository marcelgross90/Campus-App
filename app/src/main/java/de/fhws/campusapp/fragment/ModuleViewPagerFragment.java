package de.fhws.campusapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import de.fhws.campusapp.MainActivity;
import de.fhws.campusapp.R;
import de.fhws.campusapp.adapter.ModulesPagerAdapter;

public class ModuleViewPagerFragment extends Fragment implements SearchView.OnQueryTextListener {

    private ViewPager viewPager;
    @Override
    public void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setHasOptionsMenu( true );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        View pagerView = inflater.inflate(R.layout.fragment_module_view_pager, container, false);
        viewPager = (ViewPager) pagerView.findViewById(R.id.module_view_pager);

        viewPager.setAdapter( new ModulesPagerAdapter( getChildFragmentManager(), getActivity() ) );

        return pagerView;
    }

    @Override
    public void onCreateOptionsMenu( Menu menu, MenuInflater inflater ) {
        inflater.inflate( R.menu.module_menu, menu );

        MenuItem searchItem = menu.findItem( R.id.action_search );

        SearchView searchView;
        if( searchItem != null ) {
            searchView = (SearchView) searchItem.getActionView();
            searchView.setOnQueryTextListener(this);
        }
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        switch ( item.getItemId() ) {
            case R.id.action_filter:
                MainActivity.startDialogFragment( getFragmentManager(), new CoursePickerFragment() );
                break;
        }

        return super.onOptionsItemSelected( item );
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        ((ModulesPagerAdapter)viewPager.getAdapter()).filter(s);
        return true;
    }
}
