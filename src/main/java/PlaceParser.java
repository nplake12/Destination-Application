import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jdk.nashorn.internal.parser.JSONParser;

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
        String geocodingSearch = "https://maps.googleapis.com/maps/api/geocode/json?address=" + location + "&key=AIzaSyDZ4V52UN8Bpw5Lwtwbk4AfBp45djbmP-c";
        URL url = new URL(geocodingSearch);
        URLConnection connection = url.openConnection();
        InputStream inputStream = connection.getInputStream();
        JsonParser parser = new JsonParser();
        Reader reader = new InputStreamReader(inputStream);
        JsonElement rootElement = parser.parse(reader);
        JsonObject rootObject = rootElement.getAsJsonObject();
        JsonArray results = rootObject.get("results").getAsJsonArray();
        JsonObject geometry = results
                .get(0)
                .getAsJsonObject()
                .get("geometry")
                .getAsJsonObject();
        JsonObject locationCoordinates = geometry.get("location").getAsJsonObject();
        String coordinates = locationCoordinates.get("lat") + "," + locationCoordinates.get("lng");
        String googlePlacesSearch = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + coordinates + "&radius=100&type=restaurant&key=AIzaSyAlmnrNiNrVybKz8JbYjOzuxZrGVRI9-Gg";
        URL placesUrl = new URL(googlePlacesSearch);
        URLConnection placesConnection = placesUrl.openConnection();
        InputStream placesInputStream = placesConnection.getInputStream();
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

