package cleanup.cleanapp.cleancommunity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class  getstarted extends AppCompatActivity
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
}
