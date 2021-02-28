package com.example.frontend;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Parkplatz_form extends AppCompatActivity {

    Button button;
    ImageButton BackButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parkplatz_form);

        initSpinner();

        // Button
        button = (Button) findViewById(R.id.BTNSend);
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                SendForm();
            }
        });
        // Button
        BackButton = (ImageButton) findViewById(R.id.BACKBTNForm);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                backToMain();
            }
        });
    }

    private void backToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void initSpinner(){
        Spinner spinner = (Spinner) findViewById(R.id.InputLotType);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.LotTypes, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void SendForm(){
        String zip = "";
        String street= "";
        String city ="";
        String lotType ="";
        double price =0.0;
        String notes = "";

        //create Edittext Objects
        EditText EditTextZip = (EditText) findViewById(R.id.InputZipcode);
        EditText EditTextStreet = (EditText) findViewById(R.id.InputStreet);
        EditText EditTextCity= (EditText) findViewById(R.id.InputCity);
        Spinner SpinnerLotType = (Spinner) findViewById(R.id.InputLotType);
        EditText EditTextPrice = (EditText) findViewById(R.id.InputPrice);
        EditText EditTextNotes = (EditText) findViewById(R.id.InputNotes);

        // get values
        String ZipValue = "";
        String StreetValue = "";
        String CityValue = "";
        String LotTypeValue = "";
        double PriceValue = 0.0;
        String NotesValue = "";
        try {
             ZipValue = EditTextZip.getText().toString();
             StreetValue = EditTextStreet.getText().toString();
             CityValue = EditTextCity.getText().toString();
             LotTypeValue = SpinnerLotType.getSelectedItem().toString();
             PriceValue = Double.parseDouble(EditTextPrice.getText().toString());
             NotesValue = EditTextNotes.getText().toString();
        }
        // exception kommt wenn felder leer sind
        catch(java.lang.NumberFormatException E)
        {
            E.printStackTrace();
        }

        //give values to function
        Sendrequest(ZipValue,StreetValue,CityValue,LotTypeValue,PriceValue,NotesValue, 48.76049371133852d, 9.18197656367855d); //TODO @Paul bitte anpassen der Geodaten
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void Sendrequest(String pZip, String pStreet, String pCity, String pLotType, double pPrice, String pNotes, double lo, double lat) {
        AddresslistDTO.getAddresses().forEach(address -> {
            //Addresse vorhanden
            if (address.getZipcode().equalsIgnoreCase(pZip) && address.getCity().equalsIgnoreCase(pCity) && address.getStreet().equalsIgnoreCase(pStreet)) {
                String uuid = address.getIdentifier().toString();
                createParkinglot(pLotType, pPrice, pNotes, uuid);
                System.out.println("Added parkinglot to id " + uuid);
                return;
            }
        });
        //Addresse nicht vorhanden
        String geo = "" + lat + ";" + lo;
        Call<Address> call = MainActivity.getRequestClient().createAddress(pZip, pCity, pStreet, geo); //create address
        call.enqueue(new Callback<Address>() {
            @Override
            public void onResponse(Call<Address> call, Response<Address> response) {
                if (response.isSuccessful()) {
                    Address address1 = response.body();
                    String uuid = address1.getIdentifier().toString();
                    createParkinglot(pLotType, pPrice, pNotes, uuid);
                    Toast.makeText(Parkplatz_form.this, "Sucessfully creadted Offer! ",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Address> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    private void createParkinglot(String pLotType, double pPrice, String pNotes, String uuid) {
        System.out.println("Wichtiger Debug Print, da sonst Methode nicht funktioniert!!!!: " + pLotType);

        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setType(LotType.valueOf(pLotType));
        parkingLot.setPrice(pPrice);
        parkingLot.setRestrictions(null);
        parkingLot.setNotes(pNotes);

        MainActivity.getRequestClient().createParkinglot(uuid, parkingLot)
                .enqueue(new Callback<Address>() {
                    @Override
                    public void onResponse(Call<Address> call, Response<Address> response) {
                        if(response.isSuccessful()) {
                            System.out.println("Created parkinglot");
                        }
                    }

                    @Override
                    public void onFailure(Call<Address> call, Throwable t) {
                        System.out.println(t.getMessage());
                    }
                });
    }
}
