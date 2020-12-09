package cleanup.cleanapp.cleancommunity;
/*
Team Cleanup
Curtis Ching                  n01274536
Kevin Daniel Delgado Toledo   n01323567
Manpreet Parmar               n01302460
*/

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity
{

    //Displays screen for a second, then jump to next activity
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup);
        final Handler h = new Handler();
        final Intent intent = new Intent(this, Startup.class);
        h.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                startActivity(intent);
                finish();
            }

        }, 1000);
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

}



