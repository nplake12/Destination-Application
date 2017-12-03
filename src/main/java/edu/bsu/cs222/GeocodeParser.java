package edu.bsu.cs222;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

public class GeocodeParser{

    public String parse(String location) throws IOException{
        APICaller geocodingAPICaller = new APICaller();
        Reader geocodingReader = geocodingAPICaller.makeAPICall(constructGeocodingAPIURL(location));
        JsonParser parser = new JsonParser();
        JsonElement rootElement = parser.parse(geocodingReader);
        JsonObject rootObject = rootElement.getAsJsonObject();
        JsonArray locationResults = rootObject.get("results").getAsJsonArray();
        if(locationResults.size() == 0){
            originCoordinatesAlert();
        }
        return parseLocationCoordinates(locationResults);
    }

    private URL constructGeocodingAPIURL(String location) throws MalformedURLException{
        String geocodingSearch = "https://maps.googleapis.com/maps/api/geocode/json?address=" +
                                 location + "&key=AIzaSyDZ4V52UN8Bpw5Lwtwbk4AfBp45djbmP-c";
        return new URL(geocodingSearch);
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

    private void originCoordinatesAlert(){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Error");
        errorAlert.setContentText("It seems that the place you have entered either does not exist or is misspelled!");
        errorAlert.showAndWait();
    }
}
