package edu.bsu.cs222;

import java.io.IOException;

public class PlacesURL {
    public static final class Builder {
        private String coordinates;
        private String radius;
        private String placesURLCall;

        public Builder setCoordinates(String location) throws IOException{
            GeocodeParser locationParser = new GeocodeParser();
            this.coordinates = locationParser.parse(location);
            return this;
        }

        public Builder setRadius(String radius){
            this.radius = radius;
            return this;
        }

        public Builder setPlacesURLCall(){
            this.placesURLCall = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + coordinates +
                                 "&radius=" + radius + "&type=restaurant&key=AIzaSyAlmnrNiNrVybKz8JbYjOzuxZrGVRI9-Gg";
            return this;
        }

        public PlacesURL build(){
            return new PlacesURL(this);
        }
    }

    private String placesURLCall;

    public PlacesURL(Builder builder){
        this.placesURLCall = builder.placesURLCall;
    }

    public String getPlacesURLCall() { return this.placesURLCall; }


}
