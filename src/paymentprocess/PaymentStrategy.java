package paymentprocess;

import java.util.Map;

/**
 * Interface representing a payment strategy, providing methods for processing and making payments.
 */
public interface PaymentStrategy {
    /**
     * Processes a payment with the specified amount.
     * @throws Exception If an error occurs during the payment processing.
     */
    void processPayment(double amount) throws Exception;

    /**
     * Attempts to make a payment with the specified amount.
     *
     * @return True if the payment is successful, false otherwise.
     */
    boolean makePayment(double amount);

    /**
     * Creates an instance of PaymentStrategy based on the provided data.
     */
    static PaymentStrategy createInstance(Map<String, String> data) {
        return null;
    }
}
