package com.example.googlemaps;

import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    DatabaseReference db;
    ArrayList<Marker> markers = new ArrayList<>();
    Double lcont = 0.00, lgcont = 0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        db = FirebaseDatabase.getInstance().getReference();

        countDownTimer();
    }



    public void countDownTimer() {

        new CountDownTimer(15000,1500) {
            @Override
            public void onTick(long millisUntilFinished) {
                Double LaInit = 25.333113;
                Double LonInit = -103.521000;

                    lcont+=0.0010;
                    lgcont+=0.0010;

                HashMap<String,Object>localizacion=new HashMap<>();
                localizacion.put("Latitud",LaInit+lcont);
                localizacion.put("Longitud",LonInit+lgcont);
                db.child("Ubicacion").setValue(localizacion);
                onMapReady(mMap);
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        db.child("Ubicacion").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Mapa map = dataSnapshot.getValue(Mapa.class);
                Double Lon = map.getLongitud();
                Double lat = map.getLatitud();
                MarkerOptions mo = new MarkerOptions();

                mo.position(new LatLng(lat, Lon));
                markers.add(mMap.addMarker(mo));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
