package cleanup.cleanapp.cleancommunity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class SwipeupActivity extends AppCompatActivity
{
    EditText areaNickname, radius, longitude, latitude, rating, contributor;

    private SlidingUpPanelLayout mLayout;


    String areaNicknameText, radiusText, longitudeText, latitudeText, ratingText, contributorText;
    Button LocationButton;
    SeekBar seekbar;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipeupmenu);

        areaNickname = findViewById(R.id.areaNicknameText);
        radius = findViewById(R.id.radiusText);
        longitude = findViewById(R.id.longitudeText);
        latitude = findViewById(R.id.latitudeText);
        rating = findViewById(R.id.ratingText);
        contributor = findViewById(R.id.contributorText);

        areaNickname.setFocusableInTouchMode(true);
        radius.setFocusable(true);
        radius.setFocusableInTouchMode(true);
        longitude.setFocusable(true);
        longitude.setFocusableInTouchMode(true);
        latitude.setFocusable(true);
        latitude.setFocusableInTouchMode(true);
        rating.setFocusable(true);
        rating.setFocusableInTouchMode(true);
        contributor.setFocusable(true);
        contributor.setFocusableInTouchMode(true);

        LocationButton = findViewById(R.id.addLocationButton);
        LocationButton.setVisibility(View.VISIBLE);
    }

    public void AddLocation(View view)
    {
        areaNicknameText = areaNickname.getText().toString().trim();
        radiusText = radius.getText().toString().trim();
        longitudeText = longitude.getText().toString().trim();
        latitudeText = latitude.getText().toString().trim();
        ratingText = rating.getText().toString().trim();
        contributorText = contributor.getText().toString().trim();

        LocationData locationData = new LocationData();

        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Location");
        int ratingInput = Integer.parseInt(ratingText);
        int radiusInput = Integer.parseInt(radiusText);
        float latitudeInput = Float.parseFloat(latitudeText);
        float longitudeInput = Float.parseFloat(longitudeText);

        locationData.setAreaNickname(areaNicknameText);
        locationData.setLatitude(latitudeInput);
        locationData.setLongitude(longitudeInput);
        locationData.setRadius(radiusInput);
        locationData.setRating(ratingInput);
        locationData.setContributor(contributorText);

        database.push().setValue(locationData);


    }

    public void onDestroy() {
        super.onDestroy();
    }

}
