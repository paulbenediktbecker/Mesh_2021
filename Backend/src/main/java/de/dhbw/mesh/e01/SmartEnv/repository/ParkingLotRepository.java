package de.dhbw.mesh.e01.SmartEnv.repository;

import de.dhbw.mesh.e01.SmartEnv.domain.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository to store and manage {@link ParkingLot} objects.
 */
@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {

    public void deleteByIdentifier(UUID uuid);

    public Optional<ParkingLot> findByIdentifier(UUID uuid);
}
