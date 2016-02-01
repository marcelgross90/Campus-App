package de.fhws.campusapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

import de.fhws.campusapp.R;
import de.fhws.campusapp.entity.Lecturer;
import de.fhws.campusapp.network.LecturerNetwork;
import de.fhws.campusapp.receiver.NetworkChangeReceiver;

public class LecturerAdapter extends RecyclerView.Adapter<LecturerAdapter.ViewHolder> implements NetworkChangeReceiver.NetworkListener {
    private List<Lecturer> filteredLectures;
    private final List<Lecturer> allLecturers;
    private final int rowLayout;
    private final Context context;
    private final OnLecturerClickListener listener;
    private final ActivateProgressBar progressBarListener;
    private Handler handler;
    private NetworkChangeReceiver receiver;

    @Override
    public void networkAvailable() {
        downloadData();
    }

    @Override
    public void networkNotAvailable() {
        Toast.makeText( context, R.string.noInternet, Toast.LENGTH_SHORT ).show();
    }

    public interface OnLecturerClickListener {
        void onLecturerClick( String fullName, ImageView view );
    }

    public interface ActivateProgressBar {
        void showProgressBar( boolean activate );
    }

    public void filter( String searchTerm ) {
        if( searchTerm == null ) {
            notifyDataSetChanged();
            return;
        }
        if( !searchTerm.isEmpty() ) {
            for ( Lecturer lecturer : allLecturers ) {
                String lecturerName = lecturer.getLastName().toLowerCase();
                int index = filteredLectures.indexOf( lecturer );
                if( !lecturerName.startsWith( searchTerm.toLowerCase() ) ) {
                    if( index != -1 ) {
                        filteredLectures.remove( index );
                        notifyItemRemoved( index );
                    }
                } else {
                    if( index == -1 ) {
                        filteredLectures.add( lecturer );
                        notifyItemInserted( filteredLectures.size() );

                    }
                }
            }
        } else {
            filteredLectures = (List<Lecturer>) ((LinkedList<Lecturer>) allLecturers).clone();
            notifyDataSetChanged();
        }
    }

    public LecturerAdapter(
            int rowLayout,
            Context context,
            OnLecturerClickListener listener,
            ActivateProgressBar progressBarListener ) {
        this.filteredLectures = new LinkedList<>();
        this.allLecturers = new LinkedList<>();
        this.rowLayout = rowLayout;
        this.context = context;
        this.listener = listener;
        this.handler = new Handler();
        this.progressBarListener = progressBarListener;
        receiver = NetworkChangeReceiver.getInstance( this );
        downloadData();
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        View v = LayoutInflater.from( parent.getContext() ).inflate( rowLayout, parent, false );
        return new ViewHolder( v );
    }


    @Override
    public void onBindViewHolder( final ViewHolder holder, final int position ) {
        holder.assignData( filteredLectures.get( position ) );
    }

    @Override
    public int getItemCount() {
        return filteredLectures == null ? 0 : filteredLectures.size();
    }


    private void downloadData() {
        progressBarListener.showProgressBar( true );
        LecturerNetwork network = new LecturerNetwork();
        network.fetchAllLecturers( 50, 0, new LecturerNetwork.FetchAllLecturersListener() {
            @Override
            public void fetchAllLecturers( List<Lecturer> newLecturers, int totalNumber ) {
                filteredLectures = newLecturers;
                allLecturers.addAll( newLecturers );
                progressBarListener.showProgressBar( false );
                notifyDataSetChanged();
            }
        } );

    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView name;
        public final ImageView pic;
        public final CardView cardView;
        public Lecturer lecturer;
        final View view;

        public ViewHolder( final View itemView ) {
            super( itemView );
            name = (TextView) itemView.findViewById( R.id.name_tv );
            pic = (ImageView) itemView.findViewById( R.id.imageViewLecturer );
            cardView = (CardView) itemView.findViewById( R.id.card );
            view = itemView;
        }

        public void assignData( Lecturer lecturer ) {
            this.lecturer = lecturer;
            view.setOnClickListener( this );
            name.setText( String.format( "%s %s", lecturer.getName(), lecturer.getLastName() ) );
            final Uri uri = Uri.parse( lecturer.getPictureUrl() );
            Picasso.with( context ).load( uri ).into( pic );
        }

        @Override
        public void onClick( View v ) {
            int pos = getAdapterPosition();
            if( listener != null ) {
                listener.onLecturerClick( lecturer.getFullName(), pic );
            }
        }
    }
}