package cleanup.cleanapp.cleancommunity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;


public class SignUp extends AppCompatActivity {
    private static final int RC_SIGN_IN = 100;
    String nameTxt, emailTxt, phoneTxt, passwordTxt, conPasswordTxt;
    EditText editName, editEmail, editPhone, editPassword, editConPass;
    FirebaseAuth auth;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) // tells user the activy is created
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        auth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    public void adddata(View view) {

        auth = FirebaseAuth.getInstance();

        editName = findViewById(R.id.editPersonName);
        editEmail = findViewById(R.id.editEmail);
        editPhone = findViewById(R.id.editPhone);
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
        phoneTxt = editPhone.getText().toString().trim();
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
        if (phoneTxt.isEmpty()) {
            editPhone.setError("Please input your Phone number");
            editPhone.requestFocus();
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
                    UserData userdata = new UserData(nameTxt, emailTxt, phoneTxt, passwordTxt);

                    FirebaseDatabase.getInstance().getReference("User")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(userdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SignUp.this, "Sign Up Successful", Toast.LENGTH_LONG).show();
                                final Intent intent = new Intent(SignUp.this, GetStarted.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(SignUp.this, "Failed to register", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(SignUp.this, "Failed to register", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    protected void onStart() // tells user the activy is started
    {
        super.onStart();

        FirebaseUser user = auth.getCurrentUser();
        if(user != null){
            Intent intent = new Intent(getApplicationContext(),GetStarted.class);
            startActivity(intent);
        }

    }

    @Override
    protected void onStop() // tells user the activy was stoped
    {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void googleSignup(View view) {
        Log.d("123123", "error 1");
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        Log.d("123123", "error 2");
        startActivityForResult(signInIntent, RC_SIGN_IN);
        Log.d("123123", "error 3");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("123123", "error 4");
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Log.d("123123", "error 5");
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            Log.d("123123", "error 6");
            try {
                Log.d("123123", "error 7");
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("123123", "error 8");
                firebaseAuthWithGoogle(account.getIdToken());
                Log.d("123123", "error 9");
            } catch (ApiException e) {
                Toast.makeText(this, "Google sign up failed", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(SignUp.this, "Authentication Successful", Toast.LENGTH_LONG).show();
                            FirebaseUser user = auth.getCurrentUser();
                            Intent intent = new Intent(getApplicationContext(),GetStarted.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(SignUp.this, "Authentication failed", Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }
}


