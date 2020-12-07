package cleanup.cleanapp.cleancommunity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Login extends AppCompatActivity
{
    String emailTxt;
    String passwordTxt;
    EditText editEmail;
    EditText editPassword;
    CheckBox stayLogin;
    public String PREFS_USERNAME= "prefsUsername";
    public String PREFS_PASSWORD="prefsPassword";
    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String PREFS_USER = "prefsUsername";
    public static final String PREFS_PASS = "prefsPassword";


    private FirebaseAuth auth;

    public Login() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) // tells user the activy is created
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        editEmail = findViewById(R.id.editTusername);
        editPassword = findViewById(R.id.editPasswordLog);

        stayLogin = findViewById(R.id.checkBox);

        SharedPreferences pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        String emailTxt = pref.getString(PREFS_USERNAME, "");
        String passwordTxt = pref.getString(PREFS_PASSWORD, "");

        editEmail.setText(emailTxt);
        editPassword.setText(passwordTxt);

    }


    public void loginUser(View view)
    {
        emailTxt = editEmail.getText().toString().trim();
        passwordTxt = editPassword.getText().toString().trim();

        if(emailTxt.isEmpty()){
            editEmail.setError("Please input your Email");
            editEmail.requestFocus();
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

        auth = FirebaseAuth.getInstance();
        if(stayLogin.isChecked()) {
            checkPreferences();
        }


        auth.signInWithEmailAndPassword(emailTxt, passwordTxt).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
            public void onComplete(@NonNull Task<AuthResult> task){
                if(task.isSuccessful()){
                    Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_LONG).show();
                    final Intent intent = new Intent(Login.this, GetStarted.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(Login.this, "Failed to login", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private SharedPreferences checkPreferences() {
        SharedPreferences pref = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);

        getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                .edit()
                .putString(PREFS_USERNAME, emailTxt)
                .putString(PREFS_PASSWORD, passwordTxt)
                .apply();
        return pref;
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
