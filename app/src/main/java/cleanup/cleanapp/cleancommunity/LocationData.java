package cleanup.cleanapp.cleancommunity;

public class LocationData {

    String areaNickname, email;
    long longitude, latitude, radius;
    int index;

    public LocationData(){

    }

    public LocationData(String areaNickname, String email, long longitude, long latitude, long radius, int index) {
        this.areaNickname = areaNickname;
        this.email = email;
        this.longitude = longitude;
        this.latitude = latitude;
        this.radius = radius;
        this.index = index;
    }

}
