package product.forms;


import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ProductForm {

    private static String readName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter name: ");
        return scanner.nextLine();
    }

    private static String readPrice() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter price($): ");
            String price = scanner.nextLine();
            if (utils.NumberHelper.isStrictlyPositiveFloat(price)) {
                return price;
            }
            System.out.println("Invalid price!");
        }
    }

    private static String readStock() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter stock: ");
            String stock = scanner.nextLine();
            if (utils.NumberHelper.isStrictlyPositiveInteger(stock)) {
                return stock;
            }
            System.out.println("Invalid stock!");
        }
    }

    public static Map<String, String> readData() {
        String name = readName();
        String price = readPrice();
        String stock = readStock();
        return Map.of("name", name, "price", price, "stock", stock);
    }

    public static Map<String, String> readFilters() {
        Scanner scanner = new Scanner(System.in);
        Map<String, String> filters = new HashMap<>();

        Map<String, String> filterNames = new HashMap<>();
        filterNames.put("minPrice", "Minimum Price");
        filterNames.put("maxPrice", "Maximum Price");

        for (Map.Entry<String, String> entry : filterNames.entrySet()) {
            String filterKey = entry.getKey();
            String filterDisplayName = entry.getValue();

            System.out.print("Do you want to add " + filterDisplayName + "? (yes/no): ");
            String addFilter = scanner.nextLine().toLowerCase();

            if ("yes".equals(addFilter)) {
                System.out.println("Enter " + filterDisplayName + ":");
                String filterValue = scanner.nextLine();
                filters.put(filterKey, filterValue);
            }
        }

        return filters;
    }
}
