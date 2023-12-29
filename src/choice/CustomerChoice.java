package choice;

public enum CustomerChoice {
    REGISTER("Register", "Create a new customer account", false),
    LOGIN("Login", "Log into your account", false),
    LOGOUT("Logout", "Log out from the current account", true),
    SEARCH_PRODUCTS("Search Products", "Search for products", false),
    ADD_TO_CART("Add to Cart", "Add a product to the shopping cart", true),
    VIEW_CART("View Cart", "View items you have in cart", true),
    CLEAR_CART("Clear Cart", "Clear all items in your shopping cart", true),
    CHECKOUT("Checkout", "Checkout your shopping-cart", true),
    VIEW_ORDERS("View Orders", "View your recent orders", true),
    GO_BACK("Go back", "Go back to the main menu", false);

    private final String choiceName;
    private final String choiceDescription;
    private final boolean requiresLogin;

    CustomerChoice(String choiceName, String choiceDescription, boolean requiresLogin) {
        this.choiceName = choiceName;
        this.choiceDescription = choiceDescription;
        this.requiresLogin = requiresLogin;
    }

    public String getChoiceName() {
        return choiceName;
    }

    public String getChoiceDescription() {
        return choiceDescription;
    }

    public boolean requiresLogin() {
        return requiresLogin;
    }
}
