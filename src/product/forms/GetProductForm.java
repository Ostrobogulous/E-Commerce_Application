package product.forms;

import utils.Helper;

import java.util.Scanner;

public class GetProductForm {
    public static int readProductId() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter product id: ");
        return scanner.nextInt();
    }

    public static String readProductName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter product name: ");
        Helper.ignoreText(scanner);
        return scanner.nextLine();
    }

    public static String readBrand() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter brand: ");
        Helper.ignoreText(scanner);
        return scanner.nextLine();
    }
}
