package product.forms;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BookProductForm {

    private static String readAuthor() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter author: ");
        return scanner.nextLine();
    }

    private static String ReadGenre() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter genre: ");
        return scanner.nextLine();
    }

    private static String readYear() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter year: ");
            String year = scanner.nextLine();
            if (utils.NumberHelper.isInteger(year))
                return year;
            System.out.println("Invalid year!");
        }
    }

    private static String readPageCount() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter page count: ");
            String pageCount = scanner.nextLine();
            if (utils.NumberHelper.isStrictlyPositiveInteger(pageCount))
                return pageCount;
            System.out.println("Invalid page count!");
        }
    }

    public static Map<String, String> readData() {
        Map<String, String> data = new HashMap<>(ProductForm.readData());
        String author = readAuthor();
        String genre = ReadGenre();
        String year = readYear();
        String pageCount = readPageCount();
        data.putAll(Map.of("author", author, "genre", genre, "year", year, "pageCount", pageCount));
        return data;
    }
}
