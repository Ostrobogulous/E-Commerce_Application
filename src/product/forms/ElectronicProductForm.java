package product.forms;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ElectronicProductForm {

    private static String readBrand() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter brand: ");
        return scanner.nextLine();
    }

    private static String readWarranty() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter warranty (years): ");
            String warranty = scanner.nextLine();
            if (utils.NumberHelper.isPositiveInteger(warranty)) {
                return warranty;
            }
            System.out.println("Invalid warranty!");
        }
    }

    private static String readColor() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter color: ");
        return scanner.nextLine();
    }

    public static Map<String, String> readData() {
        Map<String, String> data = new HashMap<>(ProductForm.readData());
        String brand = readBrand();
        String warranty = readWarranty();
        String color = readColor();
        data.putAll(Map.of("brand", brand, "warranty", warranty, "color", color));
        return data;
    }

}
