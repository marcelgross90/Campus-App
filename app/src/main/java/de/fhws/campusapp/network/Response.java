package de.fhws.campusapp.network;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

class Response
{
    private final int code;
    private final byte data[];
    private final Map<String, List<String>> headers; // TODO: Think about the chosen data structure

    Response( int code, byte data[], Map<String, List<String>> headers )
    {
        this.code = code;
        this.data = data;
        this.headers = headers;
    }

    Map<String, List<String>> getHeaders() {
        return headers;
    }

    int getCode() {
        return code;
    }

    byte[] getData() {
        return data;
    }

    String getString()
    {
        try
        {
            return new String( data, "UTF-8" );
        }
        catch ( UnsupportedEncodingException e )
        {
            return null;
        }
    }
}
