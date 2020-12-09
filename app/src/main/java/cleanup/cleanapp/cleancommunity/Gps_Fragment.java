package cleanup.cleanapp.cleancommunity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.location.LocationManager;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;


public class Gps_Fragment extends Fragment
{
    public static String holdArea;
    FirebaseUser user;
    FirebaseAuth auth;
    int x;
    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    FirebaseDatabase database;
    EditText areaNickname, areaRating;
    public double lat;
    public double longt;
    Button abutton,bbutton,cbutton,dbutton;
    public String holdAreaname;
    Marker centerMarker;
    Circle addcircle;
    Boolean addbutton =false,nextbtn=false;
    SeekBar radseekbar;

    public static Gps_Fragment getInstance() {
        Gps_Fragment chatFragment = new Gps_Fragment();
        return chatFragment;
    }

    private final OnMapReadyCallback callback = new OnMapReadyCallback()
    {
        @Override
        public void onMapReady(final GoogleMap googleMap)
        {
            Settings_Fragment settting = new Settings_Fragment();
            abutton= getActivity().findViewById(R.id.getStarbutton);
            bbutton= getActivity().findViewById(R.id.btn_cancel);
            cbutton= getActivity().findViewById(R.id.btn_next);
            dbutton=getActivity().findViewById(R.id.btn_done);
            if(Settings_Fragment.nightmodecheck)
            {
                try
                {
                    boolean success = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getActivity(), R.raw.nightmode_map));
                    abutton.setBackgroundTintList(null);
                    abutton.setBackgroundColor(getResources().getColor(R.color.white));
                    bbutton.setBackgroundTintList(null);
                    bbutton.setBackgroundColor(getResources().getColor(R.color.white));
                    cbutton.setBackgroundTintList(null);
                    cbutton.setBackgroundColor(getResources().getColor(R.color.white));
                    dbutton.setBackgroundTintList(null);
                    dbutton.setBackgroundColor(getResources().getColor(R.color.white));

                } catch (Resources.NotFoundException e) {
                    Log.e("Error", "Can't find style. Error: ", e);
                }
            }
            else
            {
                try
                {
                    boolean success = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getActivity(), GoogleMap.MAP_TYPE_NORMAL));
                    abutton.setBackgroundResource(R.color.transparent);
                    abutton.setBackgroundColor(getResources().getColor(R.color.black));
                    bbutton.setBackgroundResource(R.color.transparent);
                    bbutton.setBackgroundColor(getResources().getColor(R.color.black));
                    cbutton.setBackgroundResource(R.color.transparent);
                    cbutton.setBackgroundColor(getResources().getColor(R.color.black));
                    dbutton.setBackgroundResource(R.color.transparent);
                    dbutton.setTextColor(getResources().getColor(R.color.black));
                } catch (Resources.NotFoundException e)
                {
                    Log.e("Error", "Can't find style. Error: ", e);
                }
            }
            x = 0;
            final ArrayList<LocationData> circle = new ArrayList<>();

            FirebaseDatabase.getInstance().getReference().child("Location").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    LocationData temp;
                    FirebaseUser locationUser = FirebaseAuth.getInstance().getCurrentUser();
                    DatabaseReference locationUserChild = FirebaseDatabase.getInstance().getReference("Location");
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                    {
                        return;
                    }
                    googleMap.setMyLocationEnabled(true);
                    getLocation();
                    float zoom = 15;
                   googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, longt), zoom));

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        LocationData data = snapshot.getValue(LocationData.class);
                        DatabaseReference currentLocationUserChild = locationUserChild.child(locationUser.getUid());

                        String uid = currentLocationUserChild.toString();
                        circle.add(new LocationData(uid));
                        temp = circle.get(x);
                        Resources res = getResources();
                        temp.addcircle(googleMap.addCircle(new CircleOptions()
                                .center(new LatLng(data.latitude, data.longitude))
                                .radius(data.radius)
                                .strokeColor(getRedcolor(data.rating, res))
                                .fillColor(getRedcolor(data.rating, res))
                                .clickable(true)));
                        x++;
                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            googleMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener()
            {
                @Override
                public void onCircleClick(final Circle circles)
                {
                    circles.getCenter();

                    final DatabaseReference locationUserChild = FirebaseDatabase.getInstance().getReference("Location");

                    int strokeColor = circles.getStrokeColor() ^ 0x00ffffff;
                    circles.setStrokeColor(strokeColor);

                    FirebaseDatabase.getInstance().getReference().child("Location").addListenerForSingleValueEvent(new ValueEventListener()
                    {
                        @SuppressLint("ResourceType")
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                final LocationData data = snapshot.getValue(LocationData.class);
                                LatLng save;

                                save = new LatLng(data.latitude, data.longitude);
                                if (circles.getCenter().equals(save))
                                {
                                    final String key = snapshot.getKey();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    builder.setMessage(getResources().getString(R.string.area_name) + " " + data.areaNickname + "\n"
                                            + getResources().getString(R.string.rating) + " " + data.rating + "\n"
                                            + getResources().getString(R.string.latitude) + " " + data.latitude + "\n"
                                            + getResources().getString(R.string.longitude) + " " + data.longitude + "\n"
                                            + getResources().getString(R.string.radius) + " " + data.radius + "\n"
                                            + getResources().getString(R.string.contributor) + " " + data.contributor).setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id)
                                        {
                                            int strokeColor = circles.getStrokeColor() ^ 0x00ffffff;
                                            circles.setStrokeColor(strokeColor);
                                        }
                                    });
                                    builder.setNegativeButton("Delete", new DialogInterface.OnClickListener(){

                                        @Override
                                        public void onClick(DialogInterface dialog, int which)
                                        {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                            builder.setMessage("Are you sure you want to delete the selected location?");
                                            builder.setPositiveButton("Yes",  new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id)
                                                {
                                                    googleMap.clear();
                                                    FirebaseDatabase.getInstance().getReference("Location").child(key).removeValue();
                                                    FirebaseDatabase.getInstance().getReference().child("Location").addListenerForSingleValueEvent(new ValueEventListener()
                                                    {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            LocationData temp;
                                                            FirebaseUser locationUser = FirebaseAuth.getInstance().getCurrentUser();
                                                            DatabaseReference locationUserChild = FirebaseDatabase.getInstance().getReference("Location");

                                                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                                LocationData data = snapshot.getValue(LocationData.class);
                                                                DatabaseReference currentLocationUserChild = locationUserChild.child(locationUser.getUid());

                                                                String uid = currentLocationUserChild.toString();
                                                                circle.add(new LocationData(uid));
                                                                temp = circle.get(x);
                                                                Resources res = getResources();
                                                                temp.addcircle(googleMap.addCircle(new CircleOptions()
                                                                        .center(new LatLng(data.latitude, data.longitude))
                                                                        .radius(data.radius)
                                                                        .strokeColor(getRedcolor(data.rating, res))
                                                                        .fillColor(getRedcolor(data.rating, res))
                                                                        .clickable(true)));
                                                                x++;
                                                            }
                                                        }
                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError error) {
                                                        }
                                                    });
                                                }
                                            });
                                            builder.setNegativeButton("No", new DialogInterface.OnClickListener(){
                                                @Override
                                                public void onClick(DialogInterface dialog, int which)
                                                {
                                                    int strokeColor = circles.getStrokeColor() ^ 0x00ffffff;
                                                    circles.setStrokeColor(strokeColor);
                                                }
                                            });
                                            AlertDialog a = builder.create();
                                            a.show();
                                        }
                                    });
                                    AlertDialog a = builder.create();
                                    a.show();
                                    break;
                                }
                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error)
                        {

                        }
                    });


                }
            });

            googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener()
            {
                @Override
                public void onCameraIdle()
                {
                    if (!addbutton)
                    {
                    LatLng center = googleMap.getCameraPosition().target;
                    googleMap.clear();

                    FirebaseDatabase.getInstance().getReference().child("Location").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            LocationData temp;
                            FirebaseUser locationUser = FirebaseAuth.getInstance().getCurrentUser();
                            DatabaseReference locationUserChild = FirebaseDatabase.getInstance().getReference("Location");

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                LocationData data = snapshot.getValue(LocationData.class);
                                DatabaseReference currentLocationUserChild = locationUserChild.child(locationUser.getUid());

                                String uid = currentLocationUserChild.toString();
                                circle.add(new LocationData(uid));
                                temp = circle.get(x);
                                Resources res = getResources();
                                temp.addcircle(googleMap.addCircle(new CircleOptions()
                                        .center(new LatLng(data.latitude, data.longitude))
                                        .radius(data.radius)
                                        .strokeColor(getRedcolor(data.rating, res))
                                        .fillColor(getRedcolor(data.rating, res))
                                        .clickable(true)));
                                x++;
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
                }

            });

            final int[] seekrating = new int[1];
            final int[] seekrad = new int[1];
            seekrating[0]=3;
            seekrad[0]=100;
            abutton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    addbutton=true;
                    abutton.setVisibility(View.GONE);
                    bbutton.setVisibility(View.VISIBLE);
                    cbutton.setVisibility(View.VISIBLE);
                    getLocation();
                    centerMarker = googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(lat, longt)));

                    addcircle = googleMap.addCircle(new CircleOptions()
                            .center(new LatLng(lat, longt))
                            .radius(100)
                            .strokeColor(getRedcolor(1, getResources()))
                            .fillColor(getRedcolor(1, getResources()))
                            .clickable(true));
                    googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener()
                    {
                        @Override
                        public void onMapClick(LatLng point)
                        {
                            if(!nextbtn)
                            {
                                centerMarker.setPosition(point);
                                addcircle.setCenter(point);
                            }
                        }
                    });
                }
            });
            bbutton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    addbutton=false;
                    nextbtn=false;
                    abutton.setVisibility(View.VISIBLE);
                    bbutton.setVisibility(View.GONE);
                    cbutton.setVisibility(View.GONE);
                    dbutton.setVisibility(View.GONE);
                    radseekbar.setVisibility(View.GONE);
                    centerMarker.remove();
                    addcircle.remove();
                }
            });
            radseekbar= getActivity().findViewById(R.id.radseekBar);
            cbutton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    nextbtn=true;
                    abutton.setVisibility(View.GONE);
                    bbutton.setVisibility(View.VISIBLE);
                    cbutton.setVisibility(View.GONE);
                    dbutton.setVisibility(View.VISIBLE);
                    radseekbar.setVisibility(View.VISIBLE);

                    lat=centerMarker.getPosition().latitude;
                    longt=centerMarker.getPosition().longitude;

                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, longt), 15));

                    centerMarker.remove();
                    radseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
                    {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
                        {
                            Resources res = getResources();
                            addcircle.setRadius((double)progress);
                            seekrad[0] = progress;
                        }
                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {}
                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {}
                    });


                }
            });
            dbutton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    nextbtn=false;
                    addbutton=false;
                    abutton.setVisibility(View.VISIBLE);
                    bbutton.setVisibility(View.GONE);
                    cbutton.setVisibility(View.GONE);
                    dbutton.setVisibility(View.GONE);
                    radseekbar.setVisibility(View.GONE);
                    addcircle.remove();

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Name and Rating");
                    final View customLayout = getLayoutInflater().inflate(R.layout.alertdialog_rating, null);
                    builder.setView(customLayout);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            areaNickname= customLayout.findViewById(R.id.areaNicknameAlertDialog);
                            areaRating = customLayout.findViewById(R.id.ratingAlertDialog);

                            String areaNicknameText, areaRatingText, contributorText;

                            areaNicknameText = areaNickname.getText().toString().trim();
                            areaRatingText = areaRating.getText().toString().trim();

                            if(areaNicknameText.isEmpty() || areaRatingText.isEmpty()){
                                Toast.makeText(getActivity(), "Area Nickname and Rating cannot be empty", Toast.LENGTH_LONG).show();
                                return;
                            }
                            else if(Pattern.matches("[a-zA-Z]+", areaRatingText)){
                                Toast.makeText(getActivity(), "Area Rating cannot contain letters", Toast.LENGTH_LONG).show();
                                return;
                            }

                            int ratingInput = Integer.parseInt(areaRatingText);
                            if(ratingInput < 1 || ratingInput > 10){
                                Toast.makeText(getActivity(), "The entered rating must be between 0 and 10", Toast.LENGTH_LONG).show();
                            }
                            else{

                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                contributorText = user.getDisplayName();

                                LocationData locationData = new LocationData();

                                DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Location");

                                int radiusInput = seekrad[0];
                                float latitudeInput = (float) lat;
                                float longitudeInput = (float) longt;

                                if(!areaNicknameText.equals(""))
                                {
                                    locationData.setAreaNickname(areaNicknameText);
                                    locationData.setLatitude(latitudeInput);
                                    locationData.setLongitude(longitudeInput);
                                    locationData.setRadius(radiusInput);
                                    locationData.setRating(ratingInput);
                                    locationData.setContributor(contributorText);
                                    database.push().setValue(locationData);
                                }
                                else
                                {

                                    // display toast error saying missing  areaname
                                }
                            }
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

        }


    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.gps_fragment, container, false);
    }

    public void onStop()
    {
        super.onStop();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null)
        {
            mapFragment.getMapAsync(callback);
        }
    }

    private static final int PATTERN_GAP_LENGTH_PX = 20;
    private static final PatternItem DOT = new Dot();
    private static final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);

    private static final List<PatternItem> PATTERN_POLYLINE_DOTTED = Arrays.asList(GAP, DOT);
    private static final int COLOR_WHITE_ARGB = 0xffffffff;
    private static final int COLOR_GREEN_ARGB = 0xff388E3C;
    private static final int COLOR_PURPLE_ARGB = 0xff81C784;
    private static final int COLOR_ORANGE_ARGB = 0xffF57F17;
    private static final int COLOR_BLUE_ARGB = 0xffF9A825;
    private static final int POLYGON_STROKE_WIDTH_PX = 8;
    private static final int PATTERN_DASH_LENGTH_PX = 20;
    private static final PatternItem DASH = new Dash(PATTERN_DASH_LENGTH_PX);
    private static final List<PatternItem> PATTERN_POLYGON_ALPHA = Arrays.asList(GAP, DASH);
    private static final List<PatternItem> PATTERN_POLYGON_BETA = Arrays.asList(DOT, GAP, DASH, GAP);


    public int getRedcolor(int radius, Resources res) {
        int color = res.getColor(R.color.red1);

        switch (radius) {
            case 1:
                color = res.getColor(R.color.red1);
                break;
            case 2:
                color = res.getColor(R.color.red2);
                break;
            case 3:
                color = res.getColor(R.color.red3);
                break;
            case 4:
                color = res.getColor(R.color.red4);
                break;
            case 5:
                color = res.getColor(R.color.red5);
                break;
            case 6:
                color = res.getColor(R.color.red6);
                break;
            case 7:
                color = res.getColor(R.color.red7);
                break;
            case 8:
                color = res.getColor(R.color.red8);
                break;
            case 9:
                color = res.getColor(R.color.red9);
                break;
            case 10:
                color = res.getColor(R.color.red10);
                break;
        }
        return color;
    }

    private void getLocation()
    {
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
            }
        }
    }
}