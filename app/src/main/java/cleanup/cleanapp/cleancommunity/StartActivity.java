package cleanup.cleanapp.cleancommunity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity
{

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



