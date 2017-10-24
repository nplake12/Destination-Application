package edu.bsu.cs222;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class GeocodeParser{

    public String parse(String location) throws IOException{
        URL geocodingUrl = constructGeocodingAPIUrlCall(location);
        InputStream geocodingInputStream = performGeocodingAPICall(geocodingUrl);
        JsonParser parser = new JsonParser();
        Reader reader = new InputStreamReader(geocodingInputStream);
        JsonElement rootElement = parser.parse(reader);
        JsonObject rootObject = rootElement.getAsJsonObject();
        JsonArray locationResults = rootObject.get("results").getAsJsonArray();
        return parseLocationCoordinates(locationResults);
    }

    private URL constructGeocodingAPIUrlCall(String location) throws MalformedURLException{
        String geocodingSearch = "https://maps.googleapis.com/maps/api/geocode/json?address=" + location + "&key=AIzaSyDZ4V52UN8Bpw5Lwtwbk4AfBp45djbmP-c";
        return new URL(geocodingSearch);
    }

    private InputStream performGeocodingAPICall(URL geocodingUrl) throws IOException{
        URLConnection connection = geocodingUrl.openConnection();
        return connection.getInputStream();
    }

    private String parseLocationCoordinates(JsonArray locationResults){
        JsonObject geometry = locationResults
                .get(0)
                .getAsJsonObject()
                .get("geometry")
                .getAsJsonObject();
        JsonObject locationCoordinates = geometry.get("location").getAsJsonObject();
        return locationCoordinates.get("lat") + "," + locationCoordinates.get("lng");
    }
}
