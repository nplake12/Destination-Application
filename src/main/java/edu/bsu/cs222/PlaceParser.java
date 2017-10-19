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
        List<Place> places = new LinkedList<Place>();
        GeocodeParser locationParser = new GeocodeParser();
        String coordinates = locationParser.parse(location);
        String googlePlacesSearch = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + coordinates + "&radius=100&type=restaurant&key=AIzaSyAlmnrNiNrVybKz8JbYjOzuxZrGVRI9-Gg";
        URL placesUrl = new URL(googlePlacesSearch);
        URLConnection placesConnection = placesUrl.openConnection();
        InputStream placesInputStream = placesConnection.getInputStream();
        JsonParser parser = new JsonParser();
        Reader placesReader = new InputStreamReader(placesInputStream);
        JsonElement placesRootElement = parser.parse(placesReader);
        JsonObject placesRootObject = placesRootElement.getAsJsonObject();
        JsonArray placesResults = placesRootObject.get("results").getAsJsonArray();
        for (JsonElement place : placesResults) {
            Place googlePlace = new Place.Builder()
                    .setName(place.getAsJsonObject().get("name").getAsString())
                    .setRating(place.getAsJsonObject().get("rating").getAsString())
                    .setAddress(place.getAsJsonObject().get("vicinity").getAsString())
                    .build();
            places.add(googlePlace);
        }
        return places;
    }
}

