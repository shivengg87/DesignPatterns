package com.dp.composition;

/**
 * Concrete implementation of Engine - Electric Engine
 */
public class ElectricEngine implements Engine {
    private int horsepower;
    private int batteryCapacity;

    public ElectricEngine(int horsepower, int batteryCapacity) {
        this.horsepower = horsepower;
        this.batteryCapacity = batteryCapacity;
    }

    @Override
    public void start() {
        System.out.println("   Electric Engine starting...");
        System.out.println("   Battery: " + batteryCapacity + " kWh - Silent power!");
    }

    @Override
    public void stop() {
        System.out.println("   Electric Engine stopped - Regenerative braking active");
    }

    @Override
    public void increasePower() {
        System.out.println("   Electric Motor: Instant torque! " + horsepower + " HP");
    }

    @Override
    public String getType() {
        return "Electric";
    }

    @Override
    public int getHorsepower() {
        return horsepower;
    }

    public int getBatteryCapacity() {
        return batteryCapacity;
    }
}