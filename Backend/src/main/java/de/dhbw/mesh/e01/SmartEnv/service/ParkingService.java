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

    /**
     * Create and save a new {@link Address} entity.
     * @param city
     * @param street
     * @param zipcode
     * @param geotag
     * @return
     */
    public Address createAddress(String city, String street, String zipcode, String geotag);

    /**
     * Update an existing {@link Address} entity.
     * @param uuid
     * @param city
     * @param street
     * @param zipcode
     * @param geotag
     * @return
     */
    public Address updateAddress(UUID uuid, String city, String street, String zipcode, String geotag);

    /**
     * Delete an existing {@link Address} entity.
     * @param uuid
     */
    public void deleteAddress(UUID uuid);

    /**
     * Get the {@link Address} with the given id.
     * @param uuid
     * @return
     */
    public Address getAddress(UUID uuid);

    /**
     * Get all {@link Address} entities.
     * @return
     */
    public Collection<Address> getAllAddress();

    /**
     * Get all {@link Address} of an city.
     * @param city
     * @return
     */
    public Collection<Address> getAddressesCity(String city);

    /**
     * Get all {@link Address} entities that matches the city and street.
     * @param city
     * @param street
     * @return
     */
    public Collection<Address> getAddressesCityStreet(String city, String street);

    /**
     * Get all {@link Address} entities that matches the zipcode.
     * @param zipcode
     * @return
     */
    public Collection<Address> getAddressesZipcode(String zipcode);

    /**
     * Get all {@link Address} entities that matches the zipcode and street.
     * @param zipcode
     * @param street
     * @return
     */
    public Collection<Address> getAddressesZipcodeStreet(String zipcode, String street);

    /**
     * Add an {@link ParkingLot} to an existing {@link Address}.
     * @param uuid
     * @param parkingLot
     * @return
     */
    public Address addParkingLot(UUID uuid, @Valid ParkingLotDTO parkingLot);

    /**
     * Update an existing {@link ParkingLot}.
     * @param uuid
     * @param parkinglotIdentifier
     * @param parkingLot
     * @return
     */
    public Address updateParkingLot(UUID uuid, UUID parkinglotIdentifier, @Valid ParkingLotDTO parkingLot);

    /**
     * Remove an {@link ParkingLot} from an {@link Address}.
     * @param addressId
     * @param parkingLot
     * @return
     */
    public Address removeParkingLot(UUID addressId, UUID parkingLot);

    /**
     * Get all {@link ParkingLot} entities of an {@link Address}.
     * @param addressId
     * @return
     */
    public Collection<ParkingLot> getParkinglotsOfAddress(UUID addressId);

    /**
     * Get the {@link ParkingLot} entity of that identifier.
     * @param parkinglotId
     * @return
     */
    public ParkingLot getParkinglot(UUID parkinglotId);
}


