package paymentprocess;

import java.util.Map;

public class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    private String cardHolder;
    private String expiryDate;
    private String cvv;

    public CreditCardPayment(String cardNumber, String cardHolder, String expiryDate, String cvv) {
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    @Override
    public void processPayment(double amount) throws Exception {
        System.out.println("Processing credit card payment for $" + amount);

        boolean paymentSuccess = makePayment(amount);

        if (paymentSuccess) {
            System.out.println("Payment successful!");
        } else {
            throw new Exception("Payment failed. Please check your credit card details");
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
        return new CreditCardPayment(data.get("cardNumber"), data.get("cardHolder"), data.get("expiryDate"), data.get("cvv"));
    }

}
