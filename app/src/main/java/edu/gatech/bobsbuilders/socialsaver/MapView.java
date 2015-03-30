package edu.gatech.bobsbuilders.socialsaver;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapView extends Activity {

    //private final LatLng LOCATION_SURRREY = new LatLng(49.187500, -122.849000);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);

        Bundle data = getIntent().getExtras();

        String foundLoc = data.getString("FOUNDLOCATION");
        TextView tvonline = (TextView)findViewById(R.id.tvonline);
        GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        if (foundLoc.equals("In-Store")) {
            tvonline.setVisibility(View.INVISIBLE);
            double latitude = data.getDouble("LAT");
            double longitude = data.getDouble("LONG");
            LatLng LOCATION = new LatLng(latitude, longitude);
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION, 16);
            map.animateCamera(update);

            map.addMarker(new MarkerOptions().position(LOCATION).title("Find me here!"));

        } else {
            tvonline.setVisibility(View.VISIBLE);
        }



    }
}