package com.dp.composition;

/**
 * Truck class - Composes Engine and Transmission
 */
public class Truck extends Vehicle {
    private double cargoCapacity;

    public Truck(String brand, String model, double cargoCapacity) {
        super(brand, model);
        this.cargoCapacity = cargoCapacity;
    }

    @Override
    public void displayInfo() {
        System.out.println("\n" + "═".repeat(50));
        System.out.println(" TRUCK INFORMATION");
        System.out.println("═".repeat(50));
        System.out.println("Brand: " + brand);
        System.out.println("Model: " + model);
        System.out.println("Cargo Capacity: " + cargoCapacity + " tons");

        if (engine != null) {
            System.out.println("Engine: " + engine.getType() +
                    " (" + engine.getHorsepower() + " HP)");
        }

        if (transmission != null) {
            System.out.println("Transmission: " + transmission.getType());
        }
        System.out.println("═".repeat(50));
    }

    public void loadCargo(double weight) {
        if (weight <= cargoCapacity) {
            System.out.println(" Loading " + weight + " tons of cargo");
        } else {
            System.out.println("  Cargo too heavy! Max capacity: " + cargoCapacity + " tons");
        }
    }
}