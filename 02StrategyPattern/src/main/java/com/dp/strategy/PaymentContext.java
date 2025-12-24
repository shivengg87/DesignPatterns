package com.dp.strategy;

/**
 * Context Class - Maintains reference to a Strategy object
 * and allows switching between different payment strategies at runtime
 */
public class PaymentContext {
    private PaymentStrategy paymentStrategy;

    /**
     * Set the payment strategy
     * @param paymentStrategy the strategy to use for payment
     */
    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    /**
     * Execute payment using the current strategy
     * @param amount the amount to be paid
     * @return true if payment is successful, false otherwise
     */
    public boolean executePayment(double amount) {
        if (paymentStrategy == null) {
            System.out.println("✗ Error: No payment method selected!");
            return false;
        }

        System.out.println("\nSelected Payment Method: " +
                paymentStrategy.getPaymentMethodName());
        return paymentStrategy.pay(amount);
    }

    /**
     * Get current payment method name
     * @return payment method name or "None" if not set
     */
    public String getCurrentPaymentMethod() {
        return paymentStrategy != null ?
                paymentStrategy.getPaymentMethodName() : "None";
    }
}
