package cleanup.cleanapp.cleancommunity;
/*
Team Cleanup
Curtis Ching                  n01274536
Kevin Daniel Delgado Toledo   n01323567
Manpreet Parmar               n01302460
*/

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    String nameTxt, emailTxt, passwordTxt, conPasswordTxt;
    EditText editName, editEmail, editPassword, editConPass;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
    }

    //onClick of the SingUp button, stores the data offered and checks for password match
    public void addData(View view) {

        auth = FirebaseAuth.getInstance();

        editName = findViewById(R.id.editPersonName);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        editConPass = findViewById(R.id.editConPassword);

        passwordTxt = editPassword.getText().toString().trim();
        conPasswordTxt = editConPass.getText().toString().trim();

        if (passwordTxt.equals(conPasswordTxt)) {
            registerUserdata();
        } else {
            Toast.makeText(SignUp.this, getString(R.string.password_no_match), Toast.LENGTH_LONG).show();
        }

    }

    //If password matched, stores user data into firebase authentication system
    private void registerUserdata() {
        nameTxt = editName.getText().toString().trim();
        emailTxt = editEmail.getText().toString().trim();
        passwordTxt = editPassword.getText().toString().trim();
        conPasswordTxt = editConPass.getText().toString().trim();

        if (nameTxt.isEmpty()) {
            editName.setError(getString(R.string.input_name));
            editName.requestFocus();
            return;
        }
        if (emailTxt.isEmpty()) {
            editEmail.setError(getString(R.string.input_email));
            editEmail.requestFocus();
            return;
        }
        if (passwordTxt.isEmpty()) {
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

        //If all the logic is correct, sign in the user to firebase authentication
        auth.createUserWithEmailAndPassword(emailTxt, passwordTxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(nameTxt).build();

                    user.updateProfile(profileUpdates);
                    Toast.makeText(SignUp.this, getString(R.string.register_good), Toast.LENGTH_LONG).show();
                    final Intent intent = new Intent(SignUp.this, GetStarted.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(SignUp.this, getString(R.string.fail_register), Toast.LENGTH_LONG).show();
                }
            }
        });
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

    public static boolean EmailValidator(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "(?=.*[0-9])" +             //must include a digit
                    "(?=.*[a-zA-Z])" +  //must include characters
                    "(?=\\S+$)" +       //no whitespace include
                    ".{6,}"             //at least 6 characterss
    );

    public static boolean PasswordValidator(String password) {
        return password != null && PASSWORD_PATTERN.matcher(password).matches();
    }

    protected void onStart()
    {
        super.onStart();

    }

    @Override
    protected void onStop()
    {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}


