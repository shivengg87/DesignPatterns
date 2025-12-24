package com.dp.composition;

/**
 * Concrete implementation of Engine - Petrol Engine
 */
public class PetrolEngine implements Engine {
    private int horsepower;
    private double displacement;

    public PetrolEngine(int horsepower, double displacement) {
        this.horsepower = horsepower;
        this.displacement = displacement;
    }

    @Override
    public void start() {
        System.out.println("    Petrol Engine starting...");
        System.out.println("  " + displacement + "L engine roaring to life!");
    }

    @Override
    public void stop() {
        System.out.println("    Petrol Engine stopped");
    }

    @Override
    public void increasePower() {
        System.out.println("  Petrol Engine: Increasing RPM... " + horsepower + " HP");
    }

    @Override
    public String getType() {
        return "Petrol";
    }

    @Override
    public int getHorsepower() {
        return horsepower;
    }
}