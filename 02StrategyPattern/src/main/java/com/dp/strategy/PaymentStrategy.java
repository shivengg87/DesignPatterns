package com.dp.strategy;
/**
 * Strategy Interface - Defines the contract for all payment strategies
 */
public interface PaymentStrategy {
    /**
     * Process payment using the specific payment method
     * @param amount the amount to be paid
     * @return true if payment is successful, false otherwise
     */
    boolean pay(double amount);

    /**
     * Get the name of the payment method
     * @return payment method name
     */
    String getPaymentMethodName();
}