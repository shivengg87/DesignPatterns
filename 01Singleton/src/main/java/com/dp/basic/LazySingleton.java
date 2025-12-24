package com.designpattern.singleton.basic;

/**
 * LAZY SINGLETON PATTERN
 * =======================
 * <p>
 * Characteristics:
 * - Instance is created only when getInstance() is called for the first time
 * - Delayed initialization (lazy loading)
 * - NOT thread-safe in this basic implementation
 * <p>
 * Advantages:
 * Saves memory if instance is never used
 * Simple implementation
 * <p>
 * Disadvantages:
 * NOT thread-safe - multiple threads can create multiple instances
 * Not suitable for multi-threaded applications
 * Race condition possible
 * <p>
 * Best Used When:
 * - Single-threaded applications only
 * - Learning/demonstration purposes
 * - Quick prototypes
 * <p>
 * WARNING: Do NOT use in production multi-threaded applications!
 */
public class LazySingleton {

    // Instance is null initially - will be created on first call to getInstance()
    private static LazySingleton instance;

    /**
     * Private constructor prevents instantiation from outside
     */
    private LazySingleton() {
        System.out.println(" LazySingleton instance being created...");

        // Simulate some initialization work
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the singleton instance, creating it if necessary
     * <p>
     * THREAD SAFETY ISSUE:
     * If two threads call this method simultaneously when instance is null,
     * both might create separate instances!
     * <p>
     * Thread A: checks (instance == null) → TRUE
     * Thread B: checks (instance == null) → TRUE (before A creates instance)
     * Thread A: creates new instance
     * Thread B: creates new instance (VIOLATION!)
     *
     * @return the singleton instance
     */
    public static LazySingleton getInstance() {
        if (instance == null) {
            System.out.println(" Creating new LazySingleton instance");
            instance = new LazySingleton();
        } else {
            System.out.println(" Returning existing LazySingleton instance");
        }
        return instance;
    }

    /**
     * Demo showing the lazy initialization
     */
    public static void main(String[] args) {
        System.out.println(" Application started");
        System.out.println(" Note: Instance NOT created yet\n");

        System.out.println(" Sleeping for 2 seconds...\n");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Instance will be created NOW (on first call)
        System.out.println(" First call to getInstance():");
        LazySingleton instance1 = LazySingleton.getInstance();

        System.out.println("\n Second call to getInstance():");
        LazySingleton instance2 = LazySingleton.getInstance();

        // Verify both references point to the same object
        System.out.println("\n Verifying singleton:");
        System.out.println("instance1 == instance2: " + (instance1 == instance2));

        // Demonstrate the thread safety issue
        demonstrateThreadSafetyIssue();
    }

    /**
     * Demonstrates why this implementation is NOT thread-safe
     * Run this multiple times - sometimes you'll see multiple instances created!
     */
    private static void demonstrateThreadSafetyIssue() {
        System.out.println("\n DEMONSTRATING THREAD SAFETY ISSUE:");
        System.out.println("Creating 10 threads simultaneously...\n");

        // Reset instance for demonstration
        instance = null;

        // Create 10 threads that all try to get instance at the same time
        for (int i = 0; i < 10; i++) {
            final int threadNum = i + 1;
            new Thread(() -> {
                LazySingleton inst = LazySingleton.getInstance();
                System.out.println("Thread-" + threadNum + " got instance: " + inst.hashCode());
            }).start();
        }

        System.out.println("\n Check the hashCodes above - you might see DIFFERENT values!");
        System.out.println(" This means multiple instances were created - Singleton VIOLATED!");
    }

    /**
     * Business method
     */
    public void doSomething() {
        System.out.println(" LazySingleton is doing something...");
    }
}