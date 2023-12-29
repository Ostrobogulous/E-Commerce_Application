package choice;

import java.util.Scanner;

public class ChoiceForm {
    public static int readChoice(int numberOfChoices) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Give a choice number: ");
            int choice = scanner.nextInt();
            if (choice >= 1 && choice <= numberOfChoices) {
                return choice;
            }
            System.out.println("Invalid choice!");
        }
    }

    public static CustomerChoice readUserChoice() {
        Scanner scanner = new Scanner(System.in);
        int choiceIndex;
        while (true) {
            try {
                System.out.print("Enter the number of your choice: ");
                choiceIndex = Integer.parseInt(scanner.nextLine()) - 1;
                if (choiceIndex >= 0 && choiceIndex < CustomerChoice.values().length) {
                    break;
                } else {
                    System.out.println("Invalid choice. Please enter a number between 1 and " + CustomerChoice.values().length);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        return CustomerChoice.values()[choiceIndex];
    }

    public static SellerChoice readSellerChoice() {
        Scanner scanner = new Scanner(System.in);
        int choiceIndex;
        while (true) {
            try {
                System.out.print("Enter the number of your choice: ");
                choiceIndex = Integer.parseInt(scanner.nextLine()) - 1;
                if (choiceIndex >= 0 && choiceIndex < SellerChoice.values().length) {
                    break;
                } else {
                    System.out.println("Invalid choice. Please enter a number between 1 and " + SellerChoice.values().length);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        return SellerChoice.values()[choiceIndex];
    }
}
