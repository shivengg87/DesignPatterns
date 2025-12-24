package com.dp.strategy;
/**
 * Concrete Strategy - Cash Payment Implementation
 */
public class CashPayment implements PaymentStrategy {
    private double cashTendered;

    public CashPayment(double cashTendered) {
        this.cashTendered = cashTendered;
    }

    @Override
    public boolean pay(double amount) {
        System.out.println("\n=== Processing Cash Payment ===");
        System.out.println("Amount Due: ₹" + amount);
        System.out.println("Cash Tendered: ₹" + cashTendered);

        // Validate sufficient cash
        if (cashTendered >= amount) {
            double change = cashTendered - amount;
            System.out.println("✓ Payment of ₹" + amount + " successful via Cash!");

            if (change > 0) {
                System.out.println("Change to return: ₹" + String.format("%.2f", change));
            } else {
                System.out.println("Exact amount received - No change.");
            }
            return true;
        }

        System.out.println("✗ Payment failed - Insufficient cash");
        System.out.println("Short by: ₹" + String.format("%.2f", (amount - cashTendered)));
        return false;
    }

    @Override
    public String getPaymentMethodName() {
        return "Cash";
    }
}
