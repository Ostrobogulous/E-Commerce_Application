package product;

import product.models.BookProduct;
import product.models.ElectronicProduct;
import product.models.MusicalProduct;
import product.models.Product;
import utils.Helper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;


/**
 * Manages product-related operations such as adding, updating, deleting,
 * and searching for products based on different criteria.
 */
public class ProductManagement {

    /**
     * Adds a new product based on the specified category.
     */
    public void addProduct(int userId, Map<String, String> data, Category category) {
        data.put("userId", Integer.toString(userId));
        if (category == Category.ELECTRONIC_PRODUCT) {
            ElectronicProduct electronicProduct = ElectronicProduct.createInstance(data);
            electronicProduct.save();
        } else if (category == Category.MUSICAL_PRODUCT) {
            MusicalProduct musicalProduct = MusicalProduct.createInstance(data);
            musicalProduct.save();
        } else if (category == Category.BOOK_PRODUCT) {
            BookProduct bookProduct = BookProduct.createInstance(data);
            bookProduct.save();
        }
    }

    public void deleteProduct(Product product) throws Exception {
        product.delete();

    }

    public void updateProduct(Map<String, String> data, Product product) throws Exception {
        product.update(data);
        product.displayInfo();
        product.save();
    }

    /**
     * Checks if the user is the owner of a given product.
     */
    public void isProductOwner(int userId, Product product) throws Exception {
        if (product.getUserId() != userId) {
            throw new Exception("You are not the owner of this product");
        }
    }

    /**
     * Lists all products from different categories, sorted by ID.
     */
    private List<Product> listProducts() {
        List<Product> allProducts = new ArrayList<>();

        allProducts.addAll(listBookProducts());
        allProducts.addAll(listElectronicProducts());
        allProducts.addAll(listMusicalProducts());
        allProducts.sort(Comparator.comparingInt(Product::getId));
        return allProducts;
    }

    /**
     * Lists all book products.
     */
    private List<Product> listBookProducts() {
        return BookProduct.list();
    }

    /**
     * Lists all electronic products.
     */
    private List<Product> listElectronicProducts() {
        return ElectronicProduct.list();
    }


    /**
     * Lists all musical products.
     */
    private List<Product> listMusicalProducts() {
        return MusicalProduct.list();
    }

    /**
     * Checks if a product is available (stock > 0).
     */
    private static boolean isAvailable(Product p) {
        return p.getStock() > 0;
    }

    /**
     * Filters a list of products based on some filters (for now just price [min, max])
     */
    private static List<Product> filterProducts(List<Product> products, Map<String, String> filters) {
        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getPrice() >= Integer.parseInt(filters.get("minPrice")) && product.getPrice() <= Integer.parseInt(filters.get("maxPrice"))) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

    /**
     * Searches for products by category and applies filters.
     */
    public void searchByCategory(Category category, Map<String, String> filters) {
        List<Product> products = new ArrayList<>();
        if (category == Category.ELECTRONIC_PRODUCT) {
            products = listElectronicProducts();

        } else if (category == Category.MUSICAL_PRODUCT) {
            products = listMusicalProducts();
        } else if (category == Category.BOOK_PRODUCT) {
            products = listBookProducts();
        }
        if (!filters.containsKey("minPrice")) {
            filters.put("minPrice", "0");
        }
        if (!filters.containsKey("maxPrice")) {
            filters.put("maxPrice", "100000000");
        }
        products = filterProducts(products, filters);
        System.out.println();
        for (Product product : products) {
            if (isAvailable(product) && product.getPrice() >= Integer.parseInt(filters.get("minPrice")) && product.getPrice() <= Integer.parseInt(filters.get("maxPrice"))) {
                Helper.printSeperator();
                product.displayInfo();
            }
        }
    }

    /**
     * Searches for products by name and applies filters.
     */
    public void searchByName(String name, Map<String, String> filters) {
        List<Product> products = listProducts();
        if (!filters.containsKey("minPrice")) {
            filters.put("minPrice", "0");
        }
        if (!filters.containsKey("maxPrice")) {
            filters.put("maxPrice", "100000000");
        }
        products = filterProducts(products, filters);
        System.out.println();
        for (Product product : products) {
            if (!isAvailable(product)) {
                continue;
            }
            if (product.getName().contains(name) && product.getPrice() >= Integer.parseInt(filters.get("minPrice")) && product.getPrice() <= Integer.parseInt(filters.get("maxPrice"))) {
                Helper.printSeperator();
                product.displayInfo();
            }
        }
    }

    /**
     * Searches for products by brand and applies filters.
     */
    public void searchByBrand(String name, Map<String, String> filters) {
        List<Product> products = listProducts();
        if (!filters.containsKey("minPrice")) {
            filters.put("minPrice", "0");
        }
        if (!filters.containsKey("maxPrice")) {
            filters.put("maxPrice", "100000000");
        }
        products = filterProducts(products, filters);
        System.out.println();
        for (Product product : products) {
            if (!isAvailable(product)) {
                continue;
            }
            if (product instanceof MusicalProduct) {
                if (((MusicalProduct) product).getBrand().equals(name)) {
                    Helper.printSeperator();
                    product.displayInfo();
                }
            } else if (product instanceof ElectronicProduct) {
                if (((ElectronicProduct) product).getBrand().contains(name)) {
                    Helper.printSeperator();
                    product.displayInfo();
                }
            }
        }
    }

}
