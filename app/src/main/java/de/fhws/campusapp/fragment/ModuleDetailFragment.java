package de.fhws.campusapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.fhws.campusapp.R;
import de.fhws.campusapp.adapter.ModuleDetailAdapter;
import de.fhws.campusapp.entity.Module;
import de.fhws.campusapp.network.ModuleNetwork;

public class ModuleDetailFragment extends Fragment
{
    // TODO: Add landscape mode

    // components
    private RecyclerView recyclerView;

    // content
    private Module module;

    public ModuleDetailFragment()
    {
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
    {
        View view = inflater.inflate( R.layout.fragment_module_detail, container, false );

        loadComponents( view );
        recyclerView.setLayoutManager( new LinearLayoutManager( getContext() ) );
        recyclerView.setHasFixedSize( true );
        recyclerView.setAdapter( new ModuleDetailAdapter( module ) );

        return view;
    }

    private void loadComponents( View container )
    {
        recyclerView = (RecyclerView) container.findViewById( R.id.rvModuleDetails );
    }

    @Override
    public void setArguments( Bundle args )
    {
        super.setArguments( args );

        String moduleID = args.getString( "lvId" );
        module = ModuleNetwork.getByLvId( moduleID );
    }
}