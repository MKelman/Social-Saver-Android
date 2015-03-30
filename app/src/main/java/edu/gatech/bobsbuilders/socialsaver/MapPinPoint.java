package edu.gatech.bobsbuilders.socialsaver;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MapPinPoint extends FragmentActivity implements OnMapLongClickListener,OnMapClickListener,OnMarkerDragListener {

    private GoogleMap map;
    private double dragLat, dragLong;
    private ParseObject sDeal;
    private Date endDate;
    private String userEmail;
    private String actualLocation;
    private String item;
    private String foundLocation;
    private Double maxPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_pin_point);

        Bundle data = getIntent().getExtras();

        userEmail = data.getString("userEmail");
        actualLocation = data.getString("actualLocation");
        item = data.getString("item");
        String saleEndDate = data.getString("saleEndDate");
        foundLocation = data.getString("foundLocation");
        maxPrice = data.getDouble("maxPrice");
        Button Submit = (Button)findViewById(R.id.submitDealWithLoc);
        @SuppressLint("SimpleDateFormat") DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        try {
            endDate = formatter.parse(saleEndDate);
        } catch(ParseException e) {
            endDate = null;
        }

        map  = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapPoint)).getMap();
        map.setOnMarkerDragListener(MapPinPoint.this);
        map.setOnMapLongClickListener(MapPinPoint.this);
        map.setOnMapClickListener(MapPinPoint.this);

        GPSTracker gps = new GPSTracker(MapPinPoint.this);
        double latitude = gps.getLatitude();
        double longitude = gps.getLongitude();
        LatLng LOCATION = new LatLng(latitude, longitude);
        dragLat = latitude;
        dragLong = longitude;

        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION, 16);
        map.animateCamera(update);
        /*
        Marker marker = map.addMarker(new MarkerOptions()
                .position(LOCATION)
                .draggable(true));
        */
        //marker = map.addMarker(new MarkerOptions().position(LOCATION).title("Find me here!"));
        Submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                sDeal = new ParseObject("FoundDeals");
                sDeal.put("userEmail", userEmail);
                sDeal.put("actualLocation", actualLocation);
                sDeal.put("item", item);
                sDeal.put("saleEndDate", endDate);
                sDeal.put("foundLocation", foundLocation);

                sDeal.put("maxPrice", maxPrice);
                ParseGeoPoint point = new ParseGeoPoint(dragLat, dragLong);
                sDeal.put("userLocation", point);

                sDeal.saveInBackground();
                Toast.makeText(MapPinPoint.this, "Found Deal Posted!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MapPinPoint.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override public void onMarkerDrag(Marker arg0) { // TODO Auto-generated method stub
    //
    }
    @Override public void onMarkerDragEnd(Marker arg0) {
    // TODO Auto-generated method stub
        LatLng dragPosition = arg0.getPosition();
        dragLat = dragPosition.latitude;
        dragLong = dragPosition.longitude;
        //Log.i("info", "on drag end :" + dragLat + " dragLong :" + dragLong);
        //Toast.makeText(getApplicationContext(), "Marker Dragged..!", Toast.LENGTH_LONG).show();
    }
    @Override
    public void onMarkerDragStart(Marker arg0) {
        // TODO Auto-generated method stub
    }
    @Override
    public void onMapClick(LatLng arg0) {
    // TODO Auto-generated method stub
        map.animateCamera(CameraUpdateFactory.newLatLng(arg0));
    }
    @Override public void onMapLongClick(LatLng arg0) {
    // TODO Auto-generated method stub
    // create new marker when user long clicks
        //map.addMarker(new MarkerOptions() .position(arg0) .draggable(true));
    }
}