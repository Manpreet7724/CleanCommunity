package cleanup.cleanapp.cleancommunity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
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

    private FirebaseAuth auth;

    public Login() {
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

        LoginUser();

        //final Intent intent = new Intent(login.this, getstarted.class);
        //startActivity(intent);

    }

    private void LoginUser()
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
