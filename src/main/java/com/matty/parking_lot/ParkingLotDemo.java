package com.matty.parking_lot;

public class ParkingLotDemo {

    public static void main(String[] args) {
        ParkingLot parkingLot = ParkingLot.getInstace();
        parkingLot.addLevel(new Level(1, 10));
        parkingLot.addLevel(new Level(2, 5));

        parkingLot.displayAvailability();

        Vehicle car = new Car("TS071117");

        parkingLot.parkVehicle(car);

        parkingLot.displayAvailability();

        parkingLot.unparkVehicle(car);
    }
}
