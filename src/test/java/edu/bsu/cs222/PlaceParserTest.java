package edu.bsu.cs222;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class PlaceParserTest {

    private LinkedList<String> placesURLParameters = new LinkedList<String>(Arrays.asList("Muncie,IN", "500", "Restaurant"));


    @Test
    public void testParse_firstName() throws IOException{
        PlaceParser parser = new PlaceParser();
        parser.constructURL(placesURLParameters);
        List<Place> places = parser.parse();
        Assert.assertEquals("SUBWAY®Restaurants", places.get(0).getName());
    }

    @Test
    public void testParse_firstRating() throws IOException{
        PlaceParser parser = new PlaceParser();
        parser.constructURL(placesURLParameters);
        List<Place> places = parser.parse();
        Assert.assertEquals("4.4", places.get(0).getRating());
    }

    @Test
    public void testParse_firstAddress() throws IOException{
        PlaceParser parser = new PlaceParser();
        parser.constructURL(placesURLParameters);
        List<Place> places = parser.parse();
        Assert.assertEquals("308 North Walnut Street, Muncie", places.get(0).getAddress());
    }

    @Test
    public void testParse_firstDistance() throws IOException{
        PlaceParser parser = new PlaceParser();
        parser.constructURL(placesURLParameters);
        List<Place> places = parser.parse();
        Assert.assertEquals("0.2 mi", places.get(0).getDistance());
    }
}
