package com.example.frontend;

import java.util.List;
import java.util.UUID;

public class Address {

    private UUID identifier;

    private String zipcode;

    private String street;

    private String city;

    private String geotag;

    private List<ParkingLot> parkingLots;


    public UUID getIdentifier() {
        return identifier;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getGeotag() {
        return geotag;
    }

    public List<ParkingLot> getParkingLots() {
        return parkingLots;
    }

    @Override
    public String toString() {
        return "Address{" +
                "identifier=" + identifier +
                ", zipcode='" + zipcode + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", geotag='" + geotag + '\'' +
                ", parkingLots=" + parkingLots +
                '}';
    }
}
