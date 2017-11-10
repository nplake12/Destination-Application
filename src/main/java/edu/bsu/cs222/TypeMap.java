package edu.bsu.cs222;

import java.util.HashMap;

public class TypeMap {

    public String getTypeParameter(String placeType){
        HashMap<String, String> placeTypes = initializeTypeParameterHashMap();
        return placeTypes.get(placeType);
    }

    public HashMap<String, String> initializeTypeParameterHashMap(){
        HashMap<String, String> placeTypes = new HashMap<String, String>();
        placeTypes.put("ATM","atm");
        placeTypes.put("Bank","bank");
        placeTypes.put("Bar","bar");
        placeTypes.put("Bowling Alley","bowling_alley");
        placeTypes.put("Clothing Store","clothing_store");
        placeTypes.put("Doctor","doctor");
        placeTypes.put("Gas Station","gas_station");
        placeTypes.put("Hospital","hospital");
        placeTypes.put("Lodging","lodging");
        placeTypes.put("Park","park");
        placeTypes.put("Parking","parking");
        placeTypes.put("Post Office","post_office");
        placeTypes.put("Restaurant","restaurant");
        placeTypes.put("School","school");
        placeTypes.put("Shopping Mall","shopping_mall");
        placeTypes.put("University","university");
        placeTypes.put("Zoo","zoo");
        return placeTypes;
    }
}
