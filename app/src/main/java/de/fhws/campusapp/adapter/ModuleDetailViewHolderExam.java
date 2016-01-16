package de.fhws.campusapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import de.fhws.campusapp.R;

public class ModuleDetailViewHolderExam extends RecyclerView.ViewHolder
{
    private TextView lectureType;
    private TextView description;
    private TextView goals;

    public ModuleDetailViewHolderExam( View itemView )
    {
        super( itemView );

        this.lectureType = (TextView) itemView.findViewById( R.id.tvLectureTypeValue );
        this.description = (TextView) itemView.findViewById( R.id.tvDescription      );
        this.goals       = (TextView) itemView.findViewById( R.id.tvGoals            );
    }

    public void displayLectureType( String lectureType )
    {
        this.lectureType.setText( lectureType );
    }

    public void displayDescription( String description )
    {
        this.description.setText( description );
    }

    public void displayGoals( String goals )
    {
        this.goals.setText( goals );
    }
}
