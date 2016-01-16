package de.fhws.campusapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import de.fhws.campusapp.R;

public class ModuleDetailViewHolderGeneral extends RecyclerView.ViewHolder
{
    private TextView lecturers;
    private TextView study;
    private TextView year;

    public ModuleDetailViewHolderGeneral( View itemView )
    {
        super( itemView );

        this.lecturers = (TextView) itemView.findViewById( R.id.tvLecturersValue );
        this.study     = (TextView) itemView.findViewById( R.id.tvStudyValue     );
        this.year      = (TextView) itemView.findViewById( R.id.tvYearValue      );
    }

    public void displayLecturers( String lecturers )
    {
        this.lecturers.setText( lecturers );
    }

    public void displayStudy( String study )
    {
        this.study.setText( study );
    }

    public void displayYear( String year )
    {
        this.year.setText( year );
    }
}