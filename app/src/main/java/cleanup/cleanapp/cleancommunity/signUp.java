package cleanup.cleanapp.cleancommunity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import org.apache.http.*;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;


public class signUp extends AppCompatActivity
{
    String urlAddress = "http://apollo.humber.ca/~n01323567/signup.php";
    String nameTxt,emailTxt,phoneTxt,passwordTxt,conPasswordTxt;
    EditText editName, editEmail, editPhone, editPassword, editConPass;
    private static final String TAG = "Error found: ";
    URL url = new URL(urlAddress);

    public signUp() throws MalformedURLException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) // tells user the activy is created
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
    }

    @SuppressLint("LongLogTag")
    public void adddata(View view)
    {
        int x;
        editName = (EditText) findViewById(R.id.editPersonName);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editPhone = (EditText) findViewById(R.id.editPhone);
        editPassword = (EditText) findViewById(R.id.editPassword);
        editConPass = (EditText) findViewById(R.id.editConPassword);

        nameTxt = editName.getText().toString().trim();
        emailTxt = editEmail.getText().toString().trim();
        phoneTxt = editPhone.getText().toString().trim();
        passwordTxt = editPassword.getText().toString().trim();
        conPasswordTxt = editConPass.getText().toString().trim();

        if(passwordTxt.equals(conPasswordTxt)){
            InsertData(nameTxt, emailTxt, phoneTxt, passwordTxt);
            final Intent intent = new Intent(this, getstarted.class);
            startActivity(intent);
        }else{
            Toast.makeText(signUp.this, "Passwords do not match", Toast.LENGTH_LONG).show();
        }

    }

    public void InsertData(final String name, final String email, final String phone, final String password){
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("Name", nameTxt));
                nameValuePairs.add(new BasicNameValuePair("Email", emailTxt));
                nameValuePairs.add(new BasicNameValuePair("Phone", phoneTxt));
                nameValuePairs.add(new BasicNameValuePair("Password", passwordTxt));
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(urlAddress);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    HttpEntity httpEntity = httpResponse.getEntity();
                }  catch (IOException e) {
                    Log.e("log_tag", "Error in http connection", e);
                }

                return "Data Inserted Successfully";
            }
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                Toast.makeText(signUp.this, "Sign Up Successful", Toast.LENGTH_LONG).show();
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(name, email, phone, password);
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


