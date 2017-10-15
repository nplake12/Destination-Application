import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class PlaceParserTest {

//    @Test
//    public void testParse() throws IOException{
//        PlaceParser parser = new PlaceParser();
//        String coordinates = parser.parse("Muncie,IN");
//        Assert.assertEquals("40.1933767,-85.3863599", coordinates);
//    }

    @Test
    public void testParse_firstName() throws IOException{
        PlaceParser parser = new PlaceParser();
        List<Place> places = parser.parse("Muncie,IN");
        Assert.assertEquals("The Downtown Farm Stand", places.get(0).getName());
    }

    @Test
    public void testParse_firstRating() throws IOException{
        PlaceParser parser = new PlaceParser();
        List<Place> places = parser.parse("Muncie,IN");
        Assert.assertEquals("4.6", places.get(0).getRating());
    }

    @Test
    public void testParse_firstAddress() throws IOException{
        PlaceParser parser = new PlaceParser();
        List<Place> places = parser.parse("Muncie,IN");
        Assert.assertEquals("125 East Main Street, Muncie", places.get(0).getAddress());
    }
}
