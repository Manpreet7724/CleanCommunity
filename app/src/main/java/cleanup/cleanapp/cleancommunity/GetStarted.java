package cleanup.cleanapp.cleancommunity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class GetStarted<view> extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) // tells user the activy is created
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getstarted);
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


    public void act_GPS(View view)
    {
        final Intent intent = new Intent(this, GpsMainAct.class);
        startActivity(intent);
    }
}
