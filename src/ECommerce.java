import choice.ChoiceForm;
import choice.CustomerChoice;
import choice.SellerChoice;
import paymentprocess.PayPalPayment;
import paymentprocess.PaymentMethod;
import paymentprocess.PaymentStrategy;
import paymentprocess.forms.CreditCardForm;
import paymentprocess.forms.PayPalForm;
import paymentprocess.forms.PaymentMethodForm;
import product.Category;
import product.ProductManagement;
import product.SearchOption;
import product.forms.*;
import product.models.BookProduct;
import product.models.ElectronicProduct;
import product.models.MusicalProduct;
import product.models.Product;
import shoppingprocess.forms.ProductItemForm;
import user.UserManagement;
import user.forms.LoginForm;
import user.forms.UserForm;
import utils.Helper;

import java.util.HashMap;
import java.util.Map;


public class ECommerce {
    UserManagement userManagement;
    ProductManagement productManagement;

    ECommerce() {
        userManagement = new UserManagement();
        productManagement = new ProductManagement();
    }

    private static void displayCustomerMenu() {
        System.out.println("-------------Customer Menu-------------");
        for (CustomerChoice choice : CustomerChoice.values()) {
            System.out.println(choice.ordinal() + 1 + ". " + choice.getChoiceName() + " - " + choice.getChoiceDescription());
        }
        System.out.println();
    }


    private static void displaySellerMenu() {
        System.out.println("-------------Seller Menu-------------");
        for (SellerChoice choice : SellerChoice.values()) {
            System.out.println(choice.ordinal() + 1 + ". " + choice.getChoiceName() + " - " + choice.getChoiceDescription());
        }
        System.out.println();
    }

    public static void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ECommerce eCommerce = new ECommerce();
        eCommerce.run();
    }

    private void run() {
        System.out.println("Welcome to E-Commerce Application!");
        while (true) {
            sleep();
            System.out.println();
            System.out.println("Choose an option:");
            System.out.print("1. Customer Menu \n2. Seller Menu\n3. Exit\n");
            int choice = ChoiceForm.readChoice(3);
            if (choice == 1) {
                runCustomerMenu();
            } else if (choice == 2) {
                runSellerMenu();
            } else if (choice == 3) {
                System.out.println("Exiting the library management system.");
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void runSellerMenu() {
        while (true) {
            System.out.println();
            displaySellerMenu();
            try {
                SellerChoice userChoice = ChoiceForm.readSellerChoice();
                if (userChoice.requiresLogin() && !userManagement.isLoggedIn()) {
                    throw new Exception("You must be logged in to perform this action");
                }

                switch (userChoice) {
                    case LOGIN -> handleSellerLogin();
                    case REGISTER -> handleSellerRegister();
                    case SEARCH_PRODUCTS -> handleSearchProducts();
                    case VIEW_INVENTORY -> handleViewInventory();
                    case CREATE_PRODUCT -> handleCreateProduct();
                    case UPDATE_PRODUCT -> handleUpdateProduct();
                    case DELETE_PRODUCT -> handleDeleteProduct();
                    case LOGOUT -> handleLogout();
                    case GO_BACK -> {
                        handleLogout();
                        System.out.println("Going back...");
                        return;
                    }
                    default -> System.out.println("Invalid choice");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            sleep();
        }
    }

    private void runCustomerMenu() {
        while (true) {
            System.out.println();
            displayCustomerMenu();
            try {
                CustomerChoice userChoice = ChoiceForm.readUserChoice();
                if (userChoice.requiresLogin() && !userManagement.isLoggedIn()) {
                    throw new Exception("You must be logged in to perform this action");
                }

                switch (userChoice) {
                    case LOGIN -> handleCustomerLogin();
                    case REGISTER -> handleCustomerRegister();
                    case SEARCH_PRODUCTS -> handleSearchProducts();
                    case ADD_TO_CART -> handleCustomerAddToCart();
                    case VIEW_CART -> handleCustomerViewCart();
                    case CLEAR_CART -> handleCustomerClearCart();
                    case CHECKOUT -> handleCustomerCheckout();
                    case LOGOUT -> handleLogout();
                    case VIEW_ORDERS -> handleViewOrders();
                    case GO_BACK -> {
                        handleLogout();
                        System.out.println("Going back...");
                        return;
                    }
                    default -> System.out.println("Invalid choice");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            sleep();
        }
    }

    private void handleSellerLogin() throws Exception {
        if (userManagement.isLoggedIn()) {
            throw new Exception("You are already logged in!");
        }
        System.out.println("Logging in...");
        Map<String, String> data = LoginForm.readInput();
        userManagement.login(data);
        userManagement.verifySeller();
        System.out.println("You have successfully logged in!");
        userManagement.displayWelcomeMessage();
    }

    private void handleSellerRegister() throws Exception {
        if (userManagement.isLoggedIn()) {
            throw new Exception("You need to logout to create new account!");
        }
        System.out.println("Registering...");
        Map<String, String> data = UserForm.readInput();
        userManagement.createSeller(data);
        System.out.println("You have successfully registered as a seller, welcome to our E-Commerce Application.");
    }

    private void handleSearchProducts() {
        System.out.println("Searching products...");
        SearchOption option = SearchProductForm.readSearchOption();
        Map<String, String> filters;
        switch (option) {
            case CATEGORY -> {
                Category category = CategoryForm.readCategory();
                filters = ProductForm.readFilters();
                productManagement.searchByCategory(category, filters);
            }
            case NAME -> {
                String name = GetProductForm.readProductName();
                filters = ProductForm.readFilters();
                productManagement.searchByName(name, filters);
            }
            case BRAND -> {
                String brand = GetProductForm.readBrand();
                filters = ProductForm.readFilters();
                productManagement.searchByBrand(brand, filters);
            }
        }
    }

    private void handleViewInventory() {
        System.out.println("Viewing inventory...");
        userManagement.viewProducts();
    }

    private void handleCreateProduct() {
        System.out.println("Creating product...");
        Category category = CategoryForm.readCategory();
        Map<String, String> data = switch (category) {
            case BOOK_PRODUCT -> BookProductForm.readData();
            case MUSICAL_PRODUCT -> MusicalProductForm.readData();
            case ELECTRONIC_PRODUCT -> ElectronicProductForm.readData();
        };
        productManagement.addProduct(userManagement.getUserId(), data, category);
        System.out.printf("Product '%s' created successfully.\n", data.get("name"));
    }

    private void handleUpdateProduct() throws Exception {
        System.out.println("Updating product...");
        int productId = GetProductForm.readProductId();
        Product product = Product.getProductById(productId);
        productManagement.isProductOwner(userManagement.getUserId(), product);
        boolean confirmation = Helper.confirmUpdate(product);
        if (!confirmation) {
            System.out.println("Abort operation.");
            return;
        }

        Map<String, String> data = new HashMap<>();
        if (product instanceof BookProduct) {
            data = BookProductForm.readData();
        } else if (product instanceof ElectronicProduct) {
            data = ElectronicProductForm.readData();
        } else if (product instanceof MusicalProduct) {
            data = MusicalProductForm.readData();
        }
        productManagement.updateProduct(data, product);
        System.out.printf("Product '%s' updated successfully.\n", data.get("name"));
    }

    private void handleDeleteProduct() throws Exception {
        System.out.println("Deleting product...");
        int productId = GetProductForm.readProductId();
        Product product = Product.getProductById(productId);
        productManagement.isProductOwner(userManagement.getUserId(), product);
        boolean confirmation = Helper.confirmDelete(product);
        if (!confirmation) {
            System.out.println("Abort operation");
            return;
        }

        productManagement.deleteProduct(product);
        System.out.printf("Product '%s' deleted successfully.\n", product.getName());
    }

    private void handleLogout() {
        if (!userManagement.isLoggedIn()) {
            return;
        }
        System.out.println("Logging out...");
        userManagement.logout();
        System.out.println("Logged out");
    }

    private void handleCustomerLogin() throws Exception {
        if (userManagement.isLoggedIn()) {
            throw new Exception("You are already logged in!");
        }
        System.out.println("Logging in...");
        Map<String, String> data = LoginForm.readInput();
        userManagement.login(data);
        userManagement.verifyCustomer();
        System.out.println("You have successfully logged in!");
        userManagement.displayWelcomeMessage();
    }

    private void handleCustomerRegister() throws Exception {
        if (userManagement.isLoggedIn()) {
            throw new Exception("You need to logout to create new account!");
        }
        System.out.println("Registering...");
        Map<String, String> data = UserForm.readInput();
        userManagement.createCustomer(data);
        System.out.println("You have successfully registered as a customer, welcome to our E-Commerce Application.");
    }

    private void handleCustomerAddToCart() throws Exception {
        System.out.println("Adding to cart...");
        Map<String, String> data = ProductItemForm.readData();
        userManagement.addToShoppingCart(data);
    }

    private void handleCustomerViewCart() {
        System.out.println("Viewing cart...");
        userManagement.viewShoppingCart();
    }

    private void handleCustomerClearCart() {
        System.out.println("Clearing cart...");
        userManagement.clearShoppingCart();
    }

    private void handleCustomerCheckout() throws Exception {
        if (userManagement.isEmptyShoppingCart()) {
            throw new Exception("Your shopping cart is empty!");
        }
        if (!Helper.confirmPurchase(userManagement.totalAmount())) {
            System.out.println("Abort operation");
            return;
        }
        Map<String, String> data;
        PaymentStrategy method = null;
        PaymentMethod paymentMethod = PaymentMethodForm.readPaymentMethod();
        switch (paymentMethod) {
            case PAYPAL:
                data = PayPalForm.readData();
                method = PayPalPayment.createInstance(data);
                break;
            case CREDIT_CARD:
                data = CreditCardForm.readData();
                method = PayPalPayment.createInstance(data);
        }
        userManagement.checkoutShoppingCart(method);
        userManagement.clearShoppingCart();
    }

    public void handleViewOrders() throws Exception {
        userManagement.viewOrders();
    }

}
