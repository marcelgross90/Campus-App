package de.fhws.campusapp.network;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fhws.campusapp.entity.Lecturer;

public class LecturerNetwork extends BaseNetwork
{
    private static HashMap<String, Lecturer> lecturerMap = new HashMap<String, Lecturer>();

    public interface FetchAllLecturersListener
    {
        void fetchAllLecturers( List<Lecturer> newLecturers, int totalNumberOfLecturers );
    }

    public LecturerNetwork()
    {
        super("http://193.175.31.146:8080/fiwincoming/api");
    }

    public void fetchAllLecturers( int size, int offset, final FetchAllLecturersListener listener )
    {
        String url = host + "/lecturers?size=" + size + "&offset=" + offset;
        requestAsync(
                new Request(
                        url,
                        METHOD_GET,
                        new String[]{"Accept:application/json"},
                        null
                ),
                new OnResultListener() {
                    @Override
                    public void onResultListener(Response response) {
                        List<Lecturer> lecturers = new ArrayList<>();
                        if (successfulRequest(response.getCode())) {
                            Type listType = new TypeToken<List<Lecturer>>() {
                            }.getType();
                            lecturers = gson.fromJson(response.getString(), listType);
                            for (int i = 0; i < lecturers.size(); i++)
                            {
                                lecturerMap.put( lecturers.get( i ).getFullName(), lecturers.get( i ) );
                            }
                            int num = getTotalNumberOfLecturers(response);
                            listener.fetchAllLecturers(lecturers, num);
                        }
                    }
                });
    }

    private int getTotalNumberOfLecturers( Response response )
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

    public static Lecturer getById( String id )
    {
        return lecturerMap.get( id );
    }
}