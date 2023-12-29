package user.forms;

import java.util.Map;
import java.util.Scanner;

public class LoginForm {
    private static String readEmail() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter email: ");
        return scanner.nextLine();
    }

    private static String readPassword() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter password: ");
        return scanner.nextLine();
    }

    public static Map<String, String> readInput() {
        Scanner scanner = new Scanner(System.in);
        String email = readEmail();
        String password = readPassword();
        return Map.of("email", email, "password", password);
    }
}
