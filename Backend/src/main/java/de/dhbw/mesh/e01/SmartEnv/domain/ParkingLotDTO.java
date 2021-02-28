package de.dhbw.mesh.e01.SmartEnv.domain;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Collection;

/**
 * DTO entity for the {@link ParkingLot}
 */
@Data
@Validated
public class ParkingLotDTO {

    private LotType type;

    @Min(value = 0)
    @Max(value = 1000)
    private double price;

    private String notes;

    private Collection<String> restrictions;

    private byte[] image;
}
