package com.dp.composition;

/**
 * Manual Transmission implementation
 */
public class ManualTransmission implements Transmission {
    private int gears;
    private int currentGear;

    public ManualTransmission(int gears) {
        this.gears = gears;
        this.currentGear = 0;
    }

    @Override
    public void engage() {
        System.out.println("   Manual Transmission engaged - " + gears + " gears");
        System.out.println("   Driver control mode");
    }

    @Override
    public void shiftGear(int gear) {
        if (gear >= 0 && gear <= gears) {
            currentGear = gear;
            System.out.println("    Shifted to gear " + currentGear);
        }
    }

    @Override
    public String getType() {
        return "Manual";
    }
}