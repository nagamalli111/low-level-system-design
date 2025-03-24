package com.matty.parking_lot;


public class ParkingSpot {
    private final VehicleType vehicleType;
    private final int spotNumber;
    private Vehicle parkedVehicle;

    public ParkingSpot(VehicleType vehicleType, int spotNumber) {
        this.vehicleType = vehicleType;
        this.spotNumber = spotNumber;
    }

    public boolean isAvailable() {
        return parkedVehicle == null;
    }

    public synchronized void parkVehicle(Vehicle vehicle) {
        if (isAvailable() && vehicle.type == vehicleType) {
            parkedVehicle = vehicle;
        } else
            throw new IllegalArgumentException("Invalid vehicle type or spot already occupied.");
    }

    public synchronized void unparkVehicle() {
        parkedVehicle = null;
    }
    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public Vehicle getParkedVehicle() {
        return parkedVehicle;
    }

    public int getSpotNumber() {
        return spotNumber;
    }


}
