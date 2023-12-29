package utils;
import product.models.Product;
import java.util.Scanner;

public class Helper {
    private static Scanner scanner = new Scanner(System.in);

    public static void printSeperator(){
        System.out.println("============================================================================================================");
    }

    public static void ignoreText(Scanner scanner){
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
    }

    public static boolean confirmDelete(Product product){
        product.displayInfo();
        System.out.print("Are you sure you want to delete this product? (yes/no): ");
        String answer = scanner.nextLine();
        return answer.equalsIgnoreCase("yes");
    }

    public static boolean confirmUpdate(Product product){
        product.displayInfo();
        System.out.print("Are you sure you want to update this product? (yes/no): ");
        String answer = scanner.nextLine();
        return answer.equalsIgnoreCase("yes");
    }

    public static boolean confirmPurchase(double amount){
        System.out.println("Total price: " + amount + "$");
        System.out.print("Do you confirm your purchase? (yes/no): ");
        String answer = scanner.nextLine();
        return answer.equalsIgnoreCase("yes");
    }
}
