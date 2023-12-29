package product.forms;

import product.Category;

import java.util.Scanner;

public class CategoryForm {

    public static Category readCategory() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Available Categories:");

        for (Category category : Category.values()) {
            System.out.println(category.ordinal() + 1 + ". " + category.getDisplayName());
        }
        while (true) {
            System.out.print("Enter your chosen category: ");
            int choice = scanner.nextInt();

            if (choice >= 1 && choice <= Category.values().length) {
                return Category.values()[choice - 1];
            } else {
                System.out.println("Invalid choice. Please enter a number between 1 and " + Category.values().length);
            }
        }
    }
}
