package edu.bsu.cs222;

import java.io.IOException;

public class PlacesURL {
    public static final class Builder {
        private String coordinates;
        private String radius;
        private String placesURLCall;
        private String placeType;

        public Builder setCoordinates(String location) throws IOException{
            GeocodeParser locationParser = new GeocodeParser();
            this.coordinates = locationParser.parse(location);
            return this;
        }

        public Builder setRadius(String radius){
            this.radius = radius;
            return this;
        }

        public Builder setPlaceType(String placeType){
            TypeMap typeMap = new TypeMap();
            this.placeType = typeMap.getTypeParameter(placeType);
            return this;
        }

        public Builder setPlacesURLCall(){
            this.placesURLCall = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + coordinates +
                                 "&radius=" + radius + "&type=" + placeType +"&key=AIzaSyAlmnrNiNrVybKz8JbYjOzuxZrGVRI9-Gg";
            return this;
        }

        public PlacesURL build(){
            return new PlacesURL(this);
        }
    }

    private String placesURLCall;
    private String coordinates;

    public PlacesURL(Builder builder){
        this.placesURLCall = builder.placesURLCall;
        this.coordinates = builder.coordinates;
    }

    public String getPlacesURLCall() { return this.placesURLCall; }

    public String getCoordinates() { return this.coordinates; }
}
