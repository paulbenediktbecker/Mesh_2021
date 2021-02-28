package de.dhbw.mesh.e01.SmartEnv.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Collection;
import java.util.UUID;

/**
 * ParkingLot entity to be created and saved.
 */
@Data
@Entity
@Validated
public class ParkingLot {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private long id;

    @Column(nullable = false, unique = true)
    @Type(type="uuid-char")
    private UUID identifier;

    @Column(nullable = false)
    private boolean occupied;

    public boolean getOccupied() {
        return this.occupied;
    }

    @Column(nullable = false)
    private LotType type;

    /**
     * Price for of parking at the lot. Charged in intervalls
     */
    @Column(nullable = true)
    @Min(value = 0)
    @Max(value = 1000)
    private double price;

    /**
     * Notes for access, etc.
     */
    @Column(nullable = true)
    private String notes;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] image;

    @ElementCollection
    private Collection<String> restrictions;
}
