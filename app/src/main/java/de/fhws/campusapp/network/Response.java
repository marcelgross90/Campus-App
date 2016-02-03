package de.fhws.campusapp.network;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

class Response
{
    private final int code;
    private final byte data[];
    private final Map<String, List<String>> headers;

    public Response( int code, byte data[], Map<String, List<String>> headers ) {
        this.code = code;
        this.data = data;
        this.headers = headers;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public int getCode() {
        return code;
    }

    public byte[] getData() {
        return data;
    }

    public String getString() {
        try {
            return new String( data, "UTF-8" );
        } catch ( UnsupportedEncodingException e ) {
            return null;
        }
    }
}
