package de.fhws.campusapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import de.fhws.campusapp.R;

public class NetworkChangeReceiver extends BroadcastReceiver {

    public interface NetworkListener {
        void networkAvailable();
        void networkNotAvailable();
    }

    private static NetworkChangeReceiver instance;
    private NetworkListener listener;


    public void setListener( NetworkListener listener ) {
        this.listener = listener;
    }

    public static NetworkChangeReceiver getInstance() {
        return instance == null ? instance = new NetworkChangeReceiver() : instance;
    }

    public static NetworkChangeReceiver getInstance(NetworkListener listener) {
        NetworkChangeReceiver receiver = instance == null ? instance = new NetworkChangeReceiver() : instance;
        receiver.setListener( listener );
        return receiver;
    }
    @Override
    public void onReceive( Context context, Intent intent ) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if( activeNetwork != null ) {
            listener.networkAvailable();
        } else {
            listener.networkNotAvailable();
        }
    }
}