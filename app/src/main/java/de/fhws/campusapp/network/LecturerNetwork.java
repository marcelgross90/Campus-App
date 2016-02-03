package de.fhws.campusapp.network;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fhws.campusapp.entity.Lecturer;

public class LecturerNetwork extends ResponseInterpreter implements NetworkConnectionHandler.OnResponseListener
{
    public interface OnLecturersFetchedListener
    {
        void onLecturersFetched( List<Lecturer> newLecturers, int totalNumberOfLecturers );
    }

    private static HashMap<String, Lecturer> lecturerMap = new HashMap<>();

    private OnLecturersFetchedListener listener;

    public LecturerNetwork( OnLecturersFetchedListener listener )
    {
        this.listener = listener;
    }

    public void fetchAllLecturers( int size, int offset )
    {
        String url = NetworkSettings.getLecturerUrl( size, offset );

        Request request = new Request( url,
                NetworkSettings.METHOD_GET,
                new String[] { "Accept:application/json" },
                null );  // TODO: Think about Request and Response builder

        NetworkConnectionHandler.requestAsync( request, this );
    }

    @Override
    public void onSuccess( Response response )
    {
        if ( NetworkSettings.successfulRequest( response.getCode() ) )
        {
            Type listType = new TypeToken<List<Lecturer>>(){}.getType();
            List<Lecturer> lecturers = gson.fromJson( response.getString(), listType );

            for ( int i = 0; i < lecturers.size(); i++ )
            {
                lecturerMap.put(lecturers.get(i).getFullName(), lecturers.get(i));
            }
            int numberOfLecturers = getTotalNumberOfLecturers( response );  // TODO: Is this really necessary?
            listener.onLecturersFetched( lecturers, numberOfLecturers );
        }
    }

    @Override
    public void onError() {}

    public static Lecturer getById( String id )
    {
        return lecturerMap.get( id );
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


    private static int getTotalNumberOfLecturers( Response response )
    {
        if( response.getCode() != 500 )
        {
            Map<String, List<String>> map = response.getHeaders();
            for (Map.Entry<String, List<String>> entry : map.entrySet())
            {
                if( entry.getKey() != null && entry.getKey().equals( "X-totalnumberofresults" ) )
                {
                    return Integer.valueOf( entry.getValue().get( 0 ));
                }
            }
        }
        return -1;
    }

}