public class Place {

    public static final class Builder {
        private String name;
        private String rating;
        private String address;

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

        public Place build(){
            return new Place(this);
        }
    }

    private String name;
    private String rating;
    private String address;

    public Place(Builder builder){
        this.name = builder.name;
        this.rating = builder.rating;
        this.address = builder.address;
    }

    public String getName(){
        return this.name;
    }

    public String getRating(){
        return this.rating;
    }

    public String getAddress(){
        return this.address;
    }
}
