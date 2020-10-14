package cleanup.cleanapp.cleancommunity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class login  extends AppCompatActivity
{
    private TextView wongtext;
    @Override
    protected void onCreate(Bundle savedInstanceState) // tells user the activy is created
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }
    public void templogin(View view)
    {
        EditText text = findViewById(R.id.editPasswordLog);
        String pass = text.getText().toString().replaceAll("\\s+", "");
        text = findViewById(R.id.editTusername);
        String email = text.getText().toString().replaceAll("\\s+", "");
        int val =storeinfo.templogin(email,pass);
        if (val!=1)
        {
            wongtext = (TextView)findViewById(R.id.wongText);
            wongtext.setVisibility(View.VISIBLE);
        }
        else
        {
            final Intent intent = new Intent(this, startup2.class);
            startActivity(intent);
        }

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
