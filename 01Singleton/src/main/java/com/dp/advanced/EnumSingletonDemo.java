package com.dp.advanced;

public class EnumSingletonDemo {

    public static void main(String[] args) {

        System.out.println(" Application started");

        // Accessing singleton instance
        EnumSingleton s1 = EnumSingleton.INSTANCE;
        EnumSingleton s2 = EnumSingleton.INSTANCE;

        System.out.println("\n Are both references same?");
        System.out.println("s1 == s2 : " + (s1 == s2));

        System.out.println("\n HashCodes:");
        System.out.println("s1 hashCode: " + s1.hashCode());
        System.out.println("s2 hashCode: " + s2.hashCode());

        System.out.println("\n Testing state sharing:");
        s1.increment();
        s2.increment();

        System.out.println("\nFinal counter value: " + s1.getCounter());

        s1.doSomething();
    }
}
