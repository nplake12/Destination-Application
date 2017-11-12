package edu.bsu.cs222;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

public class DistanceParser {
    public static final class Builder {
        private String originCoordinates;
        private String destinationCoordinates;

        public Builder setOriginCoordinates(String originCoordinates) throws IOException{
            this.originCoordinates = originCoordinates;
            return this;
        }

        public Builder setDestinationCoordinates(String destinationCoordinates){
            this.destinationCoordinates = destinationCoordinates;
            return this;
        }

        public DistanceParser build(){
            return new DistanceParser(this);
        }
    }

    private String originCoordinates;
    private String destinationCoordinates;

    public DistanceParser(Builder builder){
        this.originCoordinates = builder.originCoordinates;
        this.destinationCoordinates = builder.destinationCoordinates;
    }

    public String parseDistance() throws IOException{
        APICaller distanceMatrixAPICaller = new APICaller();
        Reader distanceMatrixReader = distanceMatrixAPICaller.makeAPICall(constructDistanceMatrixAPIURL());
        JsonParser parser = new JsonParser();
        JsonElement rootElement = parser.parse(distanceMatrixReader);
        JsonObject rootObject = rootElement.getAsJsonObject();
        JsonArray distanceArray = rootObject.get("rows").getAsJsonArray();
        return parseDistanceArray(distanceArray);
    }

    private URL constructDistanceMatrixAPIURL() throws MalformedURLException{
        return new URL("https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=" +
                originCoordinates + "&destinations=" + destinationCoordinates +
                "&key=AIzaSyCV5eOWU9AKoF3n76KCUKSMYrMGPl31fhE");
    }

    private String parseDistanceArray(JsonArray distanceArray){
        JsonObject element = distanceArray
                                .get(0)
                                .getAsJsonObject()
                                .get("elements")
                                .getAsJsonArray()
                                .get(0)
                                .getAsJsonObject();
        String distance = element
                            .get("distance")
                            .getAsJsonObject()
                            .get("text")
                            .getAsString();
        return distance.contains("ft") ? "0.1 mi" : distance;
    }
}
