package edu.bsu.cs222;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;

public class PlaceParser {

    public List<Place> parse(String location) throws IOException {
        URL placesUrl = constructPlacesAPICall(location);
        InputStream placesInputStream = performPlacesAPICall(placesUrl);
        JsonParser parser = new JsonParser();
        Reader placesReader = new InputStreamReader(placesInputStream);
        JsonElement placesRootElement = parser.parse(placesReader);
        JsonObject placesRootObject = placesRootElement.getAsJsonObject();
        JsonArray placesResults = placesRootObject.get("results").getAsJsonArray();
        return addPlacesToList(placesResults);
    }

    private URL constructPlacesAPICall(String location) throws IOException{
        GeocodeParser locationParser = new GeocodeParser();
        String coordinates = locationParser.parse(location);
        String googlePlacesSearch = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + coordinates + "&radius=500&type=restaurant&key=AIzaSyAlmnrNiNrVybKz8JbYjOzuxZrGVRI9-Gg";
        return new URL(googlePlacesSearch);
    }

    private InputStream performPlacesAPICall(URL placesUrl) throws IOException{
        URLConnection placesConnection = placesUrl.openConnection();
        return placesConnection.getInputStream();
    }

    private List<Place> addPlacesToList(JsonArray placesResults){
        List<Place> placesList = new LinkedList<Place>();
        for (JsonElement place : placesResults) {
            Place googlePlace = new Place.Builder()
                    .setName(place.getAsJsonObject().has("name") ? place.getAsJsonObject().get("name").getAsString() : "N/A")
                    .setRating(place.getAsJsonObject().has("rating") ? place.getAsJsonObject().get("rating").getAsString() : "N/A")
                    .setAddress(place.getAsJsonObject().has("vicinity") ? place.getAsJsonObject().get("vicinity").getAsString() : "N/A")
                    .build();
            placesList.add(googlePlace);
        }
        return placesList;
    }
}

