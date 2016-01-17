package de.fhws.campusapp.fragment;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import de.fhws.campusapp.MainActivity;
import de.fhws.campusapp.R;

public class MapFragment extends SupportMapFragment {

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );

        setUpMap();

    }

    private synchronized void setUpMap() {
        checkForGpsEnabled();

        getMapAsync( new OnMapReadyCallback() {
            @Override
            public void onMapReady( GoogleMap googleMap ) {
                try {
                    googleMap.setMyLocationEnabled( true );
                    googleMap.getUiSettings().setMapToolbarEnabled( true );
                } catch ( SecurityException ex ) {
                    //die silently
                }
                if( MainActivity.location != null ) {
                    LatLng latlng = new LatLng( MainActivity.location.getLatitude(), MainActivity.location.getLongitude() );
                    googleMap.moveCamera( CameraUpdateFactory.newLatLng( latlng ) );
                    googleMap.animateCamera( CameraUpdateFactory.newLatLngZoom( latlng, (float) 14.6 ) );
                }
                setMarkers( googleMap );
            }
        } );
    }

    @TargetApi( 19 )
    private void checkForGpsEnabled() {
        ContentResolver contentResolver = getContext().getContentResolver();
        int mode = Settings.Secure.getInt(
                contentResolver, Settings.Secure.LOCATION_MODE, Settings.Secure.LOCATION_MODE_OFF );

        if( mode == Settings.Secure.LOCATION_MODE_OFF ) {
            MainActivity.startDialogFragment( getFragmentManager(), new GpsSettingsDialog() );
        }
    }

    private void setMarkers( GoogleMap map ) {
        for ( POI current : initPois() ) {
            map.addMarker(
                    new MarkerOptions()
                            .position( current.getLatLng() )
                            .title( current.title )
                            .snippet( current.getAdress() )
                            .icon( BitmapDescriptorFactory.fromResource( R.drawable.ic_map_uni ) ) );
        }
    }

    private List<POI> initPois() {
        List<POI> list = new ArrayList<>();
        list.add( new POI( new LatLng( 49.777659, 9.963065 ), "FHWS", "Sanderheinrichsleitenweg 20 Würzburg" ) );
        list.add( new POI( new LatLng( 49.787609, 9.932649 ), "FHWS", "Münzstraße 12 Würzburg" ) );
        list.add( new POI( new LatLng( 49.79988, 9.93182 ), "FHWS", "Röntgenring 8 Würzburg" ) );
        list.add( new POI( new LatLng( 50.045082, 10.210245 ), "FHWS", "Ignaz-Schön-Straße 11 Schweinfurt" ) );

        return list;
    }

    private class POI {
        private LatLng latLng;
        private String title;
        private String adress;

        public POI( LatLng latLng, String title, String adress ) {
            this.latLng = latLng;
            this.title = title;
            this.adress = adress;
        }

        public LatLng getLatLng() {
            return latLng;
        }

        public String getTitle() {
            return title;
        }

        public String getAdress() {
            return adress;
        }
    }


}
