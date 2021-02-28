package de.dhbw.mesh.e01.SmartEnv.service;

import java.util.UUID;

/**
 * Rental serivce interface.
 */
public interface RentalService {

    public boolean toggleOccupiedStatus(UUID parkinglotIdentifier);
}
