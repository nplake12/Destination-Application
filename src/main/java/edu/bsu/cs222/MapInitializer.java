package edu.bsu.cs222;

import com.lynden.gmapsfx.javascript.object.*;

public class MapInitializer {

    public MapOptions initializeGoogleMap(double latitude, double longitude){

        MapOptions mapOptions = new MapOptions();

        mapOptions.center(new LatLong(latitude,longitude))
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .zoom(12);

        return mapOptions;
    }

    public Marker addMarker(double latitude, double longitude){
        MarkerOptions markerOptions = new MarkerOptions();

        markerOptions.position( new LatLong(latitude, longitude) )
                .visible(Boolean.TRUE)
                .label("Place")
                .title("My Marker");

        Marker marker = new Marker( markerOptions );

        return marker;
    }



}
