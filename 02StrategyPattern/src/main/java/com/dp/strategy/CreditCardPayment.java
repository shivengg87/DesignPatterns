package com.dp.strategy;
/**
 * Concrete Strategy - Credit Card Payment Implementation
 */
public class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    private String cardHolderName;
    private String cvv;
    private String expiryDate;

    public CreditCardPayment(String cardNumber, String cardHolderName,
                             String cvv, String expiryDate) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
    }

    @Override
    public boolean pay(double amount) {
        System.out.println("\n=== Processing Credit Card Payment ===");
        System.out.println("Card Holder: " + cardHolderName);
        System.out.println("Card Number: " + maskCardNumber(cardNumber));
        System.out.println("Amount: ₹" + amount);

        // Simulate payment processing
        if (validateCard()) {
            System.out.println("✓ Payment of ₹" + amount + " successful via Credit Card!");
            return true;
        }

        System.out.println("✗ Payment failed - Invalid card details");
        return false;
    }

    @Override
    public String getPaymentMethodName() {
        return "Credit Card";
    }

    private boolean validateCard() {
        // Simulate card validation
        return cardNumber != null && cardNumber.length() == 16
                && cvv != null && cvv.length() == 3;
    }

    private String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 4) {
            return "****";
        }
        return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
    }
}