package cleanup.cleanapp.cleancommunity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class signUp extends AppCompatActivity
{


    @Override
    protected void onCreate(Bundle savedInstanceState) // tells user the activy is created
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
    }

    public void adddata(View view)
    {
        int x;
        EditText text = findViewById(R.id.editPersonName);
        String name = text.getText().toString();
        text = findViewById(R.id.editEmail);
        String email = text.getText().toString().replaceAll("\\s+", "");
        text = findViewById(R.id.editPhone);
        String phone = text.getText().toString().replaceAll("\\s+", "");
        text = findViewById(R.id.editPassword);
        String pass = text.getText().toString().replaceAll("\\s+", "");
        text = findViewById(R.id.editConPassword);
        String conPass = text.getText().toString().replaceAll("\\s+", "");

        x = storeinfo.adddata(name,email,phone,pass,conPass);
        if(x == 1)
        {
            final Intent intent = new Intent(this, getstarted.class);
            startActivity(intent);
        }


    }

    protected void onStart() // tells user the activy is started
    {
        super.onStart();

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

