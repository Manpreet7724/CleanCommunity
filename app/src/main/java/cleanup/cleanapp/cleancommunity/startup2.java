package cleanup.cleanapp.cleancommunity;

import android.content.Intent;
import android.view.View;
import android.widget.*;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class startup2 extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) // tells user the activy is created
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup2);
    }
    public void login(View view )
    {
        final Intent intent = new Intent(this, login.class);
        startActivity(intent);

    }
    public void signUp(View view )
    {
        final Intent intent = new Intent(this, signUp.class);
        startActivity(intent);
    }
}
