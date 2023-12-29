package shoppingprocess.forms;

import utils.NumberHelper;

import java.util.Map;
import java.util.Scanner;

public class ProductItemForm {

    private static String readProductId() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Give product id: ");
            String productId = scanner.nextLine();
            if (NumberHelper.isStrictlyPositiveInteger(productId)) {
                return productId;
            }
            System.out.println("Invalid input. Please enter a positive integer.");
        }
    }

    private static String readQuantity() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Give quantity: ");
            String quantity = scanner.nextLine();
            if (NumberHelper.isStrictlyPositiveInteger(quantity)) {
                return quantity;
            }
            System.out.println("Invalid input. Please enter a positive integer.");
        }
    }

    public static Map<String, String> readData() {
        String productId = readProductId();
        String quantity = readQuantity();
        return Map.of("productId", productId, "quantity", quantity);
    }
}
