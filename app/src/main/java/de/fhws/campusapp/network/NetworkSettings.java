package de.fhws.campusapp.network;

public class NetworkSettings
{
    public static final String API_URL_LECTURERS = "http://193.175.31.146:8080/fiwincoming/api";
    public static final String API_URL_MODULES   = "http://193.175.31.146:8080/fiwincoming/api";

    public static final String METHOD_POST   = "POST";
    public static final String METHOD_GET    = "GET";
    public static final String METHOD_PUT    = "PUT";
    public static final String METHOD_DELETE = "DELETE";

    public static final int MAXIMUM_RESPONSE_SIZE = 1048576;
    public static final int BUFFER_SIZE           = 4096;
}