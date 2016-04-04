package com.example.myposition;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Action2Activity extends AppCompatActivity {
    private LocationManager locManager;
    private LocationListener locListener;
    private Location mobileLocation;
    private TextView textView2;
    private Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action2);
        Intent intent = getIntent();
        textView2=(TextView)findViewById(R.id.textView2);
        button3 =(Button)findViewById(R.id.button3);


        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonGetLocationClick();
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        buttonGetLocationClick();
    }
    /** Gets the current location and update the mobileLocation variable*/
    private void getCurrentLocation() {
        locManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locListener = new LocationListener() {
            @Override
            public void onStatusChanged(String provider, int status,
                                        Bundle extras) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProviderEnabled(String provider) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProviderDisabled(String provider) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onLocationChanged(Location location) {
                // TODO Auto-generated method stub
                mobileLocation = location;
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);
    }

    private void buttonGetLocationClick() {
        getCurrentLocation(); // gets the current location and update mobileLocation variable

        if (mobileLocation != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locManager.removeUpdates(locListener); // This needs to stop getting the location data and save the battery power.
            String latitude = "Latitude: " + mobileLocation.getLatitude();
            String longitude = "Longitude: " + mobileLocation.getLongitude();
            String altitude = "Altitude: " + mobileLocation.getAltitude();

            textView2.setText(latitude + "\n" + longitude + "\n" + altitude);
        } else {
            textView2.setText("Sorry, location is not yet determined");
        }
    }
}