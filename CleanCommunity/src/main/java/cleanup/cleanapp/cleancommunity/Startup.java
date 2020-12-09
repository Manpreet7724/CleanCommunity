package cleanup.cleanapp.cleancommunity;
/*
Team Cleanup
Curtis Ching                  n01274536
Kevin Daniel Delgado Toledo   n01323567
Manpreet Parmar               n01302460
*/

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class Startup extends AppCompatActivity
{
    private static final int RC_SIGN_IN = 100;
    int PERMISSION_ID = 44;
    FirebaseAuth auth;
    private GoogleSignInClient mGoogleSignInClient;

    //Displays screen to the user and builds GoogleSignInOptions for the user to user google auth system
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup2);

        auth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.api_web_client_ip))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        requestPermissions();

        SignInButton googleButton = findViewById(R.id.googleSignup);
        googleButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                googleSignup();
            }
        });
    }

    //On login or Signup button click, the user will be taken to the respective activity
    public void login(View view )
    {
        final Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
    public void signUp(View view )
    {
        final Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
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
        System.exit(0);
    }

    //Initializes google signup process
    public void googleSignup()
    {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try
            {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            }
            catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

    //On a successful google sign up process, advance to the next activity
    private void firebaseAuthWithGoogle(String idToken)
    {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
        {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(Startup.this, getString(R.string.googleauth_good), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(),GetStarted.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(Startup.this, getString(R.string.googleauth_error), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    //Request location permission from the user
    void requestPermissions()
    {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_ID);
    }

    //On back pressed, ask the user before terminating the app
    @Override
    public void onBackPressed()
    {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.exit_app))
                .setMessage(getString(R.string.confirm_exit))
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        dialog.dismiss();
                        finish();
                    }
                }).setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        }).show();

    }

}

