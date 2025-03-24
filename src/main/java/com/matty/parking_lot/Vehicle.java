package com.matty.parking_lot;

public abstract class Vehicle {
    protected String vehicleNumber;
    protected VehicleType type;

    public Vehicle(String vehicleNumber, VehicleType type) {
        this.vehicleNumber = vehicleNumber;
        this.type = type;
    }

    public VehicleType getType() {
        return this.type;
    }
}
