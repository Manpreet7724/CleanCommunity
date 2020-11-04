package cleanup.cleanapp.cleancommunity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CustomCap;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;

import java.util.Arrays;
import java.util.List;

public class gps_Fragment extends Fragment
{

    private OnMapReadyCallback callback = new OnMapReadyCallback()
    {
        @Override
        public void onMapReady(GoogleMap googleMap)
        {
            Polygon polygon1 = googleMap.addPolygon(new PolygonOptions()
                    .clickable(true)
                    .add(
                            new LatLng(-27.457, 153.040),
                            new LatLng(-33.852, 151.211),
                            new LatLng(-37.813, 144.962),
                            new LatLng(-34.928, 138.599)));

            stylePolygon(polygon1);

            LatLng sydney = new LatLng(-34, 151);
            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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

        // Create a stroke pattern of a gap followed by a dash.
        private static final List<PatternItem> PATTERN_POLYGON_ALPHA = Arrays.asList(GAP, DASH);

        // Create a stroke pattern of a dot followed by a gap, a dash, and another gap.
        private static final List<PatternItem> PATTERN_POLYGON_BETA =
                Arrays.asList(DOT, GAP, DASH, GAP);


        private void stylePolygon(Polygon polygon)
        {
        String type = "";
        // Get the data object stored with the polygon.
        if (polygon.getTag() != null) {
            type = polygon.getTag().toString();
        }

        List<PatternItem> pattern = null;
        int strokeColor = 0xAFFF5F49 ;
        int fillColor = 0xAFFF5F49;


        polygon.setStrokePattern(pattern);
        polygon.setStrokeWidth(POLYGON_STROKE_WIDTH_PX);
        polygon.setStrokeColor(strokeColor);
        polygon.setFillColor(fillColor);
    }


}