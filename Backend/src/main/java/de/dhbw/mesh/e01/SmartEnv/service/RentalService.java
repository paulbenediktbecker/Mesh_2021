package de.dhbw.mesh.e01.SmartEnv.service;

import java.util.UUID;

/**
 * Rental serivce interface.
 */
public interface RentalService {

    /**
     * Toggle occupied status of {@link de.dhbw.mesh.e01.SmartEnv.domain.ParkingLot}.
     * @param parkinglotIdentifier
     * @return
     */
    public boolean toggleOccupiedStatus(UUID parkinglotIdentifier);
}
