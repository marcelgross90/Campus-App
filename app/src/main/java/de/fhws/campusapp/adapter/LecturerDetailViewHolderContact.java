package de.fhws.campusapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import de.fhws.campusapp.R;

public class LecturerDetailViewHolderContact extends RecyclerView.ViewHolder
{
    private TextView email;
    private TextView phone;
    private TextView website;

    public LecturerDetailViewHolderContact( View itemView )
    {
        super( itemView );

        this.email   = (TextView) itemView.findViewById( R.id.tvEmailValue   );
        this.phone   = (TextView) itemView.findViewById( R.id.tvPhoneValue   );
        this.website = (TextView) itemView.findViewById( R.id.tvWebsiteValue );
    }

    public void displayEmail( String email )
    {
        this.email.setText( email );
    }

    public void displayPhone( String phone )
    {
        this.phone.setText( phone );
    }

    public void displayWebsite( String website )
    {
        this.website.setText( website );
    }
}