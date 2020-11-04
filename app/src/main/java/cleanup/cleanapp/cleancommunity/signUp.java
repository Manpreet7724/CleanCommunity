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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class signUp extends AppCompatActivity
{
    String urlAddress = "http://apollo.humber.ca/~n01323567/signup.php";
    String nameTxt,emailTxt,phoneTxt,passwordTxt,conPasswordTxt;
    EditText editName, editEmail, editPhone, editPassword, editConPass;

    public signUp() {
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
        editName = findViewById(R.id.editPersonName);
        editEmail = findViewById(R.id.editEmail);
        editPhone = findViewById(R.id.editPhone);
        editPassword = findViewById(R.id.editPassword);
        editConPass = findViewById(R.id.editConPassword);

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
        @SuppressLint("StaticFieldLeak")
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                List<NameValuePair> nameValuePairs = new ArrayList<>();
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


