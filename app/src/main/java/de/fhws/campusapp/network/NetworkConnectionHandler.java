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

public class NetworkConnectionHandler
{
    interface OnResponseListener
    {
        void onSuccess( Response response );
        void onError();
    }

    public static void requestAsync( Request request, OnResponseListener listener )
    {
        new NetworkConnectionTask( listener ).execute( request );
    }

    private static class NetworkConnectionTask extends AsyncTask<Request, Void, Response>
    {
        private OnResponseListener listener;

        NetworkConnectionTask( OnResponseListener listener )
        {
            this.listener = listener;
        }

        @Override
        protected Response doInBackground( Request... params )
        {
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
            listener.onSuccess(response);
        }
    }


// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++



    final Gson gson = new Gson();


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

                if( length > NetworkSettings.MAXIMUM_RESPONSE_SIZE )
                    return null;
            }

        return data.toByteArray();
    }

}