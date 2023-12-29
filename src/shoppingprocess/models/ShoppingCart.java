package shoppingprocess.models;

import paymentprocess.PaymentStrategy;
import product.models.Product;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    int customerId;
    Map<Integer, ProductItem> items;

    public ShoppingCart(int customerId) {
        this.customerId = customerId;
        items = new HashMap<>();
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int getProductQuantity(int productId) {
        if (items.containsKey(productId)) {
            return items.get(productId).getQuantity();
        }
        return 0;
    }

    public void addItem(ProductItem item) {
        if (items.containsKey(item.getProduct().getId())) {
            item.setQuantity(item.getQuantity() + getProductQuantity(item.getProduct().getId()));
            items.put(item.getProduct().getId(), item);
        } else {
            items.put(item.getProduct().getId(), item);
        }
    }

    public void removeItem(int productId) throws Exception {
        if (items.containsKey(productId)) {
            items.remove(productId);
        } else {
            throw new Exception("Product not found in shopping cart (id: " + productId + ")");
        }
    }

    public void printItems() {
        if (isEmpty()) {
            System.out.println("Your shopping cart is empty");
        } else {
            System.out.println("********* Shopping Cart *********");
            for (ProductItem item : items.values()) {
                item.getProduct().displayInfo();
                System.out.println("Quantity: " + item.getQuantity());
                System.out.println("Price: " + item.getQuantity() * item.getProduct().getPrice());
                System.out.println();
            }
        }
    }
    public void clear() {
        items.clear();
        System.out.println("Your shopping cart is now empty");
    }

    public double calculateTotalPrice() {
        double totalPrice = 0;
        for (ProductItem item : items.values()) {
            totalPrice += item.getProduct().getPrice() * item.getQuantity();
        }
        return totalPrice;
    }

    public void checkout(PaymentStrategy method) throws Exception {
        assert method != null;
        processPayment(method);
        for (ProductItem item : items.values()) {
            Product product = item.getProduct();
            int quantity = item.getProduct().getStock();
            product.changeQuantity(quantity - item.getQuantity());
            Order order = new Order(product.getId(), customerId, item.getQuantity(), product.getPrice() * item.getQuantity());
            order.save();
        }
    }

    public void processPayment(PaymentStrategy method) throws Exception {
        double totalPrice = calculateTotalPrice();
        method.processPayment(totalPrice);
    }
}
