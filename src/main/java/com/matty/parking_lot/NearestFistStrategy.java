package com.matty.parking_lot;

import java.util.List;

public class NearestFistStrategy implements ParkingStrategy {
    @Override
    public ParkingSpot findSpot(List<Level> levels, Vehicle vehicle) {
        for (Level level : levels) {
            if (level.findAvailableSpot() != null)
                return level.findAvailableSpot();
        }
        return null;
    }
}
