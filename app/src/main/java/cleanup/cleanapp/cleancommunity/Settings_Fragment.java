package cleanup.cleanapp.cleancommunity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DecimalFormat;
import java.util.Objects;

import static android.content.ContentValues.TAG;
import android.location.Location;
import android.location.LocationManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class Settings_Fragment extends Fragment implements View.OnClickListener {

    Context context;
    Button signoutButton, passUpdate, notification;
    EditText editPassUpdate;
    TextView username, useremail;
    String newPassword;
    private static final int REQUEST_LOCATION = 1;
    int PERMISSION_ID = 44;
    String backgroundColor;
    FusedLocationProviderClient mFusedLocationClient;
    LocationManager locationManager;
    public double lat;
    public double longt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings_, container, false);

        signoutButton = rootView.findViewById(R.id.logout);
        signoutButton.setOnClickListener(this);
        passUpdate = rootView.findViewById(R.id.passUpdate);
        passUpdate.setOnClickListener(this);
        notification = rootView.findViewById(R.id.notification);
        notification.setOnClickListener(this);

        editPassUpdate = rootView.findViewById(R.id.editPassUpdate);
        username = rootView.findViewById(R.id.userNameSetting);
        useremail = rootView.findViewById(R.id.userEmailSetting);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        username.setText(user.getDisplayName());
        useremail.setText(user.getEmail());

        context = Objects.requireNonNull(getActivity()).getApplicationContext();

        return rootView;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        newPassword = editPassUpdate.getText().toString().trim();
        if (v.getId() == R.id.logout) {
            signout();
        }
        if (v.getId() == R.id.passUpdate) {
            if (newPassword.isEmpty()) {
                editPassUpdate.setError("Please input a new Password");
                editPassUpdate.requestFocus();
            } else {
                passUpdate();
            }
        }
        if (v.getId() == R.id.notification){
            coordinatesNotif();
        }
    }

    public void signout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure you want to log out?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth.getInstance().signOut();
                final Intent intent = new Intent(getActivity(), Startup2.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog a = builder.create();
        a.show();
    }

    public void passUpdate() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.gmailPassUpdate);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                newPassword = editPassUpdate.getText().toString().trim();

                user.updatePassword(newPassword)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getActivity(), "Password successfully updated", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog a = builder.create();
        a.show();
    }

    public void coordinatesNotif() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        else
        {
            locationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null)
            {
                lat = locationGPS.getLatitude();
                longt = locationGPS.getLongitude();
                DecimalFormat numberFormat = new DecimalFormat("#.000");
                int reqCode = 1;
                Intent intent = new Intent(getActivity(), GpsMainAct.class);
                showNotification(getActivity(), "Clean Community", "Latitude: " + numberFormat.format(lat) + " Longitude: " + numberFormat.format(longt), intent, reqCode);
            }
        }
    }

    public void showNotification(Context context, String title, String message, Intent intent, int reqCode) {

        PendingIntent pendingIntent = PendingIntent.getActivity(context, reqCode, intent, PendingIntent.FLAG_ONE_SHOT);
        String CHANNEL_ID = "channel_name";// The id of the channel.
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel Name";// The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationManager.createNotificationChannel(mChannel);
        }
        notificationManager.notify(reqCode, notificationBuilder.build()); // 0 is the request code, it should be unique id

        Log.d("showNotification", "showNotification: " + reqCode);
    }


    public void onDestroy() {

        super.onDestroy();

    }
}