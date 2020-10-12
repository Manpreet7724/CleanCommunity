package cleanup.cleanapp.cleancommunity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup);

        final Handler h = new Handler();

        h.postDelayed(new Runnable() {

            @Override

            public void run()
            {
                setContentView(R.layout.startup2);
            }

        }, 1000);

    }

    }
