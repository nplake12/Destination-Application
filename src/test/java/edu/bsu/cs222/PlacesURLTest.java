package edu.bsu.cs222;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class PlacesURLTest {

    @Test
    public void PlacesURL_setPlacesURLCall() throws IOException{
        PlacesURL url = new PlacesURL.Builder()
                .setCoordinates("Muncie,IN")
                .setRadius("500")
                .setPlaceType("Restaurant")
                .setPlacesURLCall()
                .build();
        Assert.assertEquals("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=40.1933767,-85.3863599&radius=500&type=restaurant&key=AIzaSyAlmnrNiNrVybKz8JbYjOzuxZrGVRI9-Gg",
                            url.getPlacesURLCall());
    }

}
