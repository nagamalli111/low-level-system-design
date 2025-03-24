package com.matty.parking_lot;

import java.util.List;

public class FartherstFistStrategy implements ParkingStrategy {
    @Override
    public ParkingSpot findSpot(List<Level> levels, Vehicle vehicle) {
        for (int i = levels.size() - 1; i >= 0; i--) {
            if (levels.get(i).findAvailableSpot() != null)
                return levels.get(i).findAvailableSpot();
        }
        return null;
    }
}
