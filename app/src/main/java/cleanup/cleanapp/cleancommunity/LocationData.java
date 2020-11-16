package cleanup.cleanapp.cleancommunity;

import com.google.android.gms.maps.model.Circle;

public class LocationData
{

    String areaNickname, email;
    long longitude, latitude, radius;
    int index,rating;
    Circle circle;

    public LocationData()
    {

    }

    public LocationData(String areaNickname, String email, long longitude, long latitude, long radius, int rating,int index)
    {
        this.areaNickname = areaNickname;
        this.email = email;
        this.longitude = longitude;
        this.latitude = latitude;
        this.radius = radius;
        this.index = index;
        this.rating=rating;
    }
    public void addcircle(Circle circle)
    {
         this.circle= circle;
    }

    public Circle getCircle()
    {
        return circle;
    }

    public String getEmail()
    {
        return email;
    }

    public String getAreaNickname()
    {
        return areaNickname;
    }

    public long getRadius()
    {
        return radius;
    }

    public long getLongitude()
    {
        return longitude;
    }

    public long getLatitude()
    {
        return latitude;
    }

    public int getIndex()
    {
        return index;
    }

    public int getRating()
    {
        return rating;
    }

    public void setAreaNickname(String areaNickname)
    {
        this.areaNickname = areaNickname;
    }

    public void setCircle(Circle circle)
    {
        this.circle = circle;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public void setLatitude(long latitude)
    {
        this.latitude = latitude;
    }

    public void setLongitude(long longitude)
    {
        this.longitude = longitude;
    }

    public void setRadius(long radius)
    {
        this.radius = radius;
    }

    public void setRating(int rating)
    {
        this.rating = rating;
    }
}
