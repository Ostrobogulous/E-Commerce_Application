package paymentprocess.forms;

import utils.AuthenticationHelper;

import java.util.Map;
import java.util.Scanner;

public class PayPalForm {

    private static String readEmail() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter PayPal email: ");
            String email = scanner.nextLine();
            if (AuthenticationHelper.isValidEmail(email)) {
                return email;
            }
            System.out.println("Invalid email!");
        }
    }

    private static String readPassword() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter PayPal password: ");
        return scanner.nextLine();
    }

    private static String readBillingAddress() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter billing address: ");
        return scanner.nextLine();
    }

    public static Map<String, String> readData() {
        String email = readEmail();
        String password = readPassword();
        String billingAddress = readBillingAddress();
        return Map.of("email", email, "password", password, "billingAddress", billingAddress);
    }
}
