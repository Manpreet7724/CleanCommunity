package cleanup.cleanapp.cleancommunity;

import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class Startup2 extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) // tells user the activy is created
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup2);
    }
    public void login(View view )
    {
        final Intent intent = new Intent(this, Login.class);
        startActivity(intent);

    }
    public void signUp(View view )
    {
        final Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
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
