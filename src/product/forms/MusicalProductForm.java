package product.forms;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MusicalProductForm {

    private static String readInstrumentType() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter instrument type: ");
        return scanner.nextLine();
    }

    private static String readBrand() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter brand: ");
        return scanner.nextLine();
    }


    private static String readColor() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter color: ");
        return scanner.nextLine();
    }

    public static Map<String, String> readData() {
        Map<String, String> data = new HashMap<>(ProductForm.readData());
        String instrumentType = readInstrumentType();
        String brand = readBrand();
        String color = readColor();
        data.putAll(Map.of("instrumentType", instrumentType, "brand", brand, "color", color));
        return data;
    }
}
