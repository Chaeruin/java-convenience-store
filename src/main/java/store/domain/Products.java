package store.domain;

public class Products {
    private final Product product;
    private final Promotion promotion;
    private final int quantity;

    public Products(Product product, Promotion promotion, int quantity) {
        this.product = product;
        this.promotion = promotion;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return this.product;
    }

    public Promotion getPromotion() {
        return this.promotion;
    }

    public int getQuantity() {
        return this.quantity;
    }
}
