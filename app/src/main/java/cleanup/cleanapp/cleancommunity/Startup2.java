/*
Team Cleanup
Curtis Ching                  n01274536
Kevin Daniel Delgado Toledo   n01323567
Manpreet Parmar               n01302460
*/

package cleanup.cleanapp.cleancommunity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

public class Startup2 extends AppCompatActivity
{
    private static final int RC_SIGN_IN = 100;
    int PERMISSION_ID = 44;
    FirebaseAuth auth;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) // tells user the activy is created
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
    }

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
            catch (ApiException e)
            {
                Toast.makeText(Startup2.this, "Google Sign in failed", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken)
    {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
        {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(Startup2.this, "Google auth good", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(),GetStarted.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(Startup2.this, "Google auth good", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    void requestPermissions()
    {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_ID);
    }


}

