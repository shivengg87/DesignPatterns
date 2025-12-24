package com.dp.strategy;

/**
 * Concrete Strategy - UPI Payment Implementation
 */
public class UPIPayment implements PaymentStrategy {
    private String upiId;
    private String mobileNumber;

    public UPIPayment(String upiId, String mobileNumber) {
        this.upiId = upiId;
        this.mobileNumber = mobileNumber;
    }

    @Override
    public boolean pay(double amount) {
        System.out.println("\n=== Processing UPI Payment ===");
        System.out.println("UPI ID: " + upiId);
        System.out.println("Mobile: " + maskMobileNumber(mobileNumber));
        System.out.println("Amount: ₹" + amount);

        // Simulate UPI payment processing
        if (validateUPI()) {
            System.out.println("✓ Payment of ₹" + amount + " successful via UPI!");
            System.out.println("Transaction ID: UPI" + System.currentTimeMillis());
            return true;
        }

        System.out.println("✗ Payment failed - Invalid UPI details");
        return false;
    }

    @Override
    public String getPaymentMethodName() {
        return "UPI";
    }

    private boolean validateUPI() {
        // Simulate UPI validation
        return upiId != null && upiId.contains("@")
                && mobileNumber != null && mobileNumber.length() == 10;
    }

    private String maskMobileNumber(String mobile) {
        if (mobile == null || mobile.length() < 4) {
            return "****";
        }
        return "******" + mobile.substring(mobile.length() - 4);
    }
}