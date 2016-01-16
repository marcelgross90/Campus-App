package de.fhws.campusapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import de.fhws.campusapp.R;

public class ModuleDetailViewHolderEffort extends RecyclerView.ViewHolder
{
    private TextView credits;
    private TextView hoursPerWeek;

    public ModuleDetailViewHolderEffort( View itemView )
    {
        super( itemView );

        this.credits      = (TextView) itemView.findViewById( R.id.tvCreditsValue          );
        this.hoursPerWeek = (TextView) itemView.findViewById( R.id.tvHoursPerWeekValue );
    }

    public void displayCredits( String credits )
    {
        this.credits.setText( credits );
    }

    public void displayHoursPerWeek( String hoursPerWeek )
    {
        this.hoursPerWeek.setText( hoursPerWeek );
    }

    // TODO: add control of distribution views
}