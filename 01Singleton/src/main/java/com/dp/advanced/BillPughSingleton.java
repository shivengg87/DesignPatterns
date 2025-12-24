package com.dp.advanced;

/**
 * BILL PUGH SINGLETON PATTERN (Initialization-on-demand Holder Idiom)
 * ====================================================================
 * <p>
 * RECOMMENDED APPROACH for most use cases!
 * <p>
 * Characteristics:
 * - Uses inner static helper class
 * - Lazy initialization without synchronization
 * - Thread-safe by design (JVM guarantees)
 * - Simple and elegant
 * <p>
 * How It Works:
 * 1. Inner class SingletonHelper is NOT loaded when outer class is loaded
 * 2. Inner class loaded only when getInstance() is called first time
 * 3. JVM guarantees thread-safe class loading
 * 4. No synchronization overhead
 * <p>
 * Why It's the BEST:
 * Thread-safe (JVM guarantees class loading synchronization)
 * Lazy initialization (inner class loaded on-demand)
 * No synchronization overhead (unlike synchronized method)
 * Simple to understand and implement
 * No volatile, no synchronized keywords needed
 * Better performance than double-checked locking
 * <p>
 * Disadvantages:
 * Cannot pass parameters to getInstance() (initialization always same)
 * Doesn't prevent reflection attacks (but can be modified to prevent)
 * <p>
 * JVM Class Loading Guarantee:
 * - Inner class loaded atomically
 * - No thread can see partial initialization
 * - Happens-before guarantee automatically provided
 * <p>
 * Best Used When:
 * - You need lazy initialization
 * - Thread safety is required
 * - You want clean, simple code
 * - No complex initialization parameters needed
 * <p>
 * This is the RECOMMENDED approach by Joshua Bloch (Effective Java)
 */
public class BillPughSingleton {

    /**
     * Private constructor prevents direct instantiation
     * <p>
     * Optional: Add protection against reflection attacks
     */
    private BillPughSingleton() {
        // Prevent reflection attack
        if (SingletonHelper.INSTANCE != null) {
            throw new IllegalStateException("Singleton instance already created!");
        }

        System.out.println(" BillPughSingleton instance being created");
        System.out.println(" Created by thread: " + Thread.currentThread().getName());

        // Simulate initialization work
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Public static method to get instance
     * <p>
     * Execution Flow:
     * 1. First call: SingletonHelper class is loaded → INSTANCE created
     * 2. Subsequent calls: Return already created INSTANCE (fast!)
     * <p>
     * Thread Safety:
     * - JVM ensures SingletonHelper is loaded by only one thread
     * - All other threads wait until loading completes
     * - No need for synchronized, volatile, or locks!
     *
     * @return the singleton instance
     */
    public static BillPughSingleton getInstance() {
        System.out.println(" getInstance() called by: " + Thread.currentThread().getName());
        return SingletonHelper.INSTANCE;
    }

    /**
     * Demonstrates that instance is NOT created at class loading
     */
    public static void demonstrateLazyLoading() {
        System.out.println("\n=== LAZY LOADING DEMONSTRATION ===\n");
        System.out.println(" BillPughSingleton class loaded");
        System.out.println(" Waiting 2 seconds...");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(" 2 seconds passed");
        System.out.println(" Has instance been created yet? NO!");
        System.out.println("\n Now calling getInstance() for the first time:\n");

        BillPughSingleton instance = getInstance();

        System.out.println("\n Instance created! (on-demand)");
        instance.doSomething();
    }

    /**
     * Demonstrates thread safety
     */
    public static void demonstrateThreadSafety() {
        System.out.println("\n=== THREAD SAFETY DEMONSTRATION ===\n");
        System.out.println("Creating 15 threads simultaneously...\n");

        for (int i = 0; i < 15; i++) {
            final int threadNum = i + 1;
            new Thread(() -> {
                BillPughSingleton instance = getInstance();
                System.out.println(" Thread-" + threadNum + " got instance: " + instance.hashCode());
            }, "Thread-" + threadNum).start();
        }
    }

    /**
     * Main demo method
     */
    public static void main(String[] args) throws InterruptedException {
        System.out.println("🚀 Bill Pugh Singleton Pattern Demo\n");

        // Test 1: Lazy loading
        demonstrateLazyLoading();

        Thread.sleep(1000);

        // Test 2: Thread safety
        demonstrateThreadSafety();

        Thread.sleep(2000);


        System.out.println("\n" + "=".repeat(60));
        System.out.println(" KEY TAKEAWAY:");
        System.out.println("Bill Pugh Singleton is the BEST general-purpose");
        System.out.println("singleton implementation in Java!");
        System.out.println("=".repeat(60));
    }

    /**
     * Business method demonstrating singleton functionality
     */
    public void doSomething() {
        System.out.println(" BillPughSingleton is working");
        System.out.println(" Instance hashCode: " + this.hashCode());
    }

    /**
     * INNER STATIC HELPER CLASS
     * ==========================
     * <p>
     * Key Points:
     * 1. This class is NOT loaded when BillPughSingleton is loaded
     * 2. It's loaded ONLY when getInstance() is called for the first time
     * 3. JVM handles thread safety during class loading (no manual synchronization needed)
     * 4. Static final field initialized during class loading (atomic operation)
     * <p>
     * JVM Behavior:
     * - Class loader locks the class during initialization
     * - Only one thread can initialize the class
     * - Other threads wait until initialization completes
     * - Once initialized, all threads see the fully constructed object
     * <p>
     * This is called "Initialization-on-demand holder idiom"
     */
    private static class SingletonHelper {
        /**
         * Static instance created when SingletonHelper class is loaded
         * 'private' - only accessible within SingletonHelper
         * 'static' - belongs to class, not instances
         * 'final' - cannot be reassigned, ensures immutability
         */
        private static final BillPughSingleton INSTANCE = new BillPughSingleton();

        // Static initializer for demonstration
        static {
            System.out.println(" SingletonHelper class is being loaded");
            System.out.println(" This happens ONLY on first getInstance() call");
        }
    }
}