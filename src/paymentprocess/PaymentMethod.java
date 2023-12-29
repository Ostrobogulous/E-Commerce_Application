package paymentprocess;

public enum PaymentMethod {
    PAYPAL("PayPal"),
    CREDIT_CARD("Credit Card");

    private final String displayName;


    PaymentMethod(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
