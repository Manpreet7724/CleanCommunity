package cleanup.cleanapp.cleancommunity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class signUp extends AppCompatActivity
{
    String nameTxt,emailTxt,phoneTxt,passwordTxt,conPasswordTxt;
    EditText editName, editEmail, editPhone, editPassword, editConPass;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) // tells user the activy is created
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
    }

    @SuppressLint("LongLogTag")
    public void adddata(View view)
    {

        auth = FirebaseAuth.getInstance();

        editName = findViewById(R.id.editPersonName);
        editEmail = findViewById(R.id.editEmail);
        editPhone = findViewById(R.id.editPhone);
        editPassword = findViewById(R.id.editPassword);
        editConPass = findViewById(R.id.editConPassword);


        registerUserdata();

    }

    private void registerUserdata(){
        nameTxt = editName.getText().toString().trim();
        emailTxt = editEmail.getText().toString().trim();
        phoneTxt = editPhone.getText().toString().trim();
        passwordTxt = editPassword.getText().toString().trim();
        conPasswordTxt = editConPass.getText().toString().trim();

        if(nameTxt.isEmpty()){
            editName.setError("Please input your Name");
            editName.requestFocus();
            return;
        } if(emailTxt.isEmpty()){
            editEmail.setError("Please input your Email");
            editEmail.requestFocus();
            return;
        } if(phoneTxt.isEmpty()){
            editPhone.setError("Please input your Phone number");
            editPhone.requestFocus();
            return;
        }
        if(passwordTxt.isEmpty()){
            editPassword.setError("Please input a Password");
            editPassword.requestFocus();
            return;
        }
        if(passwordTxt.length() < 6){
            editPassword.setError("Password should be longer than 6 characters");
            editPassword.requestFocus();
            return;
        }

        auth.createUserWithEmailAndPassword(emailTxt, passwordTxt).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    userdata userdata = new userdata(nameTxt, emailTxt,phoneTxt , passwordTxt);

                    FirebaseDatabase.getInstance().getReference("User")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(userdata).addOnCompleteListener(new OnCompleteListener<Void>(){
                                public void onComplete(@NonNull Task<Void> task){
                                    if(task.isSuccessful()){
                                        Toast.makeText(signUp.this, "Sign Up Successful", Toast.LENGTH_LONG).show();
                                        final Intent intent = new Intent(signUp.this, getstarted.class);
                                        startActivity(intent);
                                    }else
                                        {
                                        Toast.makeText(signUp.this, "Failed to register", Toast.LENGTH_LONG).show();
                                        }
                                }
                            });
                }else
                    {
                    Toast.makeText(signUp.this, "Failed to register", Toast.LENGTH_LONG).show();
                    }
            }
        });
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


