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
    private View.OnClickListener listener;

    public LecturerDetailAdapter( Lecturer lecturer, View.OnClickListener listener )
    {
        this.lecturer = lecturer;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType )
    {
        RecyclerView.ViewHolder result = null;

        if ( viewType == 0 )
        {
            int layout = R.layout.card_lecturer_detail_subject;
            View v = LayoutInflater.from( parent.getContext() ).inflate(layout, parent, false);
            result = new LecturerDetailViewHolderSubject( v );
        }
        else if ( viewType == 1 )
        {
            int layout = R.layout.card_lecturer_detail_office;
            View v = LayoutInflater.from( parent.getContext() ).inflate( layout, parent, false );
            result = new LecturerDetailViewHolderOffice( v, listener );
        }
        else if ( viewType == 2 )
        {
            int layout = R.layout.card_lecturer_detail_contact;
            View v = LayoutInflater.from( parent.getContext() ).inflate( layout, parent, false );
            result = new LecturerDetailViewHolderContact( v, listener );
        }
        return result;
    }

    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, int position )
    {
        if( position == 0 )
        {
            LecturerDetailViewHolderSubject viewHolder = (LecturerDetailViewHolderSubject) holder;
            viewHolder.displaySubject( lecturer.getSubject() );
        }
        else if( position == 1 )
        {
            LecturerDetailViewHolderOffice viewHolder = (LecturerDetailViewHolderOffice) holder;
            viewHolder.displayRoom( lecturer.getOfficeNumber() );
            viewHolder.displayAddress( lecturer.getStreet() + "\n" + lecturer.getTown() );
        }
        else if( position == 2 )
        {
            LecturerDetailViewHolderContact viewHolder = (LecturerDetailViewHolderContact) holder;
            viewHolder.displayEmail( lecturer.getEmail() );
            viewHolder.displayPhone(lecturer.getPhoneNumber());
            viewHolder.displayWebsite( lecturer.getWebsite() );
        }
    }

    @Override
    public int getItemCount()
    {
        return 3;
    }

    @Override
    public int getItemViewType( int position )
    {
        return position;
    }
}