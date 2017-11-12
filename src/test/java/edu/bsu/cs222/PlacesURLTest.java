package edu.bsu.cs222;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

public class PlacesURLTest {

    @Test
    public void PlacesURL_setPlacesURLCall() throws IOException{
        LinkedList<String> placesURLParameters = new LinkedList<String>(Arrays.asList("Muncie,IN", "500", "Restaurant"));
        PlacesURL url = new PlacesURL.Builder()
                .setCoordinates(placesURLParameters.get(0))
                .setRadius(placesURLParameters.get(1))
                .setPlaceType(placesURLParameters.get(2))
                .setPlacesURLCall()
                .build();
        Assert.assertEquals("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=40.1933767,-85.3863599&radius=500&type=restaurant&key=AIzaSyAlmnrNiNrVybKz8JbYjOzuxZrGVRI9-Gg",
                            url.getPlacesURLCall());
    }

}
