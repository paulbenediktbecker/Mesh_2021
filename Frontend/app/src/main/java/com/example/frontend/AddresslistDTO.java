package com.example.frontend;

import java.util.ArrayList;
import java.util.List;

public class AddresslistDTO {

    private static List<Address> addresses = new ArrayList<>();

    public static void setAddresses(List<Address> addresses) {
        AddresslistDTO.addresses = addresses;
    }

    public static List<Address> getAddresses() {
        return addresses;
    }
}
