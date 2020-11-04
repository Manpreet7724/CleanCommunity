package cleanup.cleanapp.cleancommunity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class login  extends AppCompatActivity
{
    String urlAddress = "http://apollo.humber.ca/~n01323567/login.php";
    String nameTxt,emailTxt,phoneTxt,passwordTxt,conPasswordTxt;
    EditText editName, editEmail, editPhone, editPassword, editConPass;
    private static final String TAG = "Error found: ";
    URL url = new URL(urlAddress);

    public login() throws MalformedURLException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) // tells user the activy is created
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }
    public void templogin(View view)
    {
        int x;
        editEmail = (EditText) findViewById(R.id.editTusername);
        editPassword = (EditText) findViewById(R.id.editPasswordLog);

        emailTxt = editEmail.getText().toString().trim();
        passwordTxt = editPassword.getText().toString().trim();

       ReceiveData(emailTxt, passwordTxt);


    }

   private void  ReceiveData(final String email,final String password) {
       class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
           @Override
           protected String doInBackground(String... params) {
               InputStream is = null;

               List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
               nameValuePairs.add(new BasicNameValuePair("Email", emailTxt));
               nameValuePairs.add(new BasicNameValuePair("Password", passwordTxt));
               String result = null;
               try {
                   HttpClient httpClient = new DefaultHttpClient();
                   HttpPost httpPost = new HttpPost(urlAddress);
                   httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
                   HttpResponse httpResponse = httpClient.execute(httpPost);
                   HttpEntity httpEntity = httpResponse.getEntity();

                   is = httpEntity.getContent();

                   BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                   StringBuilder sb = new StringBuilder();
                   String line = null;
                   while ((line = reader.readLine()) != null) {
                       sb.append(line + "\n");
                   }
                   result = sb.toString();
               } catch (IOException e) {
                   Log.e("log_tag", "Error in http connection", e);
               }
               return result;
           }

           @Override
           protected void onPostExecute(String result) {
               String s = result.trim();
               if (s.equalsIgnoreCase("success")) {
                   final Intent intent = new Intent(login.this, getstarted.class);
                   startActivity(intent);
                   finish();

               } else {
                   Toast.makeText(getApplicationContext(), "Invalid Email or Password", Toast.LENGTH_LONG).show();
               }
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
