package de.dhbw.mesh.e01.SmartEnv.service;

import de.dhbw.mesh.e01.SmartEnv.domain.ParkingLot;
import de.dhbw.mesh.e01.SmartEnv.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RentalServiceImpl implements RentalService {

    @Autowired
    private ParkingService parkingService;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Override
    public boolean toggleOccupiedStatus(UUID parkinglotIdentifier) {
        ParkingLot parkingLot = parkingService.getParkinglot(parkinglotIdentifier);
        boolean status = parkingLot.getOccupied();
        parkingLot.setOccupied(!status);

        parkingLotRepository.save(parkingLot);

        return !status;
    }
}
