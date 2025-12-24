package com.dp.advanced;

/**
 * Enum-based Singleton implementation
 * <p>
 * JVM guarantees:
 * - Single instance
 * - Thread safety
 * - Serialization safety
 * - Protection against reflection
 */
public enum EnumSingleton {

    // The single instance (created automatically by JVM)
    INSTANCE;

    // Example field (state)
    private int counter;

    /**
     * Enum constructor
     * Called ONLY ONCE by JVM when enum is loaded
     */
    EnumSingleton() {
        System.out.println(" EnumSingleton constructor called");
        counter = 0;
    }

    /**
     * Business method
     */
    public void increment() {
        counter++;
        System.out.println("Counter value: " + counter);
    }

    /**
     * Getter method
     */
    public int getCounter() {
        return counter;
    }

    /**
     * Another example method
     */
    public void doSomething() {
        System.out.println("️ EnumSingleton is doing work...");
    }


}
