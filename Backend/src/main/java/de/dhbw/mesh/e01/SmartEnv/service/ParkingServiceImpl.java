package de.dhbw.mesh.e01.SmartEnv.service;

import de.dhbw.mesh.e01.SmartEnv.domain.Address;
import de.dhbw.mesh.e01.SmartEnv.domain.ParkingLot;
import de.dhbw.mesh.e01.SmartEnv.domain.ParkingLotDTO;
import de.dhbw.mesh.e01.SmartEnv.repository.AddressRepository;
import de.dhbw.mesh.e01.SmartEnv.repository.ParkingLotRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ParkingServiceImpl implements ParkingService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Override
    public Address createAddress(String city, String street, String zipcode, String geotag) {

        Address address = new Address();
        address.setIdentifier(UUID.randomUUID());
        address.setZipcode(zipcode);
        address.setCity(city);
        address.setStreet(street);
        address.setGeotag(geotag);
        address.setParkingLots(new ArrayList<>());

        addressRepository.save(address);

        log.debug("Stored address {}", address);

        return address;
    }

    @Override
    public Address updateAddress(UUID uuid, String city, String street, String zipcode, String geotag) {
        Address address = getAddress(uuid);
        address.setCity(city);
        address.setStreet(street);
        address.setZipcode(zipcode);
        address.setGeotag(geotag);

        addressRepository.save(address);

        log.debug("Updated address {}", address);

        return address;
    }

    @Override
    public void deleteAddress(UUID uuid) {
        //get the parking lots of this address
        Collection<ParkingLot> parkingLots = getAddress(uuid).getParkingLots();

        //remove all parkinglots of the address
        parkingLots.forEach(parkingLot -> parkingLotRepository.delete(parkingLot));

        //remove the address
        addressRepository.deleteByIdentifier(uuid);

        log.debug("Deleted address with identifier {}", uuid);
    }

    @Override
    public Address getAddress(UUID uuid) {
        log.debug("Returning address for identifier {}", uuid);
        return addressRepository.findByIdentifier(uuid)
                .orElseThrow(() -> new IllegalArgumentException("No address found for identifier " + uuid));
    }

    @Override
    public Collection<Address> getAllAddress() {
        log.debug("Returning all addresses");
        return addressRepository.findAll();
    }

    @Override
    public Collection<Address> getAddressesCity(String city) {
        log.debug("Returning all addresses matching city = {}", city);
        return addressRepository.findAllByCityContaining(city);
    }

    @Override
    public Collection<Address> getAddressesCityStreet(String city, String street) {
        log.debug("Returning all addresses matching city = {} and street = {}", city, street);
        return addressRepository.findAllByCityContainingAndStreetContaining(city, street);
    }

    @Override
    public Collection<Address> getAddressesZipcode(String zipcode) {
        log.debug("Returning all addresses matching zipcode = {}", zipcode);
        return addressRepository.findByZipcodeContaining(zipcode);
    }

    @Override
    public Collection<Address> getAddressesZipcodeStreet(String zipcode, String street) {
        log.debug("Returning all addresses matching zipcode = {} and street = {}", zipcode, street);
        return addressRepository.findByZipcodeContainingAndStreetContaining(zipcode, street);
    }

    @Override
    public Collection<Address> getAddressesZipcodeStreetCity(String zipcode, String street, String city) {
        log.debug("Returning all addresses matching zipcode = {} and street = {} and city = {}", zipcode, street, city);
        return addressRepository.findByZipcodeContainingAndStreetContainingAndCityContaining(zipcode, city, street);
    }

    @Override
    public Address addParkingLot(UUID uuid, @Valid ParkingLotDTO parkingLotDTO) {
        Address address = getAddress(uuid);

        if(address.getParkingLots().size() == 3)
            throw new IllegalArgumentException("Address cannot have more than 3 address");

        //Link parkinglot to address
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setIdentifier(UUID.randomUUID());
        parkingLot.setPrice(parkingLotDTO.getPrice());
        parkingLot.setType(parkingLotDTO.getType());
        parkingLot.setOccupied(false);

        //If notes are given
        if(parkingLotDTO.getNotes() != null)
            parkingLot.setNotes(parkingLotDTO.getNotes());

        //if images is given
        if(parkingLotDTO.getImage() != null)
            parkingLot.setImage(parkingLotDTO.getImage());

        //If restrictions are given
        if(parkingLotDTO.getRestrictions() != null)
            parkingLot.setRestrictions(parkingLotDTO.getRestrictions());

        //Link address to parking lot and save parkinglot
        parkingLotRepository.save(parkingLot);

        //Add parking lot
        address.getParkingLots().add(parkingLot);

        log.debug("Added parkinglot with identifier {} to address with identifier {}", parkingLot.getIdentifier(), address.getIdentifier());

        //save address
        return addressRepository.save(address);
    }

    @Override
    public Address updateParkingLot(UUID uuid, UUID parkinglotIdentifier, @Valid ParkingLotDTO parkingLot) {
        ParkingLot parkingLot1 = getParkinglot(parkinglotIdentifier);
        parkingLot1.setPrice(parkingLot.getPrice());
        parkingLot1.setType(parkingLot.getType());

        if(parkingLot.getNotes() != null)
            parkingLot1.setNotes(parkingLot.getNotes());

        if(parkingLot.getRestrictions() != null)
            parkingLot1.setRestrictions(parkingLot.getRestrictions());

        if(parkingLot.getImage() != null)
            parkingLot1.setImage(parkingLot.getImage());

        parkingLotRepository.save(parkingLot1);

        log.debug("Updated parkinglot with identifier {} of address {}", parkingLot1.getIdentifier(), uuid);

        return getAddress(uuid);
    }

    @Override
    public Address removeParkingLot(UUID addressId, UUID parkingLotId) {
        Address address = getAddress(addressId);

        List<ParkingLot> parkingLotsToRemove = address.getParkingLots().stream()
                .filter(parkingLot -> parkingLot.getIdentifier().equals(parkingLotId))
                .collect(Collectors.toList());

        if(parkingLotsToRemove.size() != 1)
            throw new IllegalStateException(String.format("Invalid amount of parkinglots with identifier %s : size is %s", parkingLotId, parkingLotsToRemove.size()));

        parkingLotsToRemove
                .forEach(parkingLot -> address.getParkingLots().remove(parkingLot));

        //remove the parkinglot from the parkinglot repository
        parkingLotRepository.deleteByIdentifier(parkingLotId);

        //save the new address
        addressRepository.save(address);

        log.debug("Removed parkinglot {} from address {}", parkingLotId, addressId);

        return address;
    }

    @Override
    public Collection<ParkingLot> getParkinglotsOfAddress(UUID addressId) {
        log.debug("Returning all parkinglots of address {}", addressId);
        Address address = getAddress(addressId);
        return address.getParkingLots();
    }

    @Override
    public ParkingLot getParkinglot(UUID parkinglotId) {
        log.debug("Returning parkinglot with identifier {}", parkinglotId);
        return parkingLotRepository.findByIdentifier(parkinglotId)
                .orElseThrow(() -> new IllegalArgumentException("Found no parkinglot with identifier " + parkinglotId));
    }
}
