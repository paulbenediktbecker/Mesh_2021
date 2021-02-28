package com.example.frontend;

import java.util.Collection;
import java.util.UUID;

public class ParkingLot {

    private UUID identifier;

    private LotType type;

    private double price;

    private Collection<String> restrictions;

    private String notes;

    public UUID getIdentifier() {
        return identifier;
    }

    public void setIdentifier(UUID identifier) {
        this.identifier = identifier;
    }

    public LotType getType() {
        return type;
    }

    public void setType(LotType type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Collection<String> getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(Collection<String> restrictions) {
        this.restrictions = restrictions;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "ParkingLot{" +
                "identifier=" + identifier +
                ", type=" + type +
                ", price=" + price +
                ", restrictions=" + restrictions +
                ", notes='" + notes + '\'' +
                '}';
    }
}
