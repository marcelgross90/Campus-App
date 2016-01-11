package de.fhws.campusapp.network;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class BaseNetwork {
    public interface OnResultListener {
        void onResultListener( Response response );
    }

    static final String METHOD_DELETE = "DELETE";
    static final String METHOD_GET = "GET";
    static final String METHOD_POST = "POST";
    static final String METHOD_PUT = "PUT";

    final String host;
    final Gson gson = new Gson();

    private static final int MAXIMUM_RESPONSE_SIZE = 1048576;

    BaseNetwork( String host ) {
        this.host = host;
    }

    static void requestAsync( final Request request, final OnResultListener listener ) {
        new AsyncTask<Request, Void, Response>() {
            @Override
            protected Response doInBackground( Request... params ) {
                HttpURLConnection connection = null;

                try {
                    connection = (HttpURLConnection)
                            (new URL( params[0].getUrl() )).openConnection();

                    if( params[0].getMethod() != null )
                        connection.setRequestMethod( params[0].getMethod() );

                    for ( int n = params[0].getHeaders() != null
                            ? params[0].getHeaders().length : 0;
                          n-- > 0; ) {
                        String tokens[] = params[0].getHeaders()[n].split( ":" );

                        if( tokens.length != 2 )
                            continue;

                        connection.setRequestProperty( tokens[0], tokens[1] );
                    }

                    if( params[0].getData() != null ) {
                        connection.setDoOutput( true );

                        OutputStreamWriter writer =
                                new OutputStreamWriter( connection.getOutputStream() );
                        writer.write( params[0].getData() );
                        writer.flush();
                    }

                    return new Response(
                            connection.getResponseCode(),
                            readResponse(
                                    new BufferedInputStream( connection.getInputStream() ) ),
                            connection.getHeaderFields()
                    );
                } catch ( IOException e ) {
                    // fall through
                } finally {
                    if( connection != null )
                        connection.disconnect();
                }

                return new Response(
                        500, new byte[]{}, null);
            }

            @Override
            protected void onPostExecute( Response response ) {
                super.onPostExecute( response );
                listener.onResultListener( response );
            }
        }.execute( request );
    }

    static boolean successfulRequest( int statusCode ) {
        return statusCode >= 200 && statusCode < 300;
    }

    private static byte[] readResponse( InputStream in ) throws IOException {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        byte buffer[] = new byte[4096];

        for ( int bytes, length = 0; (bytes = in.read( buffer )) > -1; )
            if( bytes > 0 ) {
                data.write( buffer, 0, bytes );
                length += bytes;

                if( length > MAXIMUM_RESPONSE_SIZE )
                    return null;
            }

        return data.toByteArray();
    }

    protected static class Response {
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

    static class Request {
        private String url;
        private String method;
        private String[] headers;
        private String data;

        public Request( String url, String method, String[] headers, String data ) {
            this.url = url;
            this.method = method;
            this.headers = headers;
            this.data = data;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl( String url ) {
            this.url = url;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod( String method ) {
            this.method = method;
        }

        public String[] getHeaders() {
            return headers;
        }

        public void setHeaders( String[] headers ) {
            this.headers = headers;
        }

        public String getData() {
            return data;
        }

        public void setData( String data ) {
            this.data = data;
        }
    }
}