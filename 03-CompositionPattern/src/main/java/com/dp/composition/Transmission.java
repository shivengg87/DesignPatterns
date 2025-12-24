package com.dp.composition;

/**
 * Transmission interface - Another component for composition
 */
public interface Transmission {
    void engage();
    void shiftGear(int gear);
    String getType();
}