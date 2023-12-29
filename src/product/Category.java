package product;

public enum Category {
    ELECTRONIC_PRODUCT("Electronic Product"),
    MUSICAL_PRODUCT("Musical Product"),
    BOOK_PRODUCT("Book Product");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
