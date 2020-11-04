package cleanup.cleanapp.cleancommunity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class login  extends AppCompatActivity
{
    String urlAddress = "http://apollo.humber.ca/~n01323567/login.php";
    String emailTxt;
    String passwordTxt;
    EditText editEmail;
    EditText editPassword;

    public login() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) // tells user the activy is created
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }
    public void templogin(View view)
    {
        editEmail = findViewById(R.id.editTusername);
        editPassword = findViewById(R.id.editPasswordLog);

        emailTxt = editEmail.getText().toString().trim();
        passwordTxt = editPassword.getText().toString().trim();

       ReceiveData(emailTxt, passwordTxt);


    }

   private void  ReceiveData(final String email,final String password)
   {
       @SuppressLint("StaticFieldLeak")
       class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
           @Override
           protected String doInBackground(String... params)
           {
               List<NameValuePair> nameValuePairs = new ArrayList<>();
               nameValuePairs.add(new BasicNameValuePair("email", emailTxt));
               nameValuePairs.add(new BasicNameValuePair("password", passwordTxt));

               InputStream is;
               String result = null;
               try {
                   HttpClient httpClient = new DefaultHttpClient();
                   HttpPost httpPost = new HttpPost(urlAddress);
                   httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                   HttpResponse httpResponse = httpClient.execute(httpPost);
                   HttpEntity httpEntity = httpResponse.getEntity();

                   is = httpEntity.getContent();
                   BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8), 8);
                   String line;
                   while ((line = reader.readLine()) != null) {
                       Log.d("log_tag", line);
                       if (line.trim().equalsIgnoreCase("success")) {
                           final Intent intent = new Intent(login.this, gpsMainAct.class);
                           startActivity(intent);
                           finish();
                       }
                   }
                   result = "";
               } catch (IOException e) {
                   Log.e("log_tag", "Error in http connection", e);
               }
               return result;
           }
       }
       new SendPostReqAsyncTask().execute(email, password);
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
