package com.designpattern.singleton.basic;

/**
 * EAGER SINGLETON PATTERN
 * ========================
 * <p>
 * Characteristics:
 * - Instance is created at the time of class loading
 * - Thread-safe by default (JVM guarantees thread safety during class loading)
 * - Simple and straightforward implementation
 * <p>
 * Advantages:
 * - Thread-safe without synchronization
 * - Simple to implement
 * - No performance overhead
 * <p>
 * Disadvantages:
 * -  Instance created even if never used (eager initialization)
 * -  Cannot handle exceptions during instance creation
 * -  No lazy loading
 * <p>
 * Best Used When:
 * - Instance is lightweight
 * - Instance will definitely be used in the application
 * - No complex initialization logic required
 */
public class EagerSingleton {

    // Instance created at class loading time - before any thread can access it
    private static final EagerSingleton INSTANCE = new EagerSingleton();

    /**
     * Private constructor prevents instantiation from outside the class
     * This is the KEY to the Singleton pattern
     */
    private EagerSingleton() {
        System.out.println("EagerSingleton instance created at class loading time");
    }

    /**
     * Public static method to provide global access point to the instance
     * No synchronization needed - instance already created
     *
     * @return the single instance of EagerSingleton
     */
    public static EagerSingleton getInstance() {
        System.out.println("Returning existing EagerSingleton instance");
        return INSTANCE;
    }

    /**
     * Demo method to show eager initialization
     */
    public static void main(String[] args) {
        System.out.println("Application started");
        System.out.println("Note: Instance already created when class was loaded!\n");

        // Getting instance - no creation happening here
        EagerSingleton instance1 = EagerSingleton.getInstance();
        EagerSingleton instance2 = EagerSingleton.getInstance();

        // Verify both references point to the same object
        System.out.println("\n Checking if both references point to same instance:");
        System.out.println("instance1 == instance2: " + (instance1 == instance2));
        System.out.println("instance1 hashCode: " + instance1.hashCode());
        System.out.println("instance2 hashCode: " + instance2.hashCode());

        // Use the singleton
        instance1.doSomething();
    }

    /**
     * Business method - demonstrates that the singleton can have its own functionality
     */
    public void doSomething() {
        System.out.println(" EagerSingleton is doing something...");
    }
}