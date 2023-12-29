package product.forms;

import product.SearchOption;

import java.util.Scanner;

public class SearchProductForm {

    public static SearchOption readSearchOption() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose a search option:");

        for (SearchOption option : SearchOption.values()) {
            System.out.println(option.ordinal() + 1 + ". " + option.getDisplayName());
        }

        while (true) {
            System.out.print("Enter your chosen search option: ");
            int choice = scanner.nextInt();

            if (choice >= 1 && choice <= SearchOption.values().length) {
                return SearchOption.values()[choice - 1];
            } else {
                System.out.println("Invalid choice. Please enter a number between 1 and " + SearchOption.values().length);
            }
        }
    }
}
