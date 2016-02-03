package de.fhws.campusapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import de.fhws.campusapp.R;

public class LecturerDetailViewHolderSubject extends RecyclerView.ViewHolder
{
    private TextView subject;

    public LecturerDetailViewHolderSubject( View itemView, View.OnClickListener listener )
    {
        super( itemView );

        this.subject  = (TextView) itemView.findViewById( R.id.tvSubjectValue );

        this.subject.setOnClickListener( listener );
    }

    public void displaySubject( String subject )
    {
        this.subject.setText( subject );
    }
}
