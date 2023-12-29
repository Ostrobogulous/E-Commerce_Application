package paymentprocess.forms;

import paymentprocess.PaymentMethod;

import java.util.Scanner;

public class PaymentMethodForm {
    public static PaymentMethod readPaymentMethod() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose a payment method:");

        for (PaymentMethod method : PaymentMethod.values()) {
            System.out.println(method.ordinal() + 1 + ". " + method.getDisplayName());
        }

        while (true) {
            System.out.print("Enter your chosen payment method: ");
            int choice = scanner.nextInt();

            if (choice >= 1 && choice <= PaymentMethod.values().length) {
                return PaymentMethod.values()[choice - 1];
            } else {
                System.out.println("Invalid choice. Please enter a number between 1 and " + PaymentMethod.values().length);
            }
        }
    }
}
