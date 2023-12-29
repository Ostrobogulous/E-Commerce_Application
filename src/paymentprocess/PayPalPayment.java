package paymentprocess;

import java.util.Map;

public class PayPalPayment implements PaymentStrategy {
    private String email;
    private String password;
    private String billingAddress;

    public PayPalPayment(String email, String password, String billingAddress) {
        this.email = email;
        this.password = password;
        this.billingAddress = billingAddress;
    }

    @Override
    public void processPayment(double amount) throws Exception {
        System.out.println("Processing PayPal payment for $" + amount);

        boolean paymentSuccess = makePayment(amount);

        if (paymentSuccess) {
            System.out.println("Payment successful!");
        } else {
            throw new Exception("Payment failed. Please check your PayPal account details.");
        }
    }

    @Override
    public boolean makePayment(double amount) {
        // Interact with the actual payment system to verify data and credit
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static PaymentStrategy createInstance(Map<String, String> data) {
        return new PayPalPayment(data.get("email"), data.get("password"), data.get("billingAddress"));
    }

}
