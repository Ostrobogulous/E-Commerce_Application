package utils;

public class NumberHelper {

    public static boolean isStrictlyPositiveFloat(String input){
        try {
            float price = Float.parseFloat(input);
            return price > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public static boolean isStrictlyPositiveInteger(String input) {
        try {
            int price = Integer.parseInt(input);
            return price > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isPositiveInteger(String input) {
        try {
            int price = Integer.parseInt(input);
            return price >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
