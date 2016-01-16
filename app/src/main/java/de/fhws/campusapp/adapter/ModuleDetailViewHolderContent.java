package de.fhws.campusapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import de.fhws.campusapp.R;

public class ModuleDetailViewHolderContent extends RecyclerView.ViewHolder
{
    private TextView description;
    private TextView goals;

    public ModuleDetailViewHolderContent( View itemView )
    {
        super( itemView );

        this.description = (TextView) itemView.findViewById( R.id.tvDescription      );
        this.goals       = (TextView) itemView.findViewById( R.id.tvGoals            );
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
