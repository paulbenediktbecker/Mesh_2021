package com.example.frontend;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements ExampleAdapter.EntryListener {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ImageButton buttonAdd;
    ImageButton buttonMap;

    private List<Address> addresses = new ArrayList<>();

    static List<ExampleItem> itemList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<ExampleItem> exampleList = new ArrayList<>();

        fetchAllAddress();

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter(exampleList, this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Button Add
        buttonAdd = (ImageButton) findViewById(R.id.BTNADD);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartFormActivity();
            }
        });
        // Button Map
        buttonMap = (ImageButton) findViewById(R.id.BTNMap);

        buttonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartMapActivity();
            }
        });

        System.out.println("!!!!" + addresses.size());
    }


    //Methode um in Form activity zu wechseln
    public void StartFormActivity()
    {
        {
            Intent intent = new Intent(this, Parkplatz_form.class);
            startActivity(intent);
        }
    }

    //Methode die getriggert wird, wenn man auf einen der Einträge klickt
    @Override
    public void onEntryClick(int position) {
        Intent intent = new Intent(this, Parkplatz_Detail.class);
        intent.putExtra("POSITION","" + position);
        startActivity(intent);
    }

    private void StartMapActivity() {
        Intent intent = new Intent(this, Map2.class);

        startActivity(intent);
    }

    private static final String BASEURL = "http://floww-industries.de:8080/";
    private static Retrofit retrofit = new Retrofit.Builder().baseUrl(BASEURL).addConverterFactory(GsonConverterFactory.create()).build();
    private static ParkingController requestClient = retrofit.create(ParkingController.class);


    public static ParkingController getRequestClient() {
        return requestClient;
    }

    public void fetchAllAddress() {

        requestClient.getAllAddresses().enqueue(new Callback<List<Address>>() {
            @Override
            public void onResponse(Call<List<Address>> call, Response<List<Address>> response) {
                if(response.isSuccessful()) {
                    final List<Address> addressesList = response.body();

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void run() {
                            itemList.clear();
                            addressesList.forEach(address -> {
                                address.getParkingLots().forEach(parkingLot -> {
                                    itemList.add(new ExampleItem(R.drawable.garage, String.format("%s, %s", address.getZipcode(), address.getCity()), String.format("%s Euro/h - Typ: %s", parkingLot.getPrice(), parkingLot.getType()),address.getIdentifier().toString(), parkingLot.getIdentifier().toString()));
                                });
                            });
                            mAdapter = new ExampleAdapter(itemList, MainActivity.this);
                            mRecyclerView.setAdapter(mAdapter);

                            AddresslistDTO.setAddresses(addressesList);
                            System.out.println("DOOONE");
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Address>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });



        return;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}