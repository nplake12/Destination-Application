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

public class GeocodeParser{

    public String parse(String location) throws IOException{
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
        return locationCoordinates.get("lat") + "," + locationCoordinates.get("lng");
    }
}
