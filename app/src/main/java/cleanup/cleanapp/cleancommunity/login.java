package cleanup.cleanapp.cleancommunity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class login  extends AppCompatActivity
{

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
        storeinfo.templogin(email,pass);
    }


}
