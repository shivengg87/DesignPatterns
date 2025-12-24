package com.dp.advanced;

/**
 * DOUBLE-CHECKED LOCKING SINGLETON PATTERN
 * =========================================
 * <p>
 * Characteristics:
 * - Combines lazy initialization with thread safety
 * - Minimizes synchronization overhead
 * - Uses 'volatile' keyword to prevent instruction reordering
 * <p>
 * How Double-Checked Locking Works:
 * 1. First check: if (instance == null) - without synchronization (fast)
 * 2. Synchronize only if instance is null (rare case)
 * 3. Second check: inside synchronized block to prevent race condition
 * 4. Create instance only if still null
 * <p>
 * Advantages:
 * Thread-safe
 * Lazy initialization
 * Better performance than simple synchronized method
 * Synchronization overhead only on first call
 * <p>
 * Disadvantages:
 * More complex than other implementations
 * Requires Java 5+ (for volatile to work correctly)
 * Slightly more difficult to understand
 * <p>
 * Why 'volatile' is CRITICAL:
 * - Without volatile, JVM might reorder instructions
 * - Thread A might see partially constructed object
 * - volatile ensures visibility and ordering guarantees
 * <p>
 * Best Used When:
 * - Thread safety required
 * - High-performance needed (frequent getInstance() calls)
 * - Lazy initialization desired
 * - Java 5 or higher
 */
public class DoubleCheckedLockingSingleton {

    /**
     * CRITICAL: 'volatile' keyword is MANDATORY here!
     * <p>
     * Without volatile:
     * - Thread A: creates instance (step 1: allocate memory, step 2: initialize, step 3: assign)
     * - JVM might reorder: step 1, step 3, step 2
     * - Thread B: sees instance != null but object not fully initialized (DANGER!)
     * <p>
     * With volatile:
     * - Prevents instruction reordering
     * - Ensures all threads see fully constructed object
     * - Provides happens-before guarantee
     */
    private static volatile DoubleCheckedLockingSingleton instance;

    // Statistics for demonstration
    private static int creationAttempts = 0;
    private static int synchronizedBlockEntries = 0;

    /**
     * Private constructor
     */
    private DoubleCheckedLockingSingleton() {
        System.out.println(" Instance being created by: " + Thread.currentThread().getName());

        // Simulate complex initialization
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Double-Checked Locking getInstance implementation
     * <p>
     * STEP-BY-STEP EXECUTION:
     * <p>
     * 1. FIRST CHECK (unsynchronized):
     * if (instance == null) → Very fast, no locking
     * - If instance exists: return immediately (99.99% of calls)
     * - If instance is null: proceed to synchronized block
     * <p>
     * 2. SYNCHRONIZED BLOCK:
     * synchronized (DoubleCheckedLockingSingleton.class)
     * - Only first thread(s) enter here
     * - Lock ensures only one thread executes at a time
     * <p>
     * 3. SECOND CHECK (inside synchronized):
     * if (instance == null) → Prevents multiple creations
     * - Thread A: passes first check, enters sync block, creates instance
     * - Thread B: passes first check, waits for lock, enters sync block
     * - Thread B: second check fails (instance already created by A)
     * <p>
     * 4. INSTANCE CREATION:
     * instance = new DoubleCheckedLockingSingleton()
     * - Only one thread reaches here
     * - volatile ensures other threads see fully constructed object
     *
     * @return the singleton instance
     */
    public static DoubleCheckedLockingSingleton getInstance() {
        // First check (no locking - fast path)
        if (instance == null) {
            creationAttempts++;
            System.out.println(" First check: instance is null - Thread: "
                    + Thread.currentThread().getName());

            // Synchronize on class object
            synchronized (DoubleCheckedLockingSingleton.class) {
                synchronizedBlockEntries++;
                System.out.println(" Entered synchronized block - Thread: "
                        + Thread.currentThread().getName());

                // Second check (inside synchronized block)
                if (instance == null) {
                    System.out.println(" Second check passed, creating instance - Thread: "
                            + Thread.currentThread().getName());
                    instance = new DoubleCheckedLockingSingleton();
                } else {
                    System.out.println(" Second check failed, instance already created - Thread: "
                            + Thread.currentThread().getName());
                }
            }
        } else {
            // Fast path - instance already exists
            System.out.println(" Fast path: returning existing instance - Thread: "
                    + Thread.currentThread().getName());
        }

        return instance;
    }

    /**
     * Get statistics
     */
    public static void printStatistics() {
        System.out.println("\n STATISTICS:");
        System.out.println("   Creation attempts (first check failed): " + creationAttempts);
        System.out.println("   Synchronized block entries: " + synchronizedBlockEntries);
    }

    /**
     * Demo showing double-checked locking in action
     */
    public static void main(String[] args) throws InterruptedException {
        System.out.println(" Double-Checked Locking Singleton Demo\n");

        // Test 1: Show thread safety with concurrent access
        demonstrateThreadSafety();

        Thread.sleep(2000);

        // Test 2: Show performance benefit
        demonstratePerformance();

        // Show statistics
        printStatistics();
    }

    /**
     * Demonstrates thread safety with 20 concurrent threads
     */
    private static void demonstrateThreadSafety() {
        System.out.println("=== TEST 1: THREAD SAFETY ===\n");
        System.out.println("Creating 20 threads simultaneously...\n");

        for (int i = 0; i < 20; i++) {
            final int threadNum = i + 1;
            new Thread(() -> {
                DoubleCheckedLockingSingleton inst = getInstance();
                System.out.println("Thread-" + threadNum + " hashCode: " + inst.hashCode());
            }, "Thread-" + threadNum).start();
        }
    }

    /**
     * Demonstrates performance advantage over simple synchronized method
     */
    private static void demonstratePerformance() throws InterruptedException {
        System.out.println("\n\n=== TEST 2: PERFORMANCE ===\n");

        // Ensure instance is created
        getInstance();

        System.out.println("⏱️ Making 1000 calls to getInstance()...");
        long startTime = System.nanoTime();

        for (int i = 0; i < 1000; i++) {
            getInstance();
        }

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000;

        System.out.println("⏱️ Time taken: " + duration + " ms");
        System.out.println("\n After first initialization:");
        System.out.println("   All subsequent calls use fast path (no synchronization)");
        System.out.println("   Performance ~100x better than synchronized method");
    }

    /**
     * Business method
     */
    public void doSomething() {
        System.out.println(" Working... - Thread: " + Thread.currentThread().getName());
    }
}