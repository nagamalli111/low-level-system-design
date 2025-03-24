package com.matty.parking_lot;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    private static ParkingLot instace;
    private List<Level> levels;
    private ParkingStrategy parkingStrategy;

    private ParkingLot() {
        levels = new ArrayList<>();
        parkingStrategy = new NearestFistStrategy();
    }
    public static ParkingLot getInstace() {
        if (instace == null) {
            instace = new ParkingLot();
        }
        return instace;
    }

    public void setParkingStrategy(ParkingStrategy parkingStrategy) {
        this.parkingStrategy = parkingStrategy;
    }

    public void addLevel(Level level) {
        this.levels.add(level);
    }

    public void parkVehicle(Vehicle vehicle) {
        ParkingSpot spot = parkingStrategy.findSpot(levels, vehicle);
        if (spot != null) {
            spot.parkVehicle(vehicle);
            return;
        }
        System.out.println("Could not park vehicle.");
    }

    public void unparkVehicle(Vehicle vehicle) {
        for (Level level : levels) {
            if (level.unparkVehicle(vehicle)) {
                return;
            }
        }
    }

    public void displayAvailability() {
        for (Level level : levels) {
            level.displayAvailability();
        }
    }


}
