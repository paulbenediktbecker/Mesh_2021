package com.example.frontend;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * An parking-controller Übersicht aus Swagger orientiert:
 */
public interface ParkingController {

    @GET("parking/address")
    Call<List<Address>> getAllAddresses();

    //1. Gucken ob addresse schon existiert
    //2. Wenn nicht Addresse erstellen und UUID bekommen --> POST: /parking/address
    //3. SOnst UUID der Addresse erhalten --> GET: /parking/address/city/{city}/street/{street} --> sollte nur 1 Ergebnis sein
    //4. Parkplatz zu der Addresse erstellen --> POST: /parking/address/{addressIdentifier}/parking/add

    @POST("parking/address/{addressIdentifier}/parking/{parkinglotIdentifier}/toggle-status")
    Call<Boolean> toggleBookingStatus(@Path("addressIdentifier") String addressIdentifier, @Path("parkinglotIdentifier") String parkinglotIdentifier);

    @POST("/parking/address")
    Call<Address> createAddress(@Query("zipcode") String zipcode, @Query("city") String city, @Query("street") String street, @Query("geotag") String geotag );

    @POST("parking/address/{addressIdentifier}/parking/add")
    Call<Address> createParkinglot(@Path("addressIdentifier") String addressIdentifier, @Body ParkingLot parkingLot);

    @GET("parking/address/{addressIdentifier}/parking")
    Call<ParkingLot> getParkingLotsByAdressIdentifier();

    @GET("parking/address/{zipcode}")
    Call<Address> getAdressByZipcode();

    @GET("parking/address/{zipcode}/{street}")
    Call<Address> getAdressByZipcodeAndStreet();

    @GET("parking/address/{city}")
    Call<Address> getAdressByCity();

    @GET("parking/address/{city}/{street}")
    Call<Address> getAdressByCityAndStreet();

    @GET("parking/address/{addressIdentifier}")
    Call<Address> getAdressByAdressIdentifier();

    }
