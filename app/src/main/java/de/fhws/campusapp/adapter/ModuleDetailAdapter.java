package de.fhws.campusapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.fhws.campusapp.R;

public class ModuleDetailAdapter extends RecyclerView.Adapter
{
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType )
    {
        RecyclerView.ViewHolder result = null;

        if ( viewType == 0 )
        {
            int layout = 0;// e.g. R.layout.lecturer_detail_card_function;
            View v = LayoutInflater.from( parent.getContext() ).inflate( layout, parent, false );
            result = null; // e.g. new ViewHolderFunction( v );
        }
        else if ( viewType == 1 )
        {
            int layout = 0;// e.g. R.layout.lecturer_detail_card_function;
            View v = LayoutInflater.from( parent.getContext() ).inflate( layout, parent, false );
            result = null; // e.g. new ViewHolderFunction( v );
        }
        else if ( viewType == 2 )
        {
            int layout = 0;// e.g. R.layout.lecturer_detail_card_function;
            View v = LayoutInflater.from( parent.getContext() ).inflate( layout, parent, false );
            result = null; // e.g. new ViewHolderFunction( v );
        }
        else if ( viewType == 3 )
        {
            int layout = 0;// e.g. R.layout.lecturer_detail_card_function;
            View v = LayoutInflater.from( parent.getContext() ).inflate( layout, parent, false );
            result = null; // e.g. new ViewHolderFunction( v );
        }
        return result;
    }

    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, int position )
    {
        if( position == 0 )
        {
            // TODO
            // e.g. ViewHolderFunction holderFunction = (ViewHolderFunction) holder;
            // e.g. holderFunction.tvFunction.setText( lecturer.getSubject() );
        }
    }

    @Override
    public int getItemCount()
    {
        return 4;
    }

    @Override
    public int getItemViewType( int position )
    {
        return position;
    }

    // TODO: implement 4 ViewHolders
}
