package com.dp.realworld;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * EXAMPLE: APPLICATION CONFIGURATION MANAGER
 * ======================================================
 * <p>
 * Use Case:
 * Managing application configuration is ideal for singleton because:
 * - Configuration should be loaded once and reused
 * - All components need same configuration values
 * - Changes to config should be visible everywhere
 * - Expensive to load configuration repeatedly
 * <p>
 * Business Scenario:
 * A web application needs to:
 * - Load configuration from properties file
 * - Provide config to all services/controllers
 * - Support runtime config updates
 * - Cache configuration in memory
 * <p>
 * Why Singleton?
 * Load configuration file only once (performance)
 * Consistent configuration across entire application
 * Central point for configuration management
 * Easy to update config at runtime
 * <p>
 * Real-World Benefits:
 * - Faster application startup
 * - Reduced memory usage
 * - Simplified configuration access
 * - Easy to add new configuration properties
 */
public class ConfigurationManager {

    // Default configuration values
    private static final String DEFAULT_CONFIG_FILE = "application.properties";
    // Configuration storage
    private Properties properties;
    private Map<String, String> runtimeConfig;

    // Metadata
    private long loadedAt;
    private String configFilePath;
    private int accessCount = 0;

    /**
     * Private constructor - loads configuration
     */
    private ConfigurationManager() {
        System.out.println(" Initializing Configuration Manager...");

        this.properties = new Properties();
        this.runtimeConfig = new HashMap<>();
        this.configFilePath = DEFAULT_CONFIG_FILE;

        // Load configuration
        loadConfiguration();

        // Load default values
        loadDefaultConfiguration();

        this.loadedAt = System.currentTimeMillis();
        System.out.println(" Configuration Manager initialized successfully\n");
    }

    /**
     * Get singleton instance
     */
    public static ConfigurationManager getInstance() {
        return SingletonHelper.INSTANCE.manager;
    }

    /**
     * Real-world usage example
     */
    public static void demonstrateUsage() {
        System.out.println("=== REAL-WORLD USAGE SCENARIO ===\n");
        System.out.println("Scenario: Web application startup\n");

        ConfigurationManager config = ConfigurationManager.getInstance();

        // Application initialization
        System.out.println(" Starting application...");
        System.out.println("   App Name: " + config.get("app.name"));
        System.out.println("   Version: " + config.get("app.version"));
        System.out.println("   Environment: " + config.get("app.environment"));

        // Server configuration
        System.out.println("\n Configuring server...");
        System.out.println("   Server Port: " + config.getInt("server.port"));
        System.out.println("   Server Host: " + config.get("server.host"));
        System.out.println("   Max Threads: " + config.getInt("server.maxThreads"));

        // Database configuration
        System.out.println("\n Configuring database...");
        System.out.println("   DB URL: " + config.get("db.url"));
        System.out.println("   DB Username: " + config.get("db.username"));
        System.out.println("   Max Connections: " + config.getInt("db.maxConnections"));

        // Cache configuration
        System.out.println("\n Configuring cache...");
        System.out.println("   Cache Enabled: " + config.getBoolean("cache.enabled"));
        System.out.println("   Cache TTL: " + config.getLong("cache.ttl") + " seconds");
        System.out.println("   Max Size: " + config.getInt("cache.maxSize"));

        // Runtime configuration update
        System.out.println("\n Updating configuration at runtime...");
        config.set("feature.newDashboard", "true");
        config.set("server.maxThreads", "200");
        System.out.println("   New Dashboard: " + config.get("feature.newDashboard"));
        System.out.println("   Updated Max Threads: " + config.getInt("server.maxThreads"));
    }

    /**
     * Demonstrate thread-safe access
     */
    public static void demonstrateThreadSafety() {
        System.out.println("\n=== THREAD SAFETY DEMONSTRATION ===\n");
        System.out.println("Simulating multiple components accessing config...\n");

        for (int i = 0; i < 5; i++) {
            final int componentNum = i + 1;
            final String[] services = {"UserService", "ProductService", "OrderService",
                    "PaymentService", "EmailService"};

            new Thread(() -> {
                ConfigurationManager config = ConfigurationManager.getInstance();
                System.out.println(" " + services[componentNum - 1] +
                        " got config instance: " + config.hashCode());

                // Each service reads its configuration
                String dbUrl = config.get("db.url");
                int cacheSize = config.getInt("cache.maxSize");
                System.out.println("   " + services[componentNum - 1] +
                        " - DB: " + dbUrl + ", Cache: " + cacheSize);

            }, services[componentNum - 1]).start();
        }
    }

    /**
     * Main demo method
     */
    public static void main(String[] args) throws InterruptedException {
        System.out.println(" Configuration Manager Singleton Demo\n");

        // Demo 1: Basic usage
        demonstrateUsage();

        Thread.sleep(1000);

        // Demo 2: Thread safety
        demonstrateThreadSafety();

        Thread.sleep(2000);

        // Demo 3: Show all configuration
        ConfigurationManager config = ConfigurationManager.getInstance();
        config.printAllConfiguration();

        // Demo 4: Statistics
        config.printStatistics();

        // Verify singleton
        System.out.println("\n=== SINGLETON VERIFICATION ===\n");
        ConfigurationManager config1 = ConfigurationManager.getInstance();
        ConfigurationManager config2 = ConfigurationManager.getInstance();

        System.out.println("🔍 Checking singleton:");
        System.out.println("config1 == config2: " + (config1 == config2));

        System.out.println("\n" + "=".repeat(60));
        System.out.println(" KEY BENEFITS:");
        System.out.println("• Configuration loaded once, used everywhere");
        System.out.println("• Consistent config across all application components");
        System.out.println("• Easy runtime configuration updates");
        System.out.println("• Fast access with in-memory caching");
        System.out.println("=".repeat(60));
    }

    /**
     * Load configuration from file
     * In production: Handle multiple sources (file, env vars, remote config)
     */
    private void loadConfiguration() {
        System.out.println(" Loading configuration from: " + configFilePath);

        try {
            // Try to load from file
            InputStream input = getClass().getClassLoader()
                    .getResourceAsStream(configFilePath);

            if (input != null) {
                properties.load(input);
                System.out.println(" Configuration loaded from file");
                input.close();
            } else {
                System.out.println(" Configuration file not found, using defaults");
            }

        } catch (IOException e) {
            System.err.println(" Could not load configuration: " + e.getMessage());
            System.out.println(" Using default configuration");
        }
    }

    /**
     * Load default configuration values
     * These are fallback values if not specified in config file
     */
    private void loadDefaultConfiguration() {
        System.out.println(" Loading default configuration...");

        // Application settings
        setDefaultIfAbsent("app.name", "MyApplication");
        setDefaultIfAbsent("app.version", "1.0.0");
        setDefaultIfAbsent("app.environment", "development");

        // Server settings
        setDefaultIfAbsent("server.port", "8080");
        setDefaultIfAbsent("server.host", "localhost");
        setDefaultIfAbsent("server.maxThreads", "100");

        // Database settings
        setDefaultIfAbsent("db.url", "jdbc:mysql://localhost:3306/mydb");
        setDefaultIfAbsent("db.username", "root");
        setDefaultIfAbsent("db.password", "");
        setDefaultIfAbsent("db.maxConnections", "20");

        // Cache settings
        setDefaultIfAbsent("cache.enabled", "true");
        setDefaultIfAbsent("cache.ttl", "3600");
        setDefaultIfAbsent("cache.maxSize", "1000");

        // API settings
        setDefaultIfAbsent("api.key", "your-api-key-here");
        setDefaultIfAbsent("api.timeout", "30000");
        setDefaultIfAbsent("api.retries", "3");

        // Logging settings
        setDefaultIfAbsent("log.level", "INFO");
        setDefaultIfAbsent("log.file", "application.log");

        System.out.println(" Default configuration loaded");
    }

    /**
     * Set default value if property doesn't exist
     */
    private void setDefaultIfAbsent(String key, String defaultValue) {
        if (!properties.containsKey(key)) {
            properties.setProperty(key, defaultValue);
        }
    }

    /**
     * Get configuration value as String
     *
     * @param key configuration key
     * @return configuration value or null
     */
    public String get(String key) {
        accessCount++;

        // Check runtime config first (for dynamic updates)
        if (runtimeConfig.containsKey(key)) {
            return runtimeConfig.get(key);
        }

        // Then check properties file
        return properties.getProperty(key);
    }

    /**
     * Get configuration value with default
     *
     * @param key          configuration key
     * @param defaultValue value to return if key not found
     * @return configuration value or default
     */
    public String get(String key, String defaultValue) {
        String value = get(key);
        return value != null ? value : defaultValue;
    }

    /**
     * Get configuration value as integer
     */
    public int getInt(String key) {
        String value = get(key);
        try {
            return value != null ? Integer.parseInt(value) : 0;
        } catch (NumberFormatException e) {
            System.err.println(" Invalid integer for key: " + key);
            return 0;
        }
    }

    /**
     * Get configuration value as boolean
     */
    public boolean getBoolean(String key) {
        String value = get(key);
        return value != null && Boolean.parseBoolean(value);
    }

    /**
     * Get configuration value as long
     */
    public long getLong(String key) {
        String value = get(key);
        try {
            return value != null ? Long.parseLong(value) : 0L;
        } catch (NumberFormatException e) {
            System.err.println(" Invalid long for key: " + key);
            return 0L;
        }
    }

    /**
     * Set configuration value at runtime
     * Useful for dynamic configuration updates
     *
     * @param key   configuration key
     * @param value new value
     */
    public void set(String key, String value) {
        System.out.println(" Updating configuration: " + key + " = " + value);
        runtimeConfig.put(key, value);
    }

    /**
     * Check if configuration key exists
     */
    public boolean hasKey(String key) {
        return runtimeConfig.containsKey(key) || properties.containsKey(key);
    }

    /**
     * Get all configuration keys
     */
    public void printAllConfiguration() {
        System.out.println("\n ALL CONFIGURATION PROPERTIES:");
        System.out.println("=".repeat(60));

        // Print from properties file
        System.out.println("\n From configuration file:");
        properties.forEach((key, value) -> {
            // Hide sensitive values
            String displayValue = key.toString().toLowerCase().contains("password")
                    ? "********" : value.toString();
            System.out.println("   " + key + " = " + displayValue);
        });

        // Print runtime updates
        if (!runtimeConfig.isEmpty()) {
            System.out.println("\n Runtime updates:");
            runtimeConfig.forEach((key, value) ->
                    System.out.println("   " + key + " = " + value)
            );
        }

        System.out.println("=".repeat(60));
    }

    /**
     * Get configuration statistics
     */
    public void printStatistics() {
        System.out.println("\n CONFIGURATION MANAGER STATISTICS:");
        System.out.println("   Total properties: " + properties.size());
        System.out.println("   Runtime overrides: " + runtimeConfig.size());
        System.out.println("   Access count: " + accessCount);
        System.out.println("   Loaded at: " + new java.util.Date(loadedAt));
        System.out.println("   Uptime: " + (System.currentTimeMillis() - loadedAt) / 1000 + " seconds");
        System.out.println("   Instance hashCode: " + this.hashCode());
    }

    /**
     * Reload configuration from file
     * Useful for applying config changes without restart
     */
    public void reloadConfiguration() {
        System.out.println("\n Reloading configuration...");
        properties.clear();
        loadConfiguration();
        loadDefaultConfiguration();
        System.out.println(" Configuration reloaded");
    }

    // Singleton instance using Enum (best practice)
    private static enum SingletonHelper {
        INSTANCE;
        private final ConfigurationManager manager = new ConfigurationManager();
    }
}