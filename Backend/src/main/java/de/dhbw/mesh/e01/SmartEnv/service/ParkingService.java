package de.dhbw.mesh.e01.SmartEnv.service;

import de.dhbw.mesh.e01.SmartEnv.domain.Address;
import de.dhbw.mesh.e01.SmartEnv.domain.ParkingLot;
import de.dhbw.mesh.e01.SmartEnv.domain.ParkingLotDTO;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Collection;
import java.util.UUID;

/**
 * Service interface to manage {@link de.dhbw.mesh.e01.SmartEnv.domain.Address} entities.
 */
@Validated
public interface ParkingService {

    public Address createAddress(String city, String street, String zipcode, String geotag);

    public Address updateAddress(UUID uuid, String city, String street, String zipcode, String geotag);

    public void deleteAddress(UUID uuid);

    public Address getAddress(UUID uuid);

    public Collection<Address> getAllAddress();

    public Collection<Address> getAddressesCity(String city);

    public Collection<Address> getAddressesCityStreet(String city, String street);

    public Collection<Address> getAddressesZipcode(String zipcode);

    public Collection<Address> getAddressesZipcodeStreet(String zipcode, String street);

    public Collection<Address> getAddressesZipcodeStreetCity(String zipcode, String street, String city);

    public Address addParkingLot(UUID uuid, @Valid ParkingLotDTO parkingLot);

    public Address updateParkingLot(UUID uuid, UUID parkinglotIdentifier, @Valid ParkingLotDTO parkingLot);

    public Address removeParkingLot(UUID addressId, UUID parkingLot);

    public Collection<ParkingLot> getParkinglotsOfAddress(UUID addressId);

    public ParkingLot getParkinglot(UUID parkinglotId);
}


