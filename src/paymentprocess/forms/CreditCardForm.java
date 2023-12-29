package paymentprocess.forms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;

public class CreditCardForm {

    private static String readCreditCard() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter credit card number: ");
            String creditCardNumber = scanner.nextLine();
            if (isValidCreditCard(creditCardNumber)) {
                return creditCardNumber;
            }
            System.out.println("Invalid credit card number!");
        }
    }

    private static boolean isValidCreditCard(String creditCardNumber) {
        return !creditCardNumber.isEmpty();
    }

    private static String readCardHolder() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter cardholder name: ");
        return scanner.nextLine();
    }

    private static String readExpirationDate() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter expiration date (MM/YY): ");
            String expirationDate = scanner.nextLine();
            if (isValidExpirationDate(expirationDate)) {
                return expirationDate;
            }
            System.out.println("Invalid expiration date!");
        }
    }

    private static boolean isValidExpirationDate(String expirationDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yy");
            dateFormat.setLenient(false);

            Date currentDate = new Date();
            Date enteredDate = dateFormat.parse(expirationDate);
            return enteredDate != null && enteredDate.after(currentDate);
        } catch (ParseException e) {
            return false;
        }
    }

    private static String readCvv() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter CVV: ");
            String cvv = scanner.nextLine();
            if (isValidCvv(cvv)) {
                return cvv;
            }
            System.out.println("Invalid CVV!");
        }
    }

    private static boolean isValidCvv(String cvv) {
        return cvv.length() == 3 || cvv.length() == 4;
    }

    public static Map<String, String> readData() {
        String creditCardNumber = readCreditCard();
        String cardHolder = readCardHolder();
        String expirationDate = readExpirationDate();
        String cvv = readCvv();
        return Map.of("creditCardNumber", creditCardNumber, "cardHolder", cardHolder, "expirationDate", expirationDate, "cvv", cvv);
    }

}
