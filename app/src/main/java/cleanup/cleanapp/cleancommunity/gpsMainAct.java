package cleanup.cleanapp.cleancommunity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class gpsMainAct extends AppCompatActivity
{
    TextView username, useremail;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    ImageButton openmenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) // tells user the activy is created
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_maingps);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = findViewById(R.id.drawer_layout);
        openmenu =findViewById(R.id.home);
        NavigationView navigationView = findViewById(R.id.nvView);
        View headerLayout = navigationView.inflateHeaderView(R.layout.drawer_menu_header);
        ImageView HeaderPhoto = headerLayout.findViewById(R.id.imageView);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("User");

        useremail = headerLayout.findViewById(R.id.menuUserEmail);
        username = headerLayout.findViewById(R.id.menuUserName);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            useremail.setText(email);
            String uid = user.getUid();
        }
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid= user.getUid();

                DatabaseReference currentID = FirebaseDatabase.getInstance().getReference("User")
                        .child(uid);
                String stringID = currentID.toString().substring(44);
                userdata data = dataSnapshot.child(stringID).getValue(userdata.class);

                if (stringID.equals(uid)) {
                    String name = data.name;
                    username.setText(name);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d("TAG", "Failed to read value.", error.toException());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
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

    public void addNewLocation(View view) {
        final Intent intent = new Intent(this, swipeupActivity.class);
        startActivity(intent);
    }


}
