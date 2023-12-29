package shoppingprocess.models;

import product.models.Product;

public class ProductItem {
    Product product;
    int quantity;

    public ProductItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
    
    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
