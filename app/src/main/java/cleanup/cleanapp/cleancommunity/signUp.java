package cleanup.cleanapp.cleancommunity;

import android.os.Bundle;
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

        EditText text = findViewById(R.id.editPersonName);
        String name = text.getText().toString();
        text = findViewById(R.id.editEmail);
        String email = text.getText().toString().replaceAll("\\s+", "");
        text = findViewById(R.id.editPhone);
        String phone = text.getText().toString().replaceAll("\\s+", "");
        text = findViewById(R.id.editPassword);
        String pass = text.getText().toString().replaceAll("\\s+", "");
        text = findViewById(R.id.editPassword);
        String conPass = text.getText().toString().replaceAll("\\s+", "");

        storeinfo.adddata(name,email,phone,pass,conPass);
    }

}


