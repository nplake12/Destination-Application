package edu.bsu.cs222;

import org.junit.Assert;
import org.junit.Test;

public class TypeMapTest {

    @Test
    public void testTypeMap_getTypeParameter_BowlingAlley(){
        TypeMap typeMap = new TypeMap();
        Assert.assertEquals("bowling_alley", typeMap.getTypeParameter("Bowling Alley"));
    }
}
