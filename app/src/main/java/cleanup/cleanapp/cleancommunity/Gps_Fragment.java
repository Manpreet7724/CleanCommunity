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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.location.LocationManager;

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


public class Gps_Fragment extends Fragment
{
    public static String holdArea;
    FirebaseUser user;
    FirebaseAuth auth;
    int x;
    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    FirebaseDatabase database;
    EditText areaNickname, radius, longitude, latitude, rating, contributor;
    String areaNicknameText, radiusText, longitudeText, latitudeText, ratingText, contributorText;
    public double lat;
    public double longt;
    Button abutton,bbutton,cbutton;
    public String holdAreaname;
    Marker centerMarker;
    Circle addcircle;
    Boolean addbutton =false;

    public static Gps_Fragment getInstance() {
        Gps_Fragment chatFragment = new Gps_Fragment();
        return chatFragment;
    }

    private final OnMapReadyCallback callback = new OnMapReadyCallback()
    {
        @Override
        public void onMapReady(final GoogleMap googleMap)
        {
            x = 0;
            final ArrayList<LocationData> circle = new ArrayList<>();

            FirebaseDatabase.getInstance().getReference().child("Location").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
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
                public void onCircleClick(final Circle circle)
                {
                    circle.getCenter();

                    final DatabaseReference locationUserChild = FirebaseDatabase.getInstance().getReference("Location");

                    int strokeColor = circle.getStrokeColor() ^ 0x00ffffff;
                    circle.setStrokeColor(strokeColor);

                    FirebaseDatabase.getInstance().getReference().child("Location").addListenerForSingleValueEvent(new ValueEventListener()
                    {
                        @SuppressLint("ResourceType")
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                final LocationData data = snapshot.getValue(LocationData.class);
                                LatLng save;

                                save = new LatLng(data.latitude, data.longitude);
                                if (circle.getCenter().equals(save))
                                {
                                    final String key = snapshot.getKey();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    builder.setMessage(getResources().getString(R.string.area_name) + " " + data.areaNickname + "\n"
                                            + getResources().getString(R.string.rating) + " " + data.rating + "\n"
                                            + getResources().getString(R.string.latitude) + " " + data.latitude + "\n"
                                            + getResources().getString(R.string.longitude) + " " + data.longitude + "\n"
                                            + getResources().getString(R.string.radius) + " " + data.radius + "\n"
                                            + getResources().getString(R.string.contributor) + " " + data.contributor).setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            int strokeColor = circle.getStrokeColor() ^ 0x00ffffff;
                                            circle.setStrokeColor(strokeColor);

                                        }
                                    });
                                    builder.setNegativeButton("Delete", new DialogInterface.OnClickListener(){

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            FirebaseDatabase.getInstance().getReference("Location").child(key).removeValue();
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
                public void onCameraIdle() {
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


            abutton= getActivity().findViewById(R.id.getStarbutton);
            bbutton= getActivity().findViewById(R.id.btn_cancel);
            abutton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    addbutton=true;
                    abutton.setVisibility(View.GONE);
                    bbutton.setVisibility(View.VISIBLE);
                    //cbutton.setVisibility(View.VISIBLE);
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
                            centerMarker.setPosition(point);
                            addcircle.setCenter(point);
                        }
                    });
                    googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener()
                    {
                        @Override
                        public void onMarkerDragStart(Marker marker)
                        {
                        }

                        @Override
                        public void onMarkerDragEnd(Marker marker)
                        {
                            lat = centerMarker.getPosition().latitude;
                            longt=centerMarker.getPosition().longitude;
                        }

                        @Override
                        public void onMarkerDrag(Marker marker)
                        {
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
                    abutton.setVisibility(View.VISIBLE);
                    bbutton.setVisibility(View.GONE);
                    //cbutton.setVisibility(View.GONE);
                    centerMarker.remove();
                    addcircle.remove();
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