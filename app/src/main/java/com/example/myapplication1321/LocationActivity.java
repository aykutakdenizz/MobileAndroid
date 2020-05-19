package com.example.myapplication1321;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class LocationActivity extends Activity {

    private FusedLocationProviderClient fusedLocationClient;
    private Button getButton,sendButton;
    TextView textLongitude,textLatitude;
    private LocationActivity thisActivity = this;
    ImageView img;
    LocationCallback locationCallback;
    LocationRequest locationRequest;
    double longitude;
    double latitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        setComponent();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_NO_POWER);

        setLocationCallBack();
        setClickListeners();



    }

    private void setComponent(){
        getButton = (Button) findViewById(R.id.locationGetLocation);
        sendButton = (Button) findViewById(R.id.locationSendLocation);
        textLatitude = (TextView) findViewById(R.id.locationGetLatitude);
        textLongitude = (TextView) findViewById(R.id.locationGetLongitude);
        img = (ImageView) findViewById(R.id.LocationImage);
        img.setBackground(null);
    }
    private void setLocationCallBack(){
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                }
            }
        };
    }
    private void setClickListeners(){
        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(thisActivity, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if (location != null) {
                                    textLatitude.setText(Double.toString(location.getLatitude()));
                                    textLongitude.setText(Double.toString(location.getLongitude()));
                                    longitude = location.getLongitude();
                                    latitude = location.getLatitude();
                                    img.setBackground(getApplicationContext().getDrawable(R.drawable.find_location));
                                } else {
                                    fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                                }
                            }
                        });
            }
        });
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "http://maps.google.com/maps?saddr=" + latitude + "," + longitude;
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, message);
                sendIntent.setType("text/plain");
                sendIntent.setPackage("com.whatsapp");
                startActivity(sendIntent);
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
    }

    private void startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }
}
