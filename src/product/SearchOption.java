package product;

public enum SearchOption {
    CATEGORY("Category"),
    NAME("Name"),
    BRAND("Brand");

    private final String displayName;

    SearchOption(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
