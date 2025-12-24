package com.dp.strategy;


/**
 * Demo class to showcase the Strategy Pattern
 *
 * Strategy Pattern allows selecting an algorithm at runtime.
 * Here we demonstrate different payment methods that can be switched dynamically.
 */
public class StrategyPatternDemo {

    public static void main(String[] args) {

        System.out.println("     STRATEGY PATTERN - PAYMENT SYSTEM DEMO         ");


        PaymentContext paymentContext = new PaymentContext();
        double purchaseAmount = 2500.00;

        System.out.println("\nPurchase Amount: ₹" + purchaseAmount);

        // Scenario 1: Payment via Credit Card
        demonstratePayment(
                paymentContext,
                new CreditCardPayment(
                        "1234567890123456",
                        "John Doe",
                        "123",
                        "12/25"
                ),
                purchaseAmount
        );

        // Scenario 2: Payment via UPI
        demonstratePayment(
                paymentContext,
                new UPIPayment("john.doe@paytm", "9876543210"),
                purchaseAmount
        );

        // Scenario 3: Payment via Cash (exact amount)
        demonstratePayment(
                paymentContext,
                new CashPayment(2500.00),
                purchaseAmount
        );

        // Scenario 4: Payment via Cash (with change)
        demonstratePayment(
                paymentContext,
                new CashPayment(3000.00),
                1750.50
        );

        // Scenario 5: Failed payment - insufficient cash
        demonstratePayment(
                paymentContext,
                new CashPayment(1000.00),
                purchaseAmount
        );

        // Scenario 6: No payment method selected
        System.out.println("\n[Scenario 6] Attempting payment without strategy:");
        PaymentContext emptyContext = new PaymentContext();
        emptyContext.executePayment(500.00);

    }

    private static void demonstratePayment(PaymentContext context,
                                           PaymentStrategy strategy,
                                           double amount) {
        System.out.println("\n─────────────────────────────────────────────────────");
        context.setPaymentStrategy(strategy);
        context.executePayment(amount);
    }
}