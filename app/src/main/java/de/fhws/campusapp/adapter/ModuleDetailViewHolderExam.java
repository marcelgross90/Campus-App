package de.fhws.campusapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import de.fhws.campusapp.R;

public class ModuleDetailViewHolderExam extends RecyclerView.ViewHolder
{
    private TextView moduleID;
    private TextView examType;
    private TextView literature;

    public ModuleDetailViewHolderExam( View itemView )
    {
        super( itemView );

        this.moduleID   = (TextView) itemView.findViewById( R.id.tvModuleIDValue    );
        this.examType   = (TextView) itemView.findViewById( R.id.tvExamTypeValue   );
        this.literature = (TextView) itemView.findViewById( R.id.tvLiteratureValue );
    }

    public void displayModuleID( String moduleID )
    {
        this.moduleID.setText( moduleID );
    }

    public void displayExamType( String examType )
    {
        this.examType.setText( examType );
    }

    public void displayLiterature( String literature )
    {
        this.literature.setText( literature );
    }
}
