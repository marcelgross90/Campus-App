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
    private Module module;

    public ModuleDetailFragment()
    {
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
    {
        View view = inflater.inflate( R.layout.fragment_module_detail, container, false );

        if ( savedInstanceState != null )
        {
            String lvId = savedInstanceState.getString( "lvId" );
            module = ModuleNetwork.getByLvId( lvId );
        }
        getActivity().setTitle( module.getLvnameGerman() );
        RecyclerView recyclerView = (RecyclerView) view.findViewById( R.id.rvModuleDetails );
        recyclerView.setLayoutManager( new LinearLayoutManager( getContext() ) );
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter( new ModuleDetailAdapter( module ) );

        return view;
    }

    @Override
    public void setArguments( Bundle args )
    {
        super.setArguments(args);

        String lvId = args.getString( "lvId" );
        module = ModuleNetwork.getByLvId( lvId );
    }

    @Override
    public void onSaveInstanceState( Bundle outState )
    {
        super.onSaveInstanceState( outState );
        outState.putString( "lvId", module.getLvid() );
    }
}