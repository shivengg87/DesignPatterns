package com.designpattern.singleton.basic;

/**
 * THREAD-SAFE SINGLETON PATTERN (Synchronized Method)
 * ====================================================
 * <p>
 * Characteristics:
 * - Instance created on first call (lazy loading)
 * - Thread-safe using synchronized keyword
 * - Prevents multiple threads from creating multiple instances
 * <p>
 * Advantages:
 * Thread-safe - works correctly in multi-threaded environment
 * Lazy initialization - instance created only when needed
 * Simple to understand and implement
 * <p>
 * Disadvantages:
 * Performance overhead - synchronized method is slow
 * Synchronization needed only for first-time initialization, but applied every time
 * Can become bottleneck in high-concurrency applications
 * <p>
 * Performance Impact:
 * - First call: synchronized + instance creation (~100-200x slower)
 * - Subsequent calls: still synchronized (~100x slower than unsynchronized)
 * <p>
 * Best Used When:
 * - Thread safety is required
 * - getInstance() is not called frequently
 * - Performance is not critical
 * <p>
 * Better Alternative: Use Double-Checked Locking or Bill Pugh Singleton
 */
public class ThreadSafeSingleton {

    // Instance variable - initially null
    private static ThreadSafeSingleton instance;

    // Counter to track how many times getInstance() is called
    private static int callCount = 0;

    /**
     * Private constructor prevents external instantiation
     */
    private ThreadSafeSingleton() {
        System.out.println(" ThreadSafeSingleton instance being created by: "
                + Thread.currentThread().getName());

        // Simulate initialization work
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Thread-safe getInstance using synchronized method
     * <p>
     * How it works:
     * 1. synchronized keyword ensures only ONE thread can execute this method at a time
     * 2. Other threads wait (blocked) until the first thread completes
     * 3. Once instance is created, subsequent calls still need to acquire lock (overhead!)
     * <p>
     * Synchronization Lock:
     * - Lock is on the Class object (ThreadSafeSingleton.class)
     * - Only one thread can hold the lock at a time
     * - Other threads are blocked waiting for the lock
     *
     * @return the singleton instance
     */
    public static synchronized ThreadSafeSingleton getInstance() {
        callCount++;

        if (instance == null) {
            System.out.println(" Creating new instance - Thread: "
                    + Thread.currentThread().getName());
            instance = new ThreadSafeSingleton();
        } else {
            System.out.println(" Returning existing instance - Thread: "
                    + Thread.currentThread().getName()
                    + " (Call #" + callCount + ")");
        }

        return instance;
    }

    /**
     * Getter for call count (for demonstration)
     */
    public static int getCallCount() {
        return callCount;
    }

    /**
     * Demo showing thread safety and performance impact
     */
    public static void main(String[] args) throws InterruptedException {
        System.out.println(" Starting Thread-Safe Singleton Demo\n");

        // Test 1: Demonstrate thread safety
        demonstrateThreadSafety();

        // Wait for all threads to complete
        Thread.sleep(2000);

        // Test 2: Show performance impact
        demonstratePerformanceImpact();
    }

    /**
     * Demonstrates that this implementation IS thread-safe
     * All threads will get the SAME instance
     */
    private static void demonstrateThreadSafety() {
        System.out.println("=== TEST 1: THREAD SAFETY ===\n");
        System.out.println("Creating 10 threads simultaneously...\n");

        // Create 10 threads trying to get instance
        for (int i = 0; i < 10; i++) {
            final int threadNum = i + 1;
            new Thread(() -> {
                ThreadSafeSingleton inst = ThreadSafeSingleton.getInstance();
                System.out.println(" Thread-" + threadNum + " got instance: "
                        + inst.hashCode());
            }, "Thread-" + threadNum).start();
        }

        System.out.println("\n All threads should get the SAME hashCode!");
    }

    /**
     * Demonstrates the performance overhead of synchronized method
     */
    private static void demonstratePerformanceImpact() throws InterruptedException {
        System.out.println("\n=== TEST 2: PERFORMANCE IMPACT ===\n");

        // Ensure instance exists
        ThreadSafeSingleton.getInstance();

        System.out.println(" Making 1000 calls to getInstance()...");
        long startTime = System.nanoTime();

        for (int i = 0; i < 1000; i++) {
            ThreadSafeSingleton.getInstance();
        }

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000; // Convert to milliseconds

        System.out.println("️ Time taken: " + duration + " ms");
        System.out.println(" Total getInstance() calls: " + ThreadSafeSingleton.getCallCount());
        System.out.println("\n️ Notice: Every call goes through synchronized method");
        System.out.println("️ Performance Impact: ~100x slower than unsynchronized access");
        System.out.println(" Better alternatives: Double-Checked Locking or Bill Pugh Singleton");
    }

    /**
     * Business method
     */
    public void doSomething() {
        System.out.println(" ThreadSafeSingleton is working - Thread: "
                + Thread.currentThread().getName());
    }
}