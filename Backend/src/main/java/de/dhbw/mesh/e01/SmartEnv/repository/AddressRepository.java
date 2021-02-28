package de.dhbw.mesh.e01.SmartEnv.repository;

import de.dhbw.mesh.e01.SmartEnv.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    public Optional<Address> findByIdentifier(UUID uuid);

    public Collection<Address> findAllByCityContaining(String city);

    public Collection<Address> findAllByCityContainingAndStreetContaining(String city, String street); //contains street

    public Collection<Address> findByZipcodeContaining(String zipcode);

    public Collection<Address> findByZipcodeContainingAndStreetContaining(String zipcode, String street); //contains street

    public Collection<Address> findByZipcodeContainingAndStreetContainingAndCityContaining(String zipcode, String city, String street); //contains street

    public void deleteByIdentifier(UUID uuid);
}
