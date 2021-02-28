package de.dhbw.mesh.e01.SmartEnv.controller;

import de.dhbw.mesh.e01.SmartEnv.domain.Address;
import de.dhbw.mesh.e01.SmartEnv.domain.ParkingLot;
import de.dhbw.mesh.e01.SmartEnv.domain.ParkingLotDTO;
import de.dhbw.mesh.e01.SmartEnv.service.ParkingService;
import de.dhbw.mesh.e01.SmartEnv.service.RentalService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * REST Controller for the parkingservice
 */
@RestController
@RequestMapping("/parking/address")
public class ParkingController {

    @Autowired
    private ParkingService parkingService;

    @Autowired
    private RentalService rentalService;

    /**
     * Create a new {@link Address} without any {@link ParkingLot}.
     * @param zipcode the zipcode of the address
     * @param city the city of the address
     * @param street the street of the address
     * @param geotag the geotag of the address. Format: "12.00213;14.22423"
     * @return the stored {@link Address} entitiy
     */
    @Transactional
    @PostMapping()
    public Address createAddress(
            @Parameter(description = "Zipcode of the address. E.g. 72663") @RequestParam String zipcode,
            @Parameter(description = "City of the address. E.g. grossbettlingen") @RequestParam String city,
            @Parameter(description = "Street and number of the address. E.g. albstrasse 50") @RequestParam String street,
            @Parameter(description = "Geotag of the address. E.g. 12.00213;14.22423") @RequestParam String geotag) {
        return parkingService.createAddress(city, street, zipcode, geotag);
    }

    @Transactional
    @PutMapping("/{addressIdentifier}/update")
    public Address udpateAddress(
            @Parameter(description = "Identifier of the address entity") @RequestParam String addressIdentifier,
            @Parameter(description = "Zipcode of the address. E.g. 72663") @RequestParam String zipcode,
            @Parameter(description = "City of the address. E.g. grossbettlingen") @RequestParam String city,
            @Parameter(description = "Street and number of the address. E.g. albstrasse 50") @RequestParam String street,
            @Parameter(description = "Geotag of the address. E.g. 12.00213;14.22423") @RequestParam String geotag) {
        return parkingService.updateAddress(UUID.fromString(addressIdentifier), city, street, zipcode, geotag);
    }

    /**
     * Delete an existing {@link Address} entity.
     * @param addressIdentifier the {@link UUID} of the {@link Address}
     */
    @Transactional
    @DeleteMapping("/{addressIdentifier}/delete")
    public void deleteAddress(
            @Parameter(description = "Identifier of the address entity") @RequestParam String addressIdentifier) {
        parkingService.deleteAddress(UUID.fromString(addressIdentifier));
    }

    /**
     * Return all saved {@link Address} entities.
     * @return an {@link Collection} of all {@link Address}es or an empty {@link Collection}
     */
    @GetMapping
    public Collection<Address> getAll() {
        return parkingService.getAllAddress();
    }

    /**
     * Get the specific {@link Address} entity for the given {@link UUID}.
     * @param addressIdentifier the {@link UUID} of the {@link Address} entity
     * @return the {@link Address} entity of the {@link UUID}
     */
    @GetMapping("/{addressIdentifier}")
    public Address getAddress(
            @Parameter(description = "Identifier of the address entity") @PathVariable String addressIdentifier) {
        return parkingService.getAddress(UUID.fromString(addressIdentifier));
    }

    /**
     * Get an {@link Collection} of {@link Address}es that contain the zipcode.
     * @param zipcode to filter
     * @return an {@link Collection} of {@link Address} or an empty {@link Collection}
     */
    @GetMapping("/zipcode/{zipcode}")
    public Collection<Address> getAddressZipcode(
            @Parameter(description = "Zipcode to filter") @PathVariable String zipcode) {
        return parkingService.getAddressesZipcode(zipcode);
    }

    /**
     * Get an {@link Collection} of {@link Address}es that contain the zipcode and street.
     * @param zipcode to filter
     * @param street to filter
     * @return an {@link Collection} of {@link Address} or an empty {@link Collection}
     */
    @GetMapping("/zipcode/{zipcode}/street/{street}")
    public Collection<Address> getAddressZipcodeStreet(
            @Parameter(description = "Zipcode to filter") @PathVariable String zipcode,
            @Parameter(description = "Street to filter") @PathVariable String street) {
        return parkingService.getAddressesZipcodeStreet(zipcode, street);
    }

    /**
     * Get an {@link Collection} of {@link Address}es that contain the city.
     * @param city to filter
     * @return an {@link Collection} of {@link Address} or an empty {@link Collection}
     */
    @GetMapping("/city/{city}")
    public Collection<Address> getAddressCity(
            @Parameter(description = "City to filter") @PathVariable String city) {
            return parkingService.getAddressesCity(city);
    }

    /**
     * Get an {@link Collection} of {@link Address}es that contain the city and street.
     * @param city to filter
     * @param street to filter
     * @return an {@link Collection} of {@link Address} or an empty {@link Collection}
     */
    @GetMapping("/city/{city}/street/{street}")
    public Collection<Address> getAddressCityStreet(
            @Parameter(description = "City to filter") @PathVariable String city,
            @Parameter(description = "Street to filter") @PathVariable String street) {
        return parkingService.getAddressesCityStreet(city, street);
    }

    /**
     * Get all {@link ParkingLot} of the given {@link Address}es.
     * @param addressIdentifier the {@link UUID} of the {@link Address} to match
     * @return an {@link Collection} of {@link ParkingLot}s or an emtpy {@link Collection}
     */
    @GetMapping("/{addressIdentifier}/parking")
    public Collection<ParkingLot> getParkinglots(
            @Parameter(description = "Identifier of the address entity") @PathVariable String addressIdentifier) {
        return parkingService.getParkinglotsOfAddress(UUID.fromString(addressIdentifier));
    }

    /**
     * Add an {@link ParkingLot} entity to an {@link Address} entity.
     * @param addressIdentifier the {@link UUID} of the {@link Address} to add the {@link ParkingLot}
     * @param parkingLot the {@link ParkingLot} entity
     * @return the {@link Address} with its attached {@link ParkingLot}s
     */
    @Transactional
    @PostMapping("/{addressIdentifier}/parking/add")
    public Address addParkinglot(
            @Parameter(description = "Identifier of the address entity") @PathVariable String addressIdentifier,
            @Parameter(description = "ParkingLotDTO entity as JSON") @RequestBody ParkingLotDTO parkingLot) {
        return parkingService.addParkingLot(UUID.fromString(addressIdentifier), parkingLot);
    }

    @Transactional
    @PutMapping("/{addressIdentifier}/parking/{parkinglotIdentifier}/update")
    public Address updateParkinglot(
            @Parameter(description = "Identifier of the address entity") @PathVariable String addressIdentifier,
            @Parameter(description = "Identifier of the parkinglot entity") @PathVariable String parkinglotIdentifier,
            @Valid @Parameter(description = "ParkingLotDTO entity to be updated as JSON") @RequestBody ParkingLotDTO parkingLotDTO) {
        return parkingService.updateParkingLot(UUID.fromString(addressIdentifier), UUID.fromString(parkinglotIdentifier), parkingLotDTO);
    }

    /**
     * Delete an {@link ParkingLot} from an {@link Address}.
     * @param addressIdentifier the {@link UUID} of the {@link Address} that contains the {@link ParkingLot}
     * @param parkinglotIdentifier the {@link UUID} of the {@link ParkingLot} to remove
     * @return the new {@link Address} without the deleted {@link ParkingLot}
     */
    @Transactional
    @DeleteMapping("/{addressIdentifier}/parking/{parkinglotIdentifier}/delete")
    public Address removeParkinglot(
            @Parameter(description = "Identifier of the address entity") @PathVariable String addressIdentifier,
            @Parameter(description = "Identifier of the parkinglot entity") @PathVariable String parkinglotIdentifier) {
        return parkingService.removeParkingLot(UUID.fromString(addressIdentifier), UUID.fromString(parkinglotIdentifier));
    }

    @Transactional
    @PostMapping("/{addressIdentifier}/parking/{parkinglotIdentifier}/toggle-status")
    public boolean toggleParkingSlot(
            @Parameter(description = "Identifier of the address entity") @PathVariable String addressIdentifier,
            @Parameter(description = "Identifier of the parkinglot entity") @PathVariable String parkinglotIdentifier) {
        return rentalService.toggleOccupiedStatus(UUID.fromString(parkinglotIdentifier));
    }
}
