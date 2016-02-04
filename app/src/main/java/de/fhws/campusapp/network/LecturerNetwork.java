package de.fhws.campusapp.network;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fhws.campusapp.entity.Lecturer;

public class LecturerNetwork implements NetworkConnectionHandler.OnResponseListener
{
    public interface OnLecturersFetchedListener
    {
        void onLecturersFetched( List<Lecturer> newLecturers, int totalNumberOfLecturers );
    }

    private static HashMap<String, Lecturer> lecturerMap = new HashMap<>();
    private OnLecturersFetchedListener listener;

    public static Lecturer getById( String id )
    {
        return lecturerMap.get( id );
    }

    public LecturerNetwork( OnLecturersFetchedListener listener )
    {
        this.listener = listener;
    }

    public void fetchLecturers(int size, int offset)
    {
        String url = NetworkSettings.getLecturerUrl( size, offset );

        Request request = new Request(
                url,
                NetworkSettings.METHOD_GET,
                new String[] { "Accept:application/json" },
                null );

        NetworkConnectionHandler.requestAsync( request, this );
    }

    @Override
    public void onSuccess( Response response )
    {
        if ( NetworkSettings.successfulRequest( response.getCode() ) )
        {
            Type listType = new TypeToken<List<Lecturer>>(){}.getType();
            List<Lecturer> lecturers = NetworkSettings.GSON.fromJson( response.getString(), listType );

            for ( int i = 0; i < lecturers.size(); i++ )
            {
                lecturerMap.put(lecturers.get(i).getFullName(), lecturers.get(i));
            }
            int numberOfLecturers = getTotalNumberOfLecturers( response );
            listener.onLecturersFetched( lecturers, numberOfLecturers );
        }
    }

    @Override
    public void onError() {}

    private static int getTotalNumberOfLecturers( Response response )
    {
        if( response.getCode() != 500 )
        {
            Map<String, List<String>> map = response.getHeaders();
            for (Map.Entry<String, List<String>> entry : map.entrySet())
            {
                if( entry.getKey() != null && entry.getKey().equals( NetworkSettings.HEADER_NUMBER_OF_RESULTS ) )
                {
                    return Integer.valueOf( entry.getValue().get( 0 ));
                }
            }
        }
        return -1;
    }
}