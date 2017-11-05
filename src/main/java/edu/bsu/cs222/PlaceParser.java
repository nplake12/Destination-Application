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
    private String placesAPICallURL;
    private PlacesURL url;

    public List<Place> parse() throws IOException {
        URL placesUrl = new URL(placesAPICallURL);
        InputStream placesInputStream = performPlacesAPICall(placesUrl);
        JsonParser parser = new JsonParser();
        Reader placesReader = new InputStreamReader(placesInputStream);
        JsonElement placesRootElement = parser.parse(placesReader);
        JsonObject placesRootObject = placesRootElement.getAsJsonObject();
        JsonArray placesResults = placesRootObject.get("results").getAsJsonArray();
        return addPlacesToList(placesResults);
    }

    public void constructURL(LinkedList<String> userInput) throws IOException{
        url = new PlacesURL.Builder()
                .setCoordinates(userInput.get(0))
                .setRadius(userInput.get(1))
                .setPlacesURLCall()
                .build();
        placesAPICallURL = url.getPlacesURLCall();
    }

    private InputStream performPlacesAPICall(URL placesUrl) throws IOException{
        URLConnection placesConnection = placesUrl.openConnection();
        return placesConnection.getInputStream();
    }

    private List<Place> addPlacesToList(JsonArray placesResults) throws IOException{
        List<Place> placesList = new LinkedList<Place>();
        for (JsonElement place : placesResults) {
            JsonObject destinationCoordinates = place.getAsJsonObject().get("geometry").getAsJsonObject().get("location").getAsJsonObject();
            Place googlePlace = new Place.Builder()
                    .setName(place.getAsJsonObject().has("name") ? place.getAsJsonObject().get("name").getAsString() : "N/A")
                    .setRating(place.getAsJsonObject().has("rating") ? place.getAsJsonObject().get("rating").getAsString() : "N/A")
                    .setAddress(place.getAsJsonObject().has("vicinity") ? place.getAsJsonObject().get("vicinity").getAsString() : "N/A")
                    .setDistance(getDistance(destinationCoordinates.get("lat").getAsString() + "," + destinationCoordinates.get("lng").getAsString()))
                    .build();
            placesList.add(googlePlace);
        }
        return placesList;
    }

    private String getDistance(String destinationCoordinates) throws IOException{
        DistanceParser distanceParser = new DistanceParser.Builder()
                .setOriginCoordinates(url.getCoordinates())
                .setDestinationCoordinates(destinationCoordinates)
                .build();
        return distanceParser.parseDistance();
    }
}