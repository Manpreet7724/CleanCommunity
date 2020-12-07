
package cleanup.cleanapp.cleancommunity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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
import com.google.android.gms.maps.model.PatternItem;
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
import java.util.zip.Inflater;

public class Gps_Fragment extends Fragment
{
    public static String holdArea;
    FirebaseUser user;
    FirebaseAuth auth;
    int x;
    private GoogleMap gMap;
    FirebaseDatabase database;
    EditText areaNickname, radius, longitude, latitude, rating, contributor;
    String areaNicknameText, radiusText, longitudeText, latitudeText, ratingText, contributorText;
    public String holdAreaname;


    public static Gps_Fragment getInstance()
    {
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
                    //googleMap.setMyLocationEnabled(true);
                    for (DataSnapshot snapshot : dataSnapshot.getChildren())
                    {
                        LocationData data = snapshot.getValue(LocationData.class);
                        DatabaseReference currentLocationUserChild = locationUserChild.child(locationUser.getUid());

                        String uid = currentLocationUserChild.toString();
                        circle.add(new LocationData(uid));
                        temp = circle.get(x);
                        Resources res = getResources();
                        temp.addcircle(googleMap.addCircle(new CircleOptions()
                                .center(new LatLng(data.latitude, data.longitude))
                                .radius(data.radius)
                                .strokeColor(getRedcolor(data.radius,res))
                                .fillColor(getRedcolor(data.radius,res))
                                .clickable(true)));
                       gMap= googleMap;
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

                    DatabaseReference locationUserChild = FirebaseDatabase.getInstance().getReference("Location");

                    int strokeColor = circle.getStrokeColor() ^ 0x00ffffff;
                    circle.setStrokeColor(strokeColor);

                    FirebaseDatabase.getInstance().getReference().child("Location").addListenerForSingleValueEvent(new ValueEventListener()
                    {
                        @SuppressLint("ResourceType")
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                LocationData data = snapshot.getValue(LocationData.class);
                                LatLng save;
                                save = new LatLng(data.latitude, data.longitude);
                                if (circle.getCenter().equals(save)) {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    builder.setMessage(getResources().getString(R.string.area_name) + " " + data.areaNickname + "\n"
                                            + getResources().getString(R.string.rating) + " " + data.rating + "\n"
                                            + getResources().getString(R.string.latitude) + " " + data.latitude + "\n"
                                            + getResources().getString(R.string.longitude) + " " + data.longitude + "\n"
                                            + getResources().getString(R.string.radius) + " " + data.radius + "\n"
                                            + getResources().getString(R.string.contributor) + " " + data.contributor).setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {


                                        }
                                    });
                                    AlertDialog a = builder.create();
                                    a.show();
                                    break;
                                }
                            }

                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                }
            });


            googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener()
            {
                @Override
                public void onCameraIdle()
                {
                    LatLng center = googleMap.getCameraPosition().target;
                    googleMap.clear();


                    FirebaseDatabase.getInstance().getReference().child("Location").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            LocationData temp;
                            FirebaseUser locationUser = FirebaseAuth.getInstance().getCurrentUser();
                            DatabaseReference locationUserChild = FirebaseDatabase.getInstance().getReference("Location");
                            //googleMap.setMyLocationEnabled(true);
                            for (DataSnapshot snapshot : dataSnapshot.getChildren())
                            {
                                LocationData data = snapshot.getValue(LocationData.class);
                                DatabaseReference currentLocationUserChild = locationUserChild.child(locationUser.getUid());

                                String uid = currentLocationUserChild.toString();
                                circle.add(new LocationData(uid));
                                temp = circle.get(x);
                                Resources res = getResources();
                                temp.addcircle(googleMap.addCircle(new CircleOptions()
                                        .center(new LatLng(data.latitude, data.longitude))
                                        .radius(data.radius)
                                        .strokeColor(getRedcolor(data.radius,res))
                                        .fillColor(getRedcolor(data.radius,res))
                                        .clickable(true)));
                                gMap= googleMap;
                                x++;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

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


//    @Override
//    public void onResume()
//    {
//        super.onResume();
//        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
//        if (mapFragment != null)
//        {
//            mapFragment.getMapAsync(callback);
//        }
//    }
    public void onStop() // tells user the activy was stoped
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


    public int getRedcolor(int radius, Resources res)
    {
        int  color = res.getColor(R.color.red1);

        switch (radius)
        {
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

}