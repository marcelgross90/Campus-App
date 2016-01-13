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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.fhws.campusapp.R;
import de.fhws.campusapp.entity.Lecturer;
import de.fhws.campusapp.network.LecturerNetwork;

public class LecturerAdapter extends RecyclerView.Adapter<LecturerAdapter.ViewHolder> {

    private ArrayList<Lecturer> filteredLectures;
    private final List<Lecturer> allLecturers;
    private final int rowLayout;
    private final Context context;
    private final OnLecturerClickListener listener;
    private final ActivateProgressBar progressBarListener;
    private Handler handler;

    public interface OnLecturerClickListener {
        void onLecturerClick( int position );
    }

    public interface ActivateProgressBar {
        void showProgressBar( boolean activate );
    }

    public void filter( String searchString ) {


        if( !searchString.isEmpty() ) {

            for ( Lecturer currentLecturer : allLecturers ) {
                String lecturerName = currentLecturer.getLastName().toLowerCase();
                if( !lecturerName.contains( searchString.toLowerCase() ) ) {
                    if( filteredLectures.contains( currentLecturer ) ) {
                        int index = filteredLectures.indexOf( currentLecturer );
                        filteredLectures.remove( index );
                        notifyItemRemoved( index );
                    }
                } else {
                    if( !filteredLectures.contains( currentLecturer ) ) {
                        filteredLectures.add( filteredLectures.size(), currentLecturer );
                        notifyItemInserted( filteredLectures.size() );

                    }
                }
            }
        } else {
            filteredLectures = new ArrayList<>( allLecturers );
            notifyDataSetChanged();
        }
    }

    public LecturerAdapter(
            ArrayList<Lecturer> lecturers,
            int rowLayout,
            Context context,
            OnLecturerClickListener listener,
            ActivateProgressBar progressBarListener ) {
        this.filteredLectures = lecturers;
        this.rowLayout = rowLayout;
        this.context = context;
        this.listener = listener;
//        this.allLecturers = LecturerData.getInstance().getLecturers();
        this.allLecturers = (List<Lecturer>) lecturers.clone();
        this.handler = new Handler();
        this.progressBarListener = progressBarListener;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        View v = LayoutInflater.from( parent.getContext() ).inflate( rowLayout, parent, false );
        return new ViewHolder( v );
    }


    @Override
    public void onBindViewHolder( final ViewHolder holder, final int position ) {
        if( position >= getItemCount() - 1 ) {
            loadMoreData( position + 1, getItemCount() );
        }
        holder.assignData( filteredLectures.get( position ) );
    }

    @Override
    public int getItemCount() {
        return filteredLectures == null ? 0 : filteredLectures.size();
    }

    private void loadMoreData( final int startIndex, final int size ) {
        new Thread() {
            public void run() {
                progressBarListener.showProgressBar( true );
                waitForSomeTime( 1000 );
                loadData( startIndex, size );
                informAdapter();
            }
        }.start();
    }

    private void loadData(int startIndex, int size) {
        LecturerNetwork network = new LecturerNetwork();
        network.fetchAllLecturers( size, startIndex, new LecturerNetwork.FetchAllLecturersListener() {
            @Override
            public void fetchAllLecturers( List<Lecturer> newLecturers, int totalNumber ) {
                android.util.Log.wtf( "mgr", newLecturers.size() + "" );
                filteredLectures.addAll( newLecturers );
                progressBarListener.showProgressBar( false );
            }
        } );
    }

    private void informAdapter() {
        handler.post( new Runnable() {
            public void run() {
                notifyDataSetChanged();
            }
        } );
    }

    private void waitForSomeTime( long time ) {
        try {
            Thread.sleep( time );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView name;
        public final ImageView pic;
        public final CardView cardView;
        final View view;

        public ViewHolder( final View itemView ) {
            super( itemView );
            name = (TextView) itemView.findViewById( R.id.name_tv );
            pic = (ImageView) itemView.findViewById( R.id.imageView );
            cardView = (CardView) itemView.findViewById( R.id.card );
            view = itemView;
        }

        public void assignData( Lecturer lecturer ) {
            view.setOnClickListener( this );
            name.setText( String.format( "%s %s", lecturer.getName(), lecturer.getLastName() ) );
            final Uri uri = Uri.parse( lecturer.getPictureUrl() );
            Picasso.with( context ).load( uri ).placeholder( R.mipmap.ic_launcher ).into( pic );
        }

        @Override
        public void onClick( View v ) {
            int pos = getAdapterPosition();
            if( listener != null ) {
                listener.onLecturerClick( pos );
            }
        }
    }
}