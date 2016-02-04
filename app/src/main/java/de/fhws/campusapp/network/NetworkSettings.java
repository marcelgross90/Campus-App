package de.fhws.campusapp.network;

import com.google.gson.Gson;

import java.net.HttpURLConnection;

public class NetworkSettings
{
    public static final Gson GSON = new Gson();

    public static final String BASE_URL = "http://193.175.31.146:8080/fiwincoming/api";

    public static final String METHOD_GET    = "GET";
    public static final String METHOD_POST   = "POST";
    public static final String METHOD_PUT    = "PUT";
    public static final String METHOD_DELETE = "DELETE";

    public static final String HEADER_NUMBER_OF_RESULTS = "X-totalnumberofresults";

    public static final int MAXIMUM_RESPONSE_SIZE = 1048576;
    public static final int BLOCK_SIZE            = 4096;

    public static boolean successfulRequest( int statusCode )
    {
        return statusCode >= HttpURLConnection.HTTP_OK && statusCode < HttpURLConnection.HTTP_MULT_CHOICE;
    }

    public static String getLecturerUrl( int size, int offset )
    {
        return BASE_URL + "/lecturers?size=" + size + "&offset=" + offset;
    }

    public static String getModuleUrl( String program, String language, String level, int size, int offset)
    {
        String url = BASE_URL + "/modules?size=" + size + "&offset=" + offset;

        if (program != null)
            url += "&program=" + program;
        if (language != null)
            url += "&lang=" + language;
        if (level != null)
            url += "&level=" + level;
        return url;
    }

}