package com.matty.parking_lot;

import java.util.List;

public interface ParkingStrategy {
    ParkingSpot findSpot(List<Level> levels, Vehicle vehicle);
}
