package com.dp.composition;


/**
 * Demo class showcasing Composition over Inheritance principle
 *
 * COMPOSITION OVER INHERITANCE PRINCIPLE:
 * Instead of inheriting behavior through class hierarchy (IS-A),
 * compose objects using interfaces and delegation (HAS-A).
 *
 * Benefits demonstrated:
 * 1. Flexibility - Mix and match components at runtime
 * 2. Reusability - Components can be reused across different vehicles
 * 3. Maintainability - Changes to components don't affect vehicle classes
 * 4. Testability - Components can be tested independently
 */
public class CompositionPatternDemo {

    public static void main(String[] args) {

        System.out.println("   COMPOSITION OVER INHERITANCE - VEHICLE SYSTEM   ");


        // Scenario 1: Sports Car with Petrol Engine
        demonstrateScenario1();

        // Scenario 2: Electric Car - Swapping engines at runtime
        demonstrateScenario2();

        // Scenario 3: Diesel Truck
        demonstrateScenario3();

        // Scenario 4: Motorcycle with Manual Transmission
        demonstrateScenario4();

        // Scenario 5: Runtime Component Swapping
        demonstrateScenario5();

    }

    private static void demonstrateScenario1() {
        System.out.println("\n" + "-".repeat(50));
        System.out.println("SCENARIO 1: Sports Car with Petrol Engine");
        System.out.println("-".repeat(50));

        Car sportsCar = new Car("Ferrari", "F8 Tributo", 2);
        sportsCar.setEngine(new PetrolEngine(710, 3.9));
        sportsCar.setTransmission(new AutomaticTransmission("Sport"));

        sportsCar.displayInfo();
        sportsCar.start();
        sportsCar.accelerate();
        sportsCar.stop();
    }

    private static void demonstrateScenario2() {
        System.out.println("\n" + "-".repeat(50));
        System.out.println("SCENARIO 2: Electric Car - Zero Emissions");
        System.out.println("-".repeat(50));

        Car electricCar = new Car("Tesla", "Model S Plaid", 4);
        electricCar.setEngine(new ElectricEngine(1020, 100));
        electricCar.setTransmission(new AutomaticTransmission("Eco"));

        electricCar.displayInfo();
        electricCar.start();
        electricCar.accelerate();
        electricCar.stop();
    }

    private static void demonstrateScenario3() {
        System.out.println("\n" + "-".repeat(50));
        System.out.println("SCENARIO 3: Heavy-Duty Diesel Truck");
        System.out.println("-".repeat(50));

        Truck dieselTruck = new Truck("Volvo", "FH16", 40.0);
        dieselTruck.setEngine(new DieselEngine(750, 3550));
        dieselTruck.setTransmission(new ManualTransmission(12));

        dieselTruck.displayInfo();
        dieselTruck.start();
        dieselTruck.loadCargo(35.0);
        dieselTruck.accelerate();
        dieselTruck.stop();
    }

    private static void demonstrateScenario4() {
        System.out.println("\n" + "-".repeat(50));
        System.out.println("SCENARIO 4: Sport Motorcycle");
        System.out.println("-".repeat(50));

        Motorcycle sportBike = new Motorcycle("Ducati", "Panigale V4", "Sport");
        sportBike.setEngine(new PetrolEngine(214, 1.1));
        sportBike.setTransmission(new ManualTransmission(6));

        sportBike.displayInfo();
        sportBike.start();
        sportBike.wheelie();
        sportBike.stop();
    }

    private static void demonstrateScenario5() {
        System.out.println("\n" + "-".repeat(50));
        System.out.println("SCENARIO 5: Runtime Component Swapping");
        System.out.println("-".repeat(50));

        Car hybridCar = new Car("Toyota", "Prius", 4);

        System.out.println("\n Initially configured with Petrol Engine:");
        hybridCar.setEngine(new PetrolEngine(121, 1.8));
        hybridCar.setTransmission(new AutomaticTransmission("Eco"));
        hybridCar.displayInfo();
        hybridCar.start();

        System.out.println("\n Swapping to Electric Engine at runtime:");
        hybridCar.setEngine(new ElectricEngine(95, 8));
        hybridCar.displayInfo();
        hybridCar.start();

        System.out.println("\n This flexibility is the power of COMPOSITION!");
    }


}

