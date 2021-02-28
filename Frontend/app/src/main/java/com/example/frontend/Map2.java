package com.example.frontend;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.CursorIndexOutOfBoundsException;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.api.Response;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Map2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient FLPC;
    ImageButton UpdateButton;
    ImageButton BackButton;
    Double[] CurrentLoc;

    private List<Address> addresses ;
    private List<ExampleItem> itemList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);





        CurrentLoc = new Double[2];

        // UpdateButton
        UpdateButton = (ImageButton) findViewById(R.id.UpdateButton);
        UpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickUpdateButton();
            }
        });

        //Back Button
        BackButton = (ImageButton) findViewById(R.id.BACKBTN);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBackButton();
            }
        });


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
        FLPC = LocationServices.getFusedLocationProviderClient(this);


        // get data für die ganzen pins
        addresses = AddresslistDTO.getAddresses();
        addresses.forEach(address -> {

            String[] s = address.getGeotag().split(";");
            Double d1 = Double.parseDouble(s[0]);
            Double d2 = Double.parseDouble(s[1]);


            //average preis pro adresse berechnen
            double sum = 0;
            double count = 0;
            for(ParkingLot lot: address.getParkingLots())
            {
                sum = sum + lot.getPrice();
                count++;
            }
            double avgPrice = sum/count;
            addMarker(d1,d2, avgPrice);
        });


    }

    public void getCurrentLocation(){
        Double[] ret = new Double[2];

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if( getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                // get the location
                FLPC.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if(location != null){
                                   ret[0] = location.getLatitude();
                                   ret[1] = location.getLongitude();
                                    Toast.makeText(Map2.this, "" + ret[0] + ", " + ret[1] ,
                                            Toast.LENGTH_LONG).show();
                                    SetCurrentLoc(ret[0], ret[1]);

                                }
                            }

                        });
            }
            else{
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }

        }


    }

    private void onClickUpdateButton() {
        getCurrentLocation();
        Double[] CurrentLocation = CurrentLoc;
        System.out.println(CurrentLocation[0] + "_-------------------");
        try {
            if(CurrentLocation[0] != null)
            {
                System.out.println("----------------jay------------------");
                // Add a marker in Sydney and move the camera
                LatLng sydney = new LatLng(CurrentLocation[0], CurrentLocation[1]);
                mMap.addMarker(new MarkerOptions().position(sydney).title("Your Current Position")
                        .icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));


            }
        }
        catch (NullPointerException E)
        {
            System.out.println("nullpointer bei Update Map");
        }
    }

    private void onClickBackButton() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }



    private void SetCurrentLoc(Double d1, Double d2)
    {
        CurrentLoc[0] = d1;
        CurrentLoc[1] = d2;
        System.out.println("skrrt " + d1 + d2);
    }

    private void addMarker(Double d1, Double d2, double price)
    {
        if(mMap == null)
        {
            System.out.println("MMap ist null");;
        }
        LatLng sydney = new LatLng(d1, d2);
        mMap.addMarker(new MarkerOptions().position(sydney).title("" + price + "€ / hour"));
    }

}
