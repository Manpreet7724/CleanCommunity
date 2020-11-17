package cleanup.cleanapp.cleancommunity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class gpsMainAct extends AppCompatActivity
{
    EditText areaNickname, radius, longitude, latitude, rating, contributor;

    @Override
    protected void onCreate(Bundle savedInstanceState) // tells user the activy is created
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_maingps);
    }
    @Override
    protected void onStop() // tells user the activy was stoped
    {
        super.onStop();
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    public void addNewLocation(View view) {
        final Intent intent = new Intent(this, swipeupActivity.class);
        startActivity(intent);
    }
}
