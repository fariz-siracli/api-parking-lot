package com.fs.api.parking.lot.model.enums;

public enum SlotType {
    XS("Motorbike"),
    SMALL("Handicapped"),
    MEDIUM("Car"),
    LARGE("Truck"),
    XL("Bus");

    private String name;

    SlotType(String name) {
        this.name = name;
    }
}