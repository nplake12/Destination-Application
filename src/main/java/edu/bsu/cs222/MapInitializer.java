package edu.bsu.cs222;

import com.lynden.gmapsfx.javascript.object.*;
import javafx.collections.ObservableList;

import java.util.Arrays;
import java.util.List;

public class MapInitializer{

    private ObservableList<Place> placeObservableList;
    private double latitude;
    private double longitude;

    public MapInitializer(ObservableList<Place> observableList) {
        this.placeObservableList = observableList;
    }

    public MapOptions initializeGoogleMap(String originCoordinates, ObservableList<Place> placeList){
        placeObservableList = placeList;
        separateOriginCoordinates(originCoordinates);

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

    public void addPlacesToGoogleMap(GoogleMap placeGoogleMap){
        for(Place place : placeObservableList){
            placeGoogleMap.addMarker(addPlaceMarker(place));
        }
    }

    public Marker addPlaceMarker(Place place){
        MarkerOptions markerOptions = new MarkerOptions();

        markerOptions.position( new LatLong(Double.parseDouble(place.getLatitude()), Double.parseDouble(place.getLongitude())))
                .visible(Boolean.TRUE)
                .label(place.getName())
                .title("My Marker");

        return new Marker( markerOptions );
    }

    public void separateOriginCoordinates(String originCoordinates){
        List<String> originCoordinateList = Arrays.asList(originCoordinates.split(","));
        latitude = Double.parseDouble(originCoordinateList.get(0));
        longitude = Double.parseDouble(originCoordinateList.get(1));
    }
}