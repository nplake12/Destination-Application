package edu.bsu.cs222;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class PlaceParserTest {

    @Test
    public void testParse_firstName() throws IOException{
        PlaceParser parser = new PlaceParser();
        List<Place> places = parser.parse("Muncie,IN");
        Assert.assertEquals("SUBWAY®Restaurants", places.get(0).getName());
    }

    @Test
    public void testParse_firstRating() throws IOException{
        PlaceParser parser = new PlaceParser();
        List<Place> places = parser.parse("Muncie,IN");
        Assert.assertEquals("4.4", places.get(0).getRating());
    }

    @Test
    public void testParse_firstAddress() throws IOException{
        PlaceParser parser = new PlaceParser();
        List<Place> places = parser.parse("Muncie,IN");
        Assert.assertEquals("308 North Walnut Street, Muncie", places.get(0).getAddress());
    }
}
