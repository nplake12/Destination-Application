package edu.bsu.cs222;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class GeocodeParserTest {

    @Test
        public void testParse() throws IOException{
        GeocodeParser parser = new GeocodeParser();
        String coordinates = parser.parse("Muncie,IN");
        Assert.assertEquals("40.1933767,-85.3863599", coordinates);
    }
}
