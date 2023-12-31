package user;

import paymentprocess.PaymentStrategy;
import product.models.Product;
import shoppingprocess.models.Order;
import shoppingprocess.models.ProductItem;
import user.models.Customer;
import user.models.Seller;
import user.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages user-related operations such as user creation, login, and shopping cart handling.
 */
public class UserManagement {
    private User user;

    public UserManagement() {
        user = null;
    }

    /**
     * Creates a new customer with the provided data.
     * Give customer a shopping-cart (the shopping-cart is related to the session)
     */
    public void createCustomer(Map<String, String> data) throws Exception {
        Map<String, String> userData = new HashMap<>(data);
        userData.put("role", "customer");
        createUser(userData);
    }

    /**
     * Creates a new seller with the provided data.
     */
    public void createSeller(Map<String, String> data) throws Exception {
        Map<String, String> userData = new HashMap<>(data);
        userData.put("role", "seller");
        createUser(userData);
    }

    /**
     * Creates a new user with the provided data.
     */
    public void createUser(Map<String, String> data) throws Exception {
        User user = User.createInstance(data);
        if (userAlreadyExists(user)) {
            throw new Exception("User with this email already exist");
        }
        user.save();
    }

    /**
     * Gets the ID of the currently logged-in user.
     */
    public int getUserId() {
        return user.getId();
    }

    /**
     * Checks if a user with the same email already exists.
     */
    private boolean userAlreadyExists(User user) {
        try {
            User.get(user.getEmail());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Logs in a user with the provided email and password.
     */
    public void login(Map<String, String> data) throws Exception {
        Map<String, String> userData = new HashMap<>();
        User user = User.get(data.get("email"));
        if (utils.PasswordHash.checkPassword(data.get("password"), user.getPassword())) {
            userData.putAll(data);
            userData.putAll(Map.of("firstName", user.getFirstName(), "lastName", user.getLastName()));
            if (user.getRole().equals("seller")) {
                this.user = Seller.createInstance(userData);
                this.user.setId(user.getId());
            } else if (user.getRole().equals("customer")) {
                this.user = Customer.createInstance(userData);
                this.user.setId(user.getId());
                ((Customer) this.user).getShoppingCart().setCustomerId(user.getId());
            }
        } else {
            throw new Exception("Wrong password");
        }
    }

    /**
     * Displays a welcome message for the logged-in user.
     */
    public void displayWelcomeMessage() {
        if (isLoggedIn()) {
            System.out.printf("Welcome back %s %s!\n", user.getFirstName(), user.getLastName());
        }
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    /**
     * Checks if the logged-in user is a seller.
     */
    public boolean isSeller() {
        return user instanceof Seller;
    }

    /**
     * Checks if the logged-in user is a customer.
     */
    public boolean isCustomer() {
        return user instanceof Customer;
    }

    /**
     * Verifies that the logged-in user is a seller; otherwise, sets the user to null.
     */
    public void verifySeller() throws Exception {
        if (!isSeller()) {
            this.user = null;
            throw new Exception("This is not a seller account");
        }
    }

    /**
     * Verifies that the logged-in user is a customer; otherwise, sets the user to null.
     */
    public void verifyCustomer() throws Exception {
        if (!isCustomer()) {
            this.user = null;
            throw new Exception("This is not a customer account");
        }
    }

    /**
     * Logs out the user by setting the user to null.
     */
    public void logout() {
        this.user = null;
    }

    private static void updateRoleInDatabase(int userId, String newRole) {
        Connection connection = db.DatabaseUtils.getDbConnection();

        try {
            connection.setAutoCommit(false);

            String updateRoleQuery = "UPDATE user SET role = ? WHERE id = ?";
            PreparedStatement updateRoleStatement = connection.prepareStatement(updateRoleQuery);

            updateRoleStatement.setString(1, newRole);
            updateRoleStatement.setInt(2, userId);

            updateRoleStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
        }
    }

    /**
     * Adds a product to the shopping cart of the logged-in customer.
     */
    public void addToShoppingCart(Map<String, String> data) throws Exception {
        int productId = Integer.parseInt(data.get("productId"));
        int quantity = Integer.parseInt(data.get("quantity"));
        Product product = Product.getProductById(productId);
        if (product.getStock() == 0) {
            throw new Exception("Product out of stock");
        }
        int quantityInCart = ((Customer) user).getShoppingCart().getProductQuantity(productId);
        if (product.getStock() < quantity + quantityInCart) {
            throw new Exception("Not enough stock, reduce your quantity");
        }
        Customer customer = (Customer) this.user;
        ProductItem productItem = new ProductItem(product, quantity);
        customer.addToShoppingCart(productItem);
        System.out.printf("%d pieces of product %s added to shopping cart. You now have %d pieces in total of this product in your shopping-cart.\n", quantity, product.getName(), quantity + quantityInCart);
    }

    public void removeFromShoppingCart(int productId) throws Exception {
        Customer customer = (Customer) this.user;
        customer.getShoppingCart().removeItem(productId);
    }

    /**
     * View the shopping cart items of the logged-in customer.
     */
    public void viewShoppingCart() {
        Customer customer = (Customer) this.user;
        customer.getShoppingCart().printItems();
    }

    public void clearShoppingCart() {
        Customer customer = (Customer) this.user;
        customer.getShoppingCart().clear();
    }

    /**
     *  Display total amount in the shopping cart.
     */
    public double totalAmount() {
        Customer customer = (Customer) this.user;
        return customer.getShoppingCart().calculateTotalPrice();
    }

    /**
     * Displays the orders for the logged-in customer.
     */
    public void viewOrders() throws Exception {
        List<Order> orders = ((Customer) user).getOrders();
        if (orders.isEmpty()) {
            System.out.println("You have no orders yet.");
            return;
        }
        System.out.println("Your orders:");
        for (Order order : orders) {
            order.displayInfo();
            System.out.println();
        }
    }

    /**
     * Displays the products in the inventory of the logged-in seller.
     */
    public void viewProducts() {
        List<Product> products = ((Seller) user).getProducts();
        if (products.isEmpty()) {
            System.out.println("Your inventory is empty.");
            return;
        }
        System.out.println("Your inventory:");
        for (Product product : products) {
            product.displayInfo();
            System.out.println("Stock: " + product.getStock());
            System.out.println();
        }
    }

    /**
     * Displays the sales of the logged-in seller.
     */
    public void viewSales() throws Exception {
        List<Order> sales = ((Seller) user).getSales();
        if (sales.isEmpty()) {
            System.out.println("You have no sales yet.");
            return;
        }
        System.out.println("Your sales:");
        for (Order order : sales) {
            String username = User.getUsername(order.getUserId());
            order.displayInfo(username);
            System.out.println();
        }
    }

    /**
     * Checks out the shopping cart of the logged-in customer using the specified payment method.
     */
    public void checkoutShoppingCart(PaymentStrategy method) throws Exception {
        Customer customer = (Customer) this.user;
        customer.getShoppingCart().checkout(method);
    }

    /**
     * Checks if the shopping cart of the logged-in customer is empty.
     */
    public boolean isEmptyShoppingCart() {
        return ((Customer) this.user).getShoppingCart().isEmpty();
    }

}

