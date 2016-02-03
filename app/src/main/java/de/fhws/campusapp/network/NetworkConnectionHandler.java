package de.fhws.campusapp.network;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

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
            Response result = new Response( HttpURLConnection.HTTP_INTERNAL_ERROR, new byte[]{}, null );    // TODO: Is fake Response with byte[] really necessary? What about null?

            try
            {
                Request request = params[0];
                URL url = new URL( request.getUrl() );
                connection = (HttpURLConnection) url.openConnection();

                handleRequest( connection, request );

                result = getResponse( connection );
            }
            catch ( Exception e )
            {
                listener.onError();
                e.printStackTrace();
            }
            finally
            {
                if( connection != null )
                {
                    connection.disconnect();
                }
            }
            return result;
        }

        private void handleRequest(HttpURLConnection connection, Request request) throws Exception
        {
            setHTTPMethod( connection, request );
            addHeaders( connection, request );
            addData( connection, request );
        }

        private void setHTTPMethod(HttpURLConnection connection, Request request) throws Exception
        {
            if( request.getMethod() != null )
            {
                connection.setRequestMethod( request.getMethod() );
            }
        }

        private void addHeaders( HttpURLConnection connection, Request request )
        {
            String[] headers = request.getHeaders();

            if ( headers != null )
            {
                for ( String header : headers )
                {
                    String[] tokens = header.split( ":" );

                    if ( tokens.length == 2 )
                    {
                        connection.setRequestProperty( tokens[0], tokens[1] );
                    }
                }
            }
        }

        private void addData( HttpURLConnection connection, Request request ) throws Exception
        {
            if( request.getData() != null )
            {
                connection.setDoOutput( true );
                OutputStreamWriter writer = new OutputStreamWriter( connection.getOutputStream() );
                writer.write( request.getData() );
                writer.flush();
            }
        }

        private Response getResponse( HttpURLConnection connection ) throws Exception
        {
            return new Response( connection.getResponseCode(),
                       readResponse( new BufferedInputStream( connection.getInputStream() ) ),
                       connection.getHeaderFields() );
        }

        private static byte[] readResponse( InputStream in ) throws IOException
        {
            ByteArrayOutputStream data = new ByteArrayOutputStream();
            byte[] buffer = new byte[NetworkSettings.BLOCK_SIZE];

            int totalSize = 0;
            int bytes = in.read( buffer );

            while( bytes > -1 )
            {
                if (bytes > 0) // TODO: Really?
                {
                    data.write( buffer, 0, bytes );
                    totalSize = totalSize + bytes;

                    if ( totalSize > NetworkSettings.MAXIMUM_RESPONSE_SIZE )
                    {
                        return null;
                    }
                }
                bytes = in.read( buffer );
            }
            return data.toByteArray();
        }

        @Override
        protected void onPostExecute( Response response )
        {
            super.onPostExecute( response );
            listener.onSuccess( response );
        }
    }
}