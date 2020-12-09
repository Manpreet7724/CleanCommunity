package cleanup.cleanapp.cleancommunity;
/*
Team Cleanup
Curtis Ching                  n01274536
Kevin Daniel Delgado Toledo   n01323567
Manpreet Parmar               n01302460
*/

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
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

import java.util.regex.Pattern;

public class Login extends AppCompatActivity
{
    String emailTxt;
    String passwordTxt;
    EditText editEmail;
    EditText editPassword;
    CheckBox stayLogin;
    public String PREFS_EMAIL= "prefsEmail";
    public String PREFS_PASSWORD="prefsPassword";
    public static final String PREFS_NAME = "MyPrefsFile";
    private static final String IS_CHECKED = "ischecked";

    //Needed empty constructor
    public Login() {
    }

    //Creates and display login screen for the user
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        editEmail = findViewById(R.id.editTusername);
        editPassword = findViewById(R.id.editPasswordLog);

        stayLogin = findViewById(R.id.checkBox);

        SharedPreferences pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        String emailTxt = pref.getString(PREFS_EMAIL, "");
        String passwordTxt = pref.getString(PREFS_PASSWORD, "");
        String LoginString = pref.getString(IS_CHECKED, "");

        if(LoginString.equals("true"))
        {
            stayLogin.setChecked(true);
        }

        editEmail.setText(emailTxt);
        editPassword.setText(passwordTxt);

    }

    //onClick of Login button, store the information written by the user
    public void loginUser(View view)
    {
        emailTxt = editEmail.getText().toString().trim();
        passwordTxt = editPassword.getText().toString().trim();

        if(emailTxt.isEmpty()){
            editEmail.setError(getString(R.string.input_email));
            editEmail.requestFocus();
            return;
        }
        if(passwordTxt.isEmpty()){
            editPassword.setError(getString(R.string.input_password));
            editPassword.requestFocus();
            return;
        }

        if(!PasswordValidator(passwordTxt)){
            editPassword.setError(getString(R.string.not_valid_pass));
            editPassword.requestFocus();
            return;
        }

        if(!EmailValidator(emailTxt)){
            editEmail.setError(getString(R.string.not_valid_email));
            editEmail.requestFocus();
        }


        FirebaseAuth auth = FirebaseAuth.getInstance();
        if(stayLogin.isChecked())
        {
            checkPreferences();
        }
        else
        {
            rcheckPreferences();
        }

        //Confirms the data written with the data inside firebase auth system
        auth.signInWithEmailAndPassword(emailTxt, passwordTxt).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
            public void onComplete(@NonNull Task<AuthResult> task){
                if(task.isSuccessful()){
                    Toast.makeText(Login.this, getString(R.string.login_good), Toast.LENGTH_LONG).show();
                    final Intent intent = new Intent(Login.this, GetStarted.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(Login.this, getString(R.string.login_fail), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    //SharedPreferences to remain logged in
    private void checkPreferences()
    {
         getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                .edit()
                .putString(PREFS_EMAIL, emailTxt)
                .putString(PREFS_PASSWORD, passwordTxt)
                .putString(IS_CHECKED, "true")
                .apply();
    }
    private void rcheckPreferences()
    {
        getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                .edit()
                .putString(PREFS_EMAIL, "")
                .putString(PREFS_PASSWORD, "")
                .putString(IS_CHECKED, "false")
                .apply();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    public void resetPass(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Forgot Password");

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.forgotpass,null);
        final EditText input = viewInflated.findViewById(R.id.forgotemail);
        builder.setView(viewInflated);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                FirebaseAuth auth = FirebaseAuth.getInstance();
                final String emailAddress =  input.getText().toString();

                auth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful())
                                {
                                    Toast.makeText(Login.this, getString(R.string.we_sent_email_to) + emailAddress, Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    Toast.makeText(Login.this, getString(R.string.incorrect_email) + emailAddress, Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public static final Pattern EMAIL_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%\\-]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    public static boolean EmailValidator(String email)
    {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "(?=.*[0-9])" +             //must include a digit
                    "(?=.*[a-zA-Z])" +  //must include characters
                    "(?=\\S+$)" +       //no whitespace include
                    ".{6,}"             //at least 6 characterss
    );

    public static boolean PasswordValidator(String password)
    {
        return password != null && PASSWORD_PATTERN.matcher(password).matches();
    }




}
