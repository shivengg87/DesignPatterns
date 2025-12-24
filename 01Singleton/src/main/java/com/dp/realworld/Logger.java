package com.designpattern.singleton.realworld;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * REAL-WORLD EXAMPLE: APPLICATION LOGGER
 * =======================================
 * <p>
 * Use Case:
 * Logging is a classic singleton use case because:
 * - All components need to log to same destination
 * - Should not have multiple logger instances writing to same file
 * - Need centralized log management
 * - Thread-safe logging required
 * <p>
 * Business Scenario:
 * Enterprise application needs to:
 * - Log events from multiple threads/components
 * - Write logs to file and console
 * - Support different log levels (DEBUG, INFO, WARN, ERROR)
 * - Provide structured logging
 * - Handle high-volume logging efficiently
 * <p>
 * Why Singleton?
 * Single point for all logging operations
 * Prevents file access conflicts
 * Centralized log configuration
 * Thread-safe log writing
 * <p>
 * Real-World Benefits:
 * - Consistent log format across application
 * - Better performance (buffered writing)
 * - Easier log analysis and monitoring
 * - Simplified debugging
 */
public class Logger {

    // Logger configuration
    private LogLevel currentLogLevel = LogLevel.INFO;
    private boolean logToConsole = true;
    private boolean logToFile = true;
    private String logFilePath = "application.log";
    // Log statistics
    private long debugCount = 0;
    private long infoCount = 0;
    private long warnCount = 0;
    private long errorCount = 0;
    private long totalLogs = 0;
    // File writer
    private BufferedWriter fileWriter;
    // Date formatter for timestamps
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    // Async logging queue (for high-performance scenarios)
    private BlockingQueue<String> logQueue = new LinkedBlockingQueue<>();
    private Thread asyncLoggerThread;
    private volatile boolean running = true;
    /**
     * Private constructor - initializes logger
     */
    private Logger() {
        System.out.println(" Initializing Logger...");
        initializeFileWriter();
        startAsyncLogger();
        System.out.println(" Logger initialized successfully\n");
    }

    /**
     * Get singleton instance
     */
    public static Logger getInstance() {
        return SingletonHelper.INSTANCE.logger;
    }

    /**
     * Real-world usage example: Web application
     */
    public static void demonstrateWebApplicationUsage() {
        System.out.println("=== WEB APPLICATION LOGGING SCENARIO ===\n");

        Logger logger = Logger.getInstance();

        // Application startup
        logger.info(" Application starting...");
        logger.info("Loading configuration...");
        logger.debug("Config file path: /etc/app/config.properties");

        // User authentication
        logger.info("User authentication request from IP: 192.168.1.100");
        logger.debug("Checking credentials for user: john@example.com");
        logger.info(" User authenticated successfully");

        // Database operation
        logger.info("Executing database query...");
        logger.debug("SQL: SELECT * FROM users WHERE active = true");
        logger.info("Query returned 150 results");

        // Warning example
        logger.warn(" High memory usage detected: 85%");
        logger.warn("Cache size approaching limit: 950/1000");

        // Error example
        try {
            // Simulate error
            throw new IllegalStateException("Database connection pool exhausted");
        } catch (Exception e) {
            logger.error("Failed to process user request", e);
        }

        logger.info("Request completed in 245ms");
    }

    /**
     * Demonstrate multi-threaded logging
     */
    public static void demonstrateThreadSafety() {
        System.out.println("\n=== THREAD SAFETY DEMONSTRATION ===\n");
        System.out.println("Simulating 5 concurrent services logging...\n");

        String[] services = {"UserService", "OrderService", "PaymentService",
                "EmailService", "NotificationService"};

        for (int i = 0; i < 5; i++) {
            final String serviceName = services[i];
            new Thread(() -> {
                Logger logger = Logger.getInstance();

                for (int j = 1; j <= 3; j++) {
                    logger.info(serviceName + " processing request #" + j);

                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }

                logger.info(serviceName + " completed all requests");
            }, serviceName).start();
        }
    }

    /**
     * Demonstrate different log levels
     */
    public static void demonstrateLogLevels() {
        System.out.println("\n=== LOG LEVELS DEMONSTRATION ===\n");

        Logger logger = Logger.getInstance();

        // Test with INFO level (default)
        System.out.println(" Current log level: INFO\n");
        logger.debug("This DEBUG message will NOT be shown");
        logger.info("This INFO message will be shown");
        logger.warn("This WARN message will be shown");
        logger.error("This ERROR message will be shown");

        // Change to DEBUG level
        System.out.println("\n Changing log level to DEBUG\n");
        logger.setLogLevel(LogLevel.DEBUG);
        logger.debug("Now DEBUG messages are visible!");
        logger.info("INFO messages still visible");

        // Change to ERROR level
        System.out.println("\n📊 Changing log level to ERROR\n");
        logger.setLogLevel(LogLevel.ERROR);
        logger.info("This INFO message will NOT be shown");
        logger.warn("This WARN message will NOT be shown");
        logger.error("Only ERROR messages will be shown");

        // Reset to INFO
        logger.setLogLevel(LogLevel.INFO);
    }

    /**
     * Performance test
     */
    public static void demonstratePerformance() {
        System.out.println("\n=== PERFORMANCE TEST ===\n");

        Logger logger = Logger.getInstance();

        // Disable console output for performance test
        logger.setLogToConsole(false);

        System.out.println(" Logging 1000 messages...");
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 1000; i++) {
            logger.info("Performance test message #" + i);
        }

        long endTime = System.currentTimeMillis();

        // Re-enable console
        logger.setLogToConsole(true);

        System.out.println(" Completed in: " + (endTime - startTime) + " ms");
        System.out.println(" Average per message: " + ((endTime - startTime) / 1000.0) + " ms");
        System.out.println(" Async queue handled " + 1000 + " messages efficiently");
    }

    /**
     * Main demo method
     */
    public static void main(String[] args) throws InterruptedException {
        System.out.println(" Logger Singleton Pattern Demo\n");
        System.out.println(" Real-world example: Application logging system\n");

        // Demo 1: Web application usage
        demonstrateWebApplicationUsage();

        Thread.sleep(1000);

        // Demo 2: Thread safety
        demonstrateThreadSafety();

        Thread.sleep(3000);

        // Demo 3: Log levels
        demonstrateLogLevels();

        Thread.sleep(1000);

        // Demo 4: Performance
        demonstratePerformance();

        Thread.sleep(1000);

        // Verify singleton
        System.out.println("\n=== SINGLETON VERIFICATION ===\n");
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();

        System.out.println(" Checking singleton:");
        System.out.println("logger1 == logger2: " + (logger1 == logger2));
        System.out.println("All components use same logger: " + (logger1 == logger2 ? " YES" : " NO"));

        // Show statistics
        logger1.printStatistics();

        // Shutdown
        Thread.sleep(1000);
        logger1.shutdown();

        System.out.println("\n" + "=".repeat(60));
        System.out.println(" KEY BENEFITS:");
        System.out.println("=".repeat(60));
    }

    /**
     * Initialize file writer for logging to file
     */
    private void initializeFileWriter() {
        if (!logToFile) return;

        try {
            File logFile = new File(logFilePath);
            fileWriter = new BufferedWriter(new FileWriter(logFile, true)); // append mode
            System.out.println(" Log file opened: " + logFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println(" Could not open log file: " + e.getMessage());
            logToFile = false;
        }
    }

    /**
     * Start async logger thread
     * Processes log messages from queue in background
     */
    private void startAsyncLogger() {
        asyncLoggerThread = new Thread(() -> {
            while (running || !logQueue.isEmpty()) {
                try {
                    String logMessage = logQueue.poll();
                    if (logMessage != null) {
                        writeToFile(logMessage);
                    } else {
                        Thread.sleep(10); // Prevent busy waiting
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }, "AsyncLoggerThread");

        asyncLoggerThread.setDaemon(true);
        asyncLoggerThread.start();
    }

    /**
     * Log DEBUG message
     */
    public void debug(String message) {
        log(LogLevel.DEBUG, message, null);
    }

    /**
     * Log INFO message
     */
    public void info(String message) {
        log(LogLevel.INFO, message, null);
    }

    /**
     * Log WARN message
     */
    public void warn(String message) {
        log(LogLevel.WARN, message, null);
    }

    /**
     * Log ERROR message
     */
    public void error(String message) {
        log(LogLevel.ERROR, message, null);
    }

    /**
     * Log ERROR message with exception
     */
    public void error(String message, Throwable throwable) {
        log(LogLevel.ERROR, message, throwable);
    }

    /**
     * Main logging method
     *
     * @param level     log level
     * @param message   log message
     * @param throwable optional exception
     */
    private synchronized void log(LogLevel level, String message, Throwable throwable) {
        // Check if this level should be logged
        if (level.ordinal() < currentLogLevel.ordinal()) {
            return;
        }

        // Update statistics
        totalLogs++;
        switch (level) {
            case DEBUG:
                debugCount++;
                break;
            case INFO:
                infoCount++;
                break;
            case WARN:
                warnCount++;
                break;
            case ERROR:
                errorCount++;
                break;
        }

        // Format log message
        String formattedMessage = formatLogMessage(level, message, throwable);

        // Log to console
        if (logToConsole) {
            System.out.println(formattedMessage);
        }

        // Log to file (async)
        if (logToFile) {
            try {
                logQueue.put(formattedMessage);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Format log message with timestamp, level, thread, and message
     * <p>
     * Format: [timestamp] [LEVEL] [ThreadName] message
     */
    private String formatLogMessage(LogLevel level, String message, Throwable throwable) {
        StringBuilder sb = new StringBuilder();

        // Timestamp
        sb.append("[").append(dateFormat.format(new Date())).append("] ");

        // Log level
        sb.append("[").append(level.getDisplayName()).append("] ");

        // Thread name
        sb.append("[").append(Thread.currentThread().getName()).append("] ");

        // Message
        sb.append(message);

        // Exception stack trace if provided
        if (throwable != null) {
            sb.append("\n").append(getStackTrace(throwable));
        }

        return sb.toString();
    }

    /**
     * Get stack trace as string
     */
    private String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        return sw.toString();
    }

    /**
     * Write log message to file
     * Called by async logger thread
     */
    private void writeToFile(String message) {
        if (fileWriter == null) return;

        try {
            fileWriter.write(message);
            fileWriter.newLine();
            fileWriter.flush(); // Ensure immediate write
        } catch (IOException e) {
            System.err.println(" Error writing to log file: " + e.getMessage());
        }
    }

    /**
     * Get current log level
     */
    public LogLevel getLogLevel() {
        return currentLogLevel;
    }

    /**
     * Set log level
     * Only messages at or above this level will be logged
     */
    public void setLogLevel(LogLevel level) {
        System.out.println(" Setting log level to: " + level.getDisplayName());
        this.currentLogLevel = level;
    }

    /**
     * Enable/disable console logging
     */
    public void setLogToConsole(boolean enabled) {
        this.logToConsole = enabled;
        System.out.println(" Console logging " + (enabled ? "enabled" : "disabled"));
    }

    /**
     * Enable/disable file logging
     */
    public void setLogToFile(boolean enabled) {
        this.logToFile = enabled;
        System.out.println(" File logging " + (enabled ? "enabled" : "disabled"));
    }

    /**
     * Get log statistics
     */
    public void printStatistics() {
        System.out.println("\n LOGGER STATISTICS:");
        System.out.println("=".repeat(60));
        System.out.println("   Total logs: " + totalLogs);
        System.out.println("   DEBUG: " + debugCount);
        System.out.println("   INFO: " + infoCount);
        System.out.println("   WARN: " + warnCount);
        System.out.println("   ERROR: " + errorCount);
        System.out.println("   Current level: " + currentLogLevel.getDisplayName());
        System.out.println("   Console logging: " + (logToConsole ? "enabled" : "disabled"));
        System.out.println("   File logging: " + (logToFile ? "enabled" : "disabled"));
        System.out.println("   Log file: " + logFilePath);
        System.out.println("   Queue size: " + logQueue.size());
        System.out.println("   Instance hashCode: " + this.hashCode());
        System.out.println("=".repeat(60));
    }

    /**
     * Close logger and release resources
     * Should be called on application shutdown
     */
    public void shutdown() {
        System.out.println("\n Shutting down logger...");
        running = false;

        try {
            // Wait for async thread to finish processing queue
            asyncLoggerThread.join(5000);

            // Close file writer
            if (fileWriter != null) {
                fileWriter.close();
                System.out.println(" Log file closed");
            }
        } catch (Exception e) {
            System.err.println(" Error during logger shutdown: " + e.getMessage());
        }
    }

    // Singleton instance using Enum (thread-safe, prevents attacks)
    private static enum SingletonHelper {
        INSTANCE;
        private final Logger logger = new Logger();
    }

    // Log levels
    public enum LogLevel {
        DEBUG(" DEBUG"),
        INFO(" INFO"),
        WARN(" WARN"),
        ERROR(" ERROR");

        private final String displayName;

        LogLevel(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}