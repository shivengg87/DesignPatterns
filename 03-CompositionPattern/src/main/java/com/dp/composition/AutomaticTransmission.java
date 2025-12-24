package com.dp.composition;

/**
 * Automatic Transmission implementation
 */
public class AutomaticTransmission implements Transmission {
    private String mode;

    public AutomaticTransmission(String mode) {
        this.mode = mode; // "Sport", "Eco", "Comfort"
    }

    @Override
    public void engage() {
        System.out.println("   Automatic Transmission engaged");
        System.out.println("   Mode: " + mode);
    }

    @Override
    public void shiftGear(int gear) {
        System.out.println("    Auto-shifting smoothly (gear " + gear + ")");
    }

    @Override
    public String getType() {
        return "Automatic";
    }

    public void changeMode(String newMode) {
        this.mode = newMode;
        System.out.println("   Transmission mode changed to: " + mode);
    }
}