package com.example.angela.googlemapapi;

import android.location.Address;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.location.Geocoder;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LatLng ll1;
    LatLng ll2;

    LatLng getLatLng(String myAddress) throws IOException {
        Geocoder coder = new Geocoder(this);
        List<Address> address;
        address = coder.getFromLocationName("my add", 5);
        System.out.print(address.get(0));
        /*if (address == null) {
            return;
        }
*/
        //Lets take first possibility from the all possibilities.
        Address location = address.get(0);
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        return latLng;
    }

    LatLng mid(LatLng l1, LatLng l2){
        double lat = (l1.latitude + l2.latitude)/2;
        double lng = (l1.longitude + l2.longitude)/2;
        LatLng newll = new LatLng(lat, lng);
        return newll;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        try {
            final TextView tv1 = (TextView) findViewById(R.id.place_1);
            final TextView tv2 = (TextView) findViewById(R.id.place_2);
            String add1 = tv1.getText().toString();
            String add2 = tv2.getText().toString();
            ll1 = getLatLng(add1);
            ll2 = getLatLng(add2);
            final ImageView image = (ImageView) findViewById(R.id.start_image);
            image.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    LatLng midPoint = mid(ll1,ll2);
                    mMap.addMarker(new MarkerOptions().position(midPoint).title("Where2Meet"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(midPoint));
                } });
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(77, -51);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
