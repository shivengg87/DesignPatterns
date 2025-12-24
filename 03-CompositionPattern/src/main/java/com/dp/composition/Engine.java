package com.dp.composition;

/**
 * Engine interface - Component that can be composed into vehicles
 */
public interface Engine {
    void start();
    void stop();
    void increasePower();
    String getType();
    int getHorsepower();
}
