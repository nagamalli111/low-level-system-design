package com.matty.parking_lot;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private final int floor;
    private final List<ParkingSpot> spotList;

    public Level(int floor, int numberOfSpots) {
        this.floor = floor;
        this.spotList = new ArrayList<>(numberOfSpots);

        double bikeSpots = 0.5;
        double carSpots = 0.4;

        int numberOfBikes = (int) (bikeSpots * numberOfSpots);
        int numberOfCars = (int) (carSpots * numberOfSpots);

        int i;
        for (i = 1; i <= numberOfBikes; i++) {
             ParkingSpot spot = new ParkingSpot(VehicleType.MINI, i);
             spotList.add(spot);
        }
        for (i = numberOfBikes + 1; i <= numberOfBikes + numberOfCars; i++) {
            ParkingSpot spot = new ParkingSpot(VehicleType.COMPACT, i);
            spotList.add(spot);
        }

        for (i = numberOfBikes + numberOfCars + 1; i <= numberOfSpots ; i++) {
            ParkingSpot spot = new ParkingSpot(VehicleType.LARGE, i);
            spotList.add(spot);
        }
    }

    public synchronized boolean parkVehicle(Vehicle vehicle) {
        for (ParkingSpot spot : spotList) {
            if (spot.isAvailable() && spot.getVehicleType() == vehicle.type) {
                spot.parkVehicle(vehicle);
                return true;
            }
        }
        return false;
    }

    public synchronized boolean unparkVehicle(Vehicle vehicle) {
        for (ParkingSpot spot : spotList) {
            if (!spot.isAvailable() && spot.getParkedVehicle().equals(vehicle)) {
                spot.unparkVehicle();
                return true;
            }
        }
        return false;
    }

    public ParkingSpot findAvailableSpot() {
        for (ParkingSpot spot : spotList) {
            if (spot.isAvailable())
                return spot;
        }
        return null;
    }

    public void displayAvailability() {
        System.out.println("Level " + floor + " Availability:");
        for (ParkingSpot spot : spotList) {
            System.out.println("Spot " + spot.getSpotNumber() + ": " + (spot.isAvailable() ? "Available For"  : "Occupied By ")+" "+spot.getVehicleType());
        }
    }
}
