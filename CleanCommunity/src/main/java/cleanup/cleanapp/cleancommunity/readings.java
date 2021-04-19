package cleanup.cleanapp.cleancommunity;
/*
Team Cleanup
Curtis Ching                  n01274536
Kevin Daniel Delgado Toledo   n01323567
Manpreet Parmar               n01302460
*/

import com.google.android.gms.maps.model.Circle;

//Object to hold location data for the database
public class readings {

    float CO2,Humidity, temp ,tVOC;
    
    float longitude, latitude;
    String uid;
    public readings(){

    }

    public readings(String uid)
    {
        this.uid = uid;

    }

    public void setHumidity(float Humidity) {
        this.Humidity = Humidity;
    }

    public void setCo2(float CO2) {
        this.CO2 = CO2;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setTemp(float temperature) {
        this.temp = temperature;
    }

    public void setRating(float tVOC) {
        this.tVOC = tVOC;
    }

    @SuppressWarnings("unused")
    public float getHumidity() {
        return Humidity;
    }
    @SuppressWarnings("unused")
    public float getco2() {
        return CO2;
    }
    @SuppressWarnings("unused")
    public float getLongitude() {
        return longitude;
    }
    @SuppressWarnings("unused")
    public float getLatitude() {
        return latitude;
    }
    @SuppressWarnings("unused")
    public double getTemp() {
        return temp;
    }
    @SuppressWarnings("unused")
    public double gettVoc() {
        return tVOC;
    }




}
