package cleanup.cleanapp.cleancommunity;

import com.google.android.gms.maps.model.Circle;

public class LocationData {

    String areaNickname, contributor;
    Long longitude, latitude;
    int radius, rating;
    Circle circle;

    public void setAreaNickname(String areaNickname) {
        this.areaNickname = areaNickname;
    }

    public void setContributor(String contributor) {
        this.contributor = contributor;
    }

    public void setLongitude(Long longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getAreaNickname() {
        return areaNickname;
    }

    public String getContributor() {
        return contributor;
    }

    public Long getLongitude() {
        return longitude;
    }

    public Long getLatitude() {
        return latitude;
    }

    public int getRadius() {
        return radius;
    }

    public int getRating() {
        return rating;
    }

    public void addcircle(Circle circle)
    {
        this.circle= circle;
    }

    public Circle getCircle()
    {
        return circle;
    }
    public void setCircle(Circle circle)
    {
        this.circle = circle;
    }

}
