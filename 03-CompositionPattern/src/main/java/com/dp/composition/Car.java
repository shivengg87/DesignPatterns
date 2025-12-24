package com.dp.composition;

/**
 * Car class - Composes Engine and Transmission
 */
public class Car extends Vehicle {
    private int numberOfDoors;

    public Car(String brand, String model, int numberOfDoors) {
        super(brand, model);
        this.numberOfDoors = numberOfDoors;
    }

    @Override
    public void displayInfo() {
        System.out.println("\n" + "═".repeat(50));
        System.out.println(" CAR INFORMATION");
        System.out.println("═".repeat(50));
        System.out.println("Brand: " + brand);
        System.out.println("Model: " + model);
        System.out.println("Doors: " + numberOfDoors);

        if (engine != null) {
            System.out.println("Engine: " + engine.getType() +
                    " (" + engine.getHorsepower() + " HP)");
        }

        if (transmission != null) {
            System.out.println("Transmission: " + transmission.getType());
        }
        System.out.println("═".repeat(50));
    }

    public void openTrunk() {
        System.out.println("📦 Trunk opened");
    }
}
