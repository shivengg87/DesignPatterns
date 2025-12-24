package com.dp.composition;

/**
 * Motorcycle class - Composes Engine and Transmission
 */
public class Motorcycle extends Vehicle {
    private String bikeType;

    public Motorcycle(String brand, String model, String bikeType) {
        super(brand, model);
        this.bikeType = bikeType;
    }

    @Override
    public void displayInfo() {
        System.out.println("\n" + "═".repeat(50));
        System.out.println("️  MOTORCYCLE INFORMATION");
        System.out.println("═".repeat(50));
        System.out.println("Brand: " + brand);
        System.out.println("Model: " + model);
        System.out.println("Type: " + bikeType);

        if (engine != null) {
            System.out.println("Engine: " + engine.getType() +
                    " (" + engine.getHorsepower() + " HP)");
        }

        if (transmission != null) {
            System.out.println("Transmission: " + transmission.getType());
        }
        System.out.println("═".repeat(50));
    }

    public void wheelie() {
        System.out.println(" Performing a wheelie!");
    }
}
