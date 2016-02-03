package de.fhws.campusapp.network;

import java.net.HttpURLConnection;

public class NetworkSettings
{
    public static final String METHOD_GET    = "GET";
    public static final String METHOD_POST   = "POST";
    public static final String METHOD_PUT    = "PUT";
    public static final String METHOD_DELETE = "DELETE";

    public static final int MAXIMUM_RESPONSE_SIZE = 1048576;
    public static final int BLOCK_SIZE            = 4096;

    public static boolean successfulRequest( int statusCode )
    {
        return statusCode >= HttpURLConnection.HTTP_OK && statusCode < HttpURLConnection.HTTP_MULT_CHOICE;
    }
}