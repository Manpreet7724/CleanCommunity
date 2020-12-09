package cleanup.cleanapp.cleancommunity;
/*
Team Cleanup
Curtis Ching                  n01274536
Kevin Daniel Delgado Toledo   n01323567
Manpreet Parmar               n01302460
*/

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import cleanup.cleanapp.cleancommunity.GpsMainAct;
import cleanup.cleanapp.cleancommunity.R;

public class GetStarted extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) //
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getstarted);

    }
    @Override
    protected void onStop()
    {
        super.onStop();
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    public void act_GPS(View view)
    {
        final Intent intent = new Intent(this, GpsMainAct.class);
        startActivity(intent);
        finish();
    }
}
