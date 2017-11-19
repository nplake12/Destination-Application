package edu.bsu.cs222;

public class Place {

    public static final class Builder {
        private String name;
        private String rating;
        private String address;
        private String distance;
        private String longitude;
        private String latitude;

        public Builder setName(String name){
            this.name = name;
            return this;
        }

        public Builder setRating(String rating){
            this.rating = rating;
            return this;
        }

        public Builder setAddress(String address){
            this.address = address;
            return this;
        }

        public Builder setDistance(String distance){
            this.distance = distance;
            return this;
        }

        public Builder setLongitude(String longitude){
            this.longitude = longitude;
            return this;
        }

        public Builder setLatitude(String latitude){
            this.latitude = latitude;
            return this;
        }

        public Place build(){
            return new Place(this);
        }
    }

    final private String name;
    final private String rating;
    final private String address;
    final private String distance;
    final private String longitude;
    final private String latitude;

    public Place(Builder builder){
        this.name = builder.name;
        this.rating = builder.rating;
        this.address = builder.address;
        this.distance = builder.distance;
        this.longitude = builder.longitude;
        this.latitude = builder.latitude;
    }

    public String getName(){
        return this.name;
    }

    public String getRating(){
        return this.rating;
    }

    public String getAddress(){ return this.address; }

    public String getDistance() { return this.distance; }

    public String getLongitude() { return this.longitude; }

    public String getLatitude() { return this.latitude; }
}
