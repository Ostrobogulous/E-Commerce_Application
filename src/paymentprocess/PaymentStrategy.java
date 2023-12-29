package paymentprocess;

import java.util.Map;

public interface PaymentStrategy {
    void processPayment(double amount) throws Exception;

    boolean makePayment(double amount);

    static PaymentStrategy createInstance(Map<String, String> data) {
        return null;
    }
}
