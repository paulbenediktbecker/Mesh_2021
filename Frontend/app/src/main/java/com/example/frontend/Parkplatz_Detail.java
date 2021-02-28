package com.example.frontend;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Parkplatz_Detail extends AppCompatActivity {

    ImageButton buttonBack;
    Button buttonBookNow;
    private List<Address> addresses ;
    private List<ExampleItem> itemList = new ArrayList<>();

    private String mIdAdresse;
    private String mIDLot;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parkplatz__detail);

        String Position = getIntent().getStringExtra("POSITION");
        System.out.println("POSITION:" + Position);
        int pos = Integer.parseInt(Position);
        addresses = AddresslistDTO.getAddresses();
        System.out.println("HIIIIIER" + addresses.size());

        addresses.forEach(address -> {
            address.getParkingLots().forEach(parkingLot -> {
                itemList.add(new ExampleItem(R.drawable.garage, String.format("%s, %s", address.getZipcode(), address.getCity()), String.format("%s Euro/h - Typ: %s", parkingLot.getPrice(), parkingLot.getType()),address.getIdentifier().toString(), parkingLot.getIdentifier().toString()));
                System.out.println("ADDED AN ITEM");
            });
        });
        System.out.println("Size Itemlist : " + itemList.size());
        changevalues(itemList.get(pos));

        ExampleItem temp = itemList.get(pos);
        mIdAdresse = temp.getmIdAdresse();
        mIDLot = temp.getmIDLot();

        // Button Back
        buttonBack = (ImageButton) findViewById(R.id.BTNBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonBackClick();
            }
        });

        // Button BookNow
        buttonBookNow = (Button) findViewById(R.id.BTNBook);
        buttonBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonBookNowClick();
            }
        });

        System.out.println("ADDRESS:" + AddresslistDTO.getAddresses());
    }

    // go back to main()
    private void onButtonBackClick(){
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }


    private void onButtonBookNowClick(){
        MainActivity.getRequestClient().toggleBookingStatus(mIdAdresse, mIDLot).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()) {
                    if (response.body()){
                        Toast.makeText(Parkplatz_Detail.this, "Sucessfully booked! ",Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(Parkplatz_Detail.this, "Removed Booking! ",Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    private void changevalues(ExampleItem p_item){
        ImageView img = findViewById(R.id.ImageViewDetail);
        img.setImageResource(p_item.getImageResource());

        TextView text1 = findViewById(R.id.textPrice);
        text1.setText(p_item.getText2());

        TextView text2 = findViewById(R.id.textStreet);
        text2.setText((p_item.getText1()));
    }
}
