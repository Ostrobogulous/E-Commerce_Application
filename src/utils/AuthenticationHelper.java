package utils;

public class AuthenticationHelper {
    public static boolean isValidEmail(String email){
        return email.contains("@");
    }

    public static boolean isValidPassword(String password){
        return true;
    }
}
