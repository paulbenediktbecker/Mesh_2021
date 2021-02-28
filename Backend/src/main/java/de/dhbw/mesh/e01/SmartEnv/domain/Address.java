package de.dhbw.mesh.e01.SmartEnv.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

/**
 * Address entity. Each {@link ParkingLot} has exactly one address.
 */
@Data
@Entity
@Validated
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private long id;

    @Column(nullable = false, unique = true)
    @Type(type="uuid-char")
    private UUID identifier;

    @NotBlank(message = "Zipcode must not be blank")
    @Column(nullable = false)
    private String zipcode;

    @NotBlank(message = "Street must not be blank")
    @Column(nullable = false)
    private String street;

    @NotBlank(message = "City must not be blank")
    @Column(nullable = false)
    private String city;

    @NotBlank
    private String geotag;

    @OneToMany
    private List<ParkingLot> parkingLots;
}
