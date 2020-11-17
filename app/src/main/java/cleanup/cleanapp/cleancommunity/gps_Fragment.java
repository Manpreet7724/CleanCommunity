
package cleanup.cleanapp.cleancommunity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.CustomCap;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
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

public class gps_Fragment extends Fragment
{
    FirebaseUser user;
    FirebaseAuth auth;
    FirebaseDatabase database;

    private OnMapReadyCallback callback = new OnMapReadyCallback()
    {
        @Override
        public void onMapReady(final GoogleMap googleMap)
        {
            
            final ArrayList<LocationData> circle = new ArrayList<>();

            LocationData temp;
            FirebaseDatabase.getInstance().getReference().child("Location").addListenerForSingleValueEvent(new ValueEventListener(){
               @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                   LocationData temp;
                   FirebaseUser locationUser = FirebaseAuth.getInstance().getCurrentUser();
                   DatabaseReference locationUserChild = FirebaseDatabase.getInstance().getReference("Location");
                   int x = 0;

                   for(DataSnapshot snapshot : dataSnapshot.getChildren())
                   {
                       LocationData data = snapshot.getValue(LocationData.class);
                       DatabaseReference currentLocationUserChild = locationUserChild.child(locationUser.getUid());

                       Log.d("TAG", "onDataChange: "+data.latitude);
                       String uid = currentLocationUserChild.toString();
                       circle.add(new LocationData(uid));
                       temp = circle.get(x);
                       temp.addcircle(googleMap.addCircle(new CircleOptions()
                               .center(new LatLng(data.latitude, data.longitude))
                               .radius(data.radius)
                               .strokeColor(Color.RED)
                                .fillColor(Color.RED)
                                .clickable(true)));
                       x++;
                   }
               }
                @Override
                public void onCancelled(@NonNull DatabaseError error){

                }
            });

            googleMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {
                @Override
                public void onCircleClick(Circle circle)
                {
                    // Flip the r, g and b components of the circle's stroke color.
                    int strokeColor = circle.getStrokeColor() ^ 0x00ffffff;
                    circle.setStrokeColor(strokeColor);
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
        private static final List<PatternItem> PATTERN_POLYGON_BETA =
                Arrays.asList(DOT, GAP, DASH, GAP);






}