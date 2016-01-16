package de.fhws.campusapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.fhws.campusapp.R;
import de.fhws.campusapp.entity.Lecturer;

public class LecturerDetailAdapter extends RecyclerView.Adapter
{
    private Lecturer lecturer;

    public LecturerDetailAdapter( Lecturer lecturer )
    {
        this.lecturer = lecturer;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType )
    {
        RecyclerView.ViewHolder result = null;

        if ( viewType == 0 )
        {
            // int layout = R.layout.card_module_detail_general;
            // View v = LayoutInflater.from(parent.getContext()).inflate( layout, parent, false );
            // result = new ModuleDetailViewHolderGeneral( v );
        }
        else if ( viewType == 1 )
        {
        }
        else if ( viewType == 2 )
        {
        }
        else if ( viewType == 3 )
        {
        }
        return result;
    }

    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, int position )
    {
        if( position == 0 )
        {
            // ModuleDetailViewHolderGeneral viewHolder = (ModuleDetailViewHolderGeneral) holder;
            // viewHolder.displayLectureType( module.getLectureType() );
            // viewHolder.displayLecturers( module.getLecturers() );
            // viewHolder.displayStudy( module.getStudy() );
            // viewHolder.displayYear( String.valueOf( module.getLevel() ) );
        }
        else if( position == 1 )
        {
        }
    }

    @Override
    public int getItemCount()
    {
        return 0;
    }

    @Override
    public int getItemViewType( int position )
    {
        return position;
    }
}