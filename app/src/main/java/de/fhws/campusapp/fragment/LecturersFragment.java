package de.fhws.campusapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.fhws.campusapp.MainActivity;
import de.fhws.campusapp.R;
import de.fhws.campusapp.adapter.LecturerAdapter;
import de.fhws.campusapp.entity.Lecturer;
import de.fhws.campusapp.network.LecturerNetwork;

public class LecturersFragment extends Fragment implements
        LecturerAdapter.OnLecturerClickListener,
        LecturerAdapter.ActivateProgressBar,
        SearchView.OnQueryTextListener,
        SearchView.OnCloseListener {

    private ProgressBar progressBar;
    private LecturerAdapter adapter;

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setHasOptionsMenu( true );
        setRetainInstance( true );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        View view = inflater.inflate( R.layout.fragment_lecturers, container, false );

        getActivity().setTitle( getString( R.string.app_name ) );

        final RecyclerView recyclerView = (RecyclerView) view.findViewById( R.id.list_rv );
        progressBar = (ProgressBar) view.findViewById( R.id.progressBar );

        final StaggeredGridLayoutManager llm = new StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL );

        recyclerView.setLayoutManager( llm );
        recyclerView.setItemAnimator( new DefaultItemAnimator() );
        LecturerNetwork network = new LecturerNetwork();
        showProgressBar( true );
        network.fetchAllLecturers( 20, 0, new LecturerNetwork.FetchAllLecturersListener() {
            @Override
            public void fetchAllLecturers( List<Lecturer> newLecturers, int totalNumber ) {
                showProgressBar( false );
                if( newLecturers != null ) {
                    adapter = new LecturerAdapter(
                            new ArrayList<>( newLecturers ),
                            R.layout.card_lecturer,
                            getActivity(),
                            LecturersFragment.this,
                            LecturersFragment.this );
                    recyclerView.setAdapter( adapter );
                    adapter.notifyDataSetChanged();
                }
            }
        } );

        return view;
    }

    @Override
    public void onStart() {
        MainActivity.drawerToggle.setDrawerIndicatorEnabled( true );
        super.onStart();
    }

    @Override
    public void showProgressBar( final boolean show ) {
        getActivity().runOnUiThread( new Runnable() {
            public void run() {
                progressBar.setVisibility( show ? View.VISIBLE : View.INVISIBLE );
            }
        } );
    }

    @Override
    public void onLecturerClick( int position ) {
        Bundle bundle = new Bundle();
        bundle.putInt( "position", position );
        LecturerDetailFragment fragment = new LecturerDetailFragment();
        fragment.setArguments( bundle );
        MainActivity.replaceFragment( getFragmentManager(), fragment );
    }

    @Override
    public void onCreateOptionsMenu( Menu menu, MenuInflater inflater ) {
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate( R.menu.search_menu, menu );

        MenuItem searchItem = menu.findItem( R.id.action_search );

        SearchView searchView;
        if( searchItem != null ) {
            searchView = (SearchView) searchItem.getActionView();
            searchView.setOnQueryTextListener( this );
            searchView.setOnCloseListener( this );
        }
    }

    @Override
    public boolean onQueryTextChange( String query ) {
        this.adapter.filter( query );
        return false;
    }

    @Override
    public boolean onQueryTextSubmit( String query ) {
        return false;
    }

    @Override
    public boolean onClose() {
        return false;
    }
}