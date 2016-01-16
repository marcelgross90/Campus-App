package de.fhws.campusapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import de.fhws.campusapp.R;

public class LecturerDetailViewHolderOffice extends RecyclerView.ViewHolder
{
    private TextView room;
    private TextView address;

    public LecturerDetailViewHolderOffice( View itemView )
    {
        super( itemView );

        this.room    = (TextView) itemView.findViewById( R.id.tvRoomValue );
        this.address = (TextView) itemView.findViewById( R.id.tvAddressValue );
    }

    public void displayRoom( String room )
    {
        this.room.setText( room );
    }
    public void displayAddress( String address )
    {
        this.address.setText( address );
    }
}
