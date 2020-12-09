/*
Team Cleanup
Curtis Ching                  n01274536
Kevin Daniel Delgado Toledo   n01323567
Manpreet Parmar               n01302460
*/

package cleanup.cleanapp.cleancommunity;

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
            Toast.makeText(SignUp.this, "Passwords do not Match", Toast.LENGTH_LONG).show();
        }

    }

    private void registerUserdata() {
        nameTxt = editName.getText().toString().trim();
        emailTxt = editEmail.getText().toString().trim();
        passwordTxt = editPassword.getText().toString().trim();
        conPasswordTxt = editConPass.getText().toString().trim();

        if (nameTxt.isEmpty()) {
            editName.setError("Please input your Name");
            editName.requestFocus();
            return;
        }
        if (emailTxt.isEmpty()) {
            editEmail.setError("Please input your Email");
            editEmail.requestFocus();
            return;
        }
        if (passwordTxt.isEmpty()) {
            editPassword.setError("Please input a Password");
            editPassword.requestFocus();
            return;
        }
        if (passwordTxt.length() < 6) {
            editPassword.setError("Password should be longer than 6 characters");
            editPassword.requestFocus();
            return;
        }

        auth.createUserWithEmailAndPassword(emailTxt, passwordTxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(nameTxt).build();

                    user.updateProfile(profileUpdates);
                    Toast.makeText(SignUp.this, "Sign Up Successful", Toast.LENGTH_LONG).show();
                    final Intent intent = new Intent(SignUp.this, GetStarted.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(SignUp.this, "Failed to register", Toast.LENGTH_LONG).show();
                }
            }
        });
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


