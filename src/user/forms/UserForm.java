package user.forms;

import java.util.Map;
import java.util.Scanner;

public class UserForm {
    public static String readFirstName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter first name: ");
        return scanner.nextLine();
    }

    public static String readLastName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter last name: ");
        return scanner.nextLine();
    }

    public static String readEmail() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            if (utils.AuthenticationHelper.isValidEmail(email))
                return email;
            System.out.println("Invalid email (email must contain '@')!");
        }
    }

    public static String readPassword() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            if (utils.AuthenticationHelper.isValidPassword(password))
                return password;
            System.out.println("Invalid password!");
        }
    }

    public static Map<String, String> readInput() {
        String firstName = readFirstName();
        String lastName = readLastName();
        String email = readEmail();
        String password = readPassword();
        password = utils.PasswordHash.hashPassword(password);
        return Map.of("firstName", firstName, "lastName", lastName, "email", email, "password", password);
    }
}
