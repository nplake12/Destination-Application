import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class PlaceParserTest {

    @Test
    public void testParse() throws IOException{
        PlaceParser parser = new PlaceParser();
        String coordinates = parser.parse("Muncie,IN");
        Assert.assertEquals("40.1933767,-85.3863599", coordinates);
    }
}
