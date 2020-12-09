package cleanup.cleanapp.cleancommunity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

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
