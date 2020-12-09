package cleanup.cleanapp.cleancommunity;
/*
Team Cleanup
Curtis Ching                  n01274536
Kevin Daniel Delgado Toledo   n01323567
Manpreet Parmar               n01302460
*/

import com.google.android.gms.maps.model.Circle;

//Object to hold location data for the database
public class LocationData {

    String areaNickname, contributor;
    float longitude, latitude;
    int radius, rating;
    Circle circle;
    String uid;

    public LocationData(){

    }

    public LocationData(String uid)
    {
        this.uid = uid;

    }

    public void setAreaNickname(String areaNickname) {
        this.areaNickname = areaNickname;
    }

    public void setContributor(String contributor) {
        this.contributor = contributor;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @SuppressWarnings("unused")
    public String getAreaNickname() {
        return areaNickname;
    }
    @SuppressWarnings("unused")
    public String getContributor() {
        return contributor;
    }
    @SuppressWarnings("unused")
    public Float getLongitude() {
        return longitude;
    }
    @SuppressWarnings("unused")
    public Float getLatitude() {
        return latitude;
    }
    @SuppressWarnings("unused")
    public int getRadius() {
        return radius;
    }
    @SuppressWarnings("unused")
    public int getRating() {
        return rating;
    }

    public void addcircle(Circle circle)
    {
        this.circle= circle;
    }



}
