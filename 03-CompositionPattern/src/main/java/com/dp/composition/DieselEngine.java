package com.dp.composition;

/**
 * Concrete implementation of Engine - Diesel Engine
 */
public class DieselEngine implements Engine {
    private int horsepower;
    private int torque;

    public DieselEngine(int horsepower, int torque) {
        this.horsepower = horsepower;
        this.torque = torque;
    }

    @Override
    public void start() {
        System.out.println("    Diesel Engine starting...");
        System.out.println("  High torque: " + torque + " Nm");
    }

    @Override
    public void stop() {
        System.out.println("  ️  Diesel Engine stopped");
    }

    @Override
    public void increasePower() {
        System.out.println("   Diesel Engine: Power surge! " + horsepower + " HP, " + torque + " Nm torque");
    }

    @Override
    public String getType() {
        return "Diesel";
    }

    @Override
    public int getHorsepower() {
        return horsepower;
    }

    public int getTorque() {
        return torque;
    }
}