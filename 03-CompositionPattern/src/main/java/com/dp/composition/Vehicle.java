package com.dp.composition;

/**
 * Base class for all vehicles
 * Uses Composition over Inheritance principle
 */
public abstract class Vehicle {
    protected String brand;
    protected String model;
    protected Engine engine;          // HAS-A relationship
    protected Transmission transmission; // HAS-A relationship

    public Vehicle(String brand, String model) {
        this.brand = brand;
        this.model = model;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    public void start() {
        System.out.println("\n Starting " + brand + " " + model);
        if (engine != null) {
            engine.start();
        }
        if (transmission != null) {
            transmission.engage();
        }
    }

    public void accelerate() {
        System.out.println(" Accelerating...");
        if (engine != null) {
            engine.increasePower();
        }
    }

    public void stop() {
        System.out.println(" Stopping " + brand + " " + model);
        if (engine != null) {
            engine.stop();
        }
    }

    public abstract void displayInfo();
}
