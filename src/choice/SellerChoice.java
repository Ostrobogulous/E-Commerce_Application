package choice;

public enum SellerChoice {
    REGISTER("Register", "Create a new seller account", false),
    LOGIN("Login", "Log into your account", false),
    LOGOUT("Logout", "Log out from the current account", true),
    SEARCH_PRODUCTS("Search Products", "Search for products", false),
    VIEW_INVENTORY("View Inventory", "View the products your created", true),
    CREATE_PRODUCT("Create Product", "Add a new product", true),
    UPDATE_PRODUCT("Update Product", "Update one of your products", true),
    DELETE_PRODUCT("Delete Product", "Delete one of your products", true),
    VIEW_SALES("View Sales", "Track the sales of your products", true),
    GO_BACK("Go Back", "Go back to the main menu", false);

    private final String choiceName;
    private final String choiceDescription;
    private final boolean requiresLogin;

    SellerChoice(String choiceName, String choiceDescription, boolean requiresLogin) {
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
