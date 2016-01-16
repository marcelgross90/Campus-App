package de.fhws.campusapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.fhws.campusapp.R;

public class ModuleDetailViewHolderEffort extends RecyclerView.ViewHolder
{
    private TextView credits;
    private TextView hoursPerWeek;
    private View     distributionLecture;
    private View     distributionSelf;

    public ModuleDetailViewHolderEffort( View itemView )
    {
        super( itemView );

        this.credits             = (TextView) itemView.findViewById( R.id.tvCreditsValue      );
        this.hoursPerWeek        = (TextView) itemView.findViewById( R.id.tvHoursPerWeekValue );
        this.distributionLecture =            itemView.findViewById( R.id.distributionLecture );
        this.distributionSelf    =            itemView.findViewById( R.id.distributionSelf    );
    }

    public void displayCredits( String credits )
    {
        this.credits.setText( credits );
    }

    public void displayHoursPerWeek( String hoursPerWeek )
    {
        this.hoursPerWeek.setText( hoursPerWeek );
    }

    public void displayDistribution( int timeLecture, int timeSelf )
    {
        float timeSum        = timeLecture + timeSelf;
        float percentLecture = timeLecture / timeSum * 100;
        float percentSelf    = timeSum - percentLecture;

        distributionLecture.setLayoutParams( new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, percentSelf ) );
        distributionSelf.setLayoutParams( new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, percentLecture ) );
    }
}