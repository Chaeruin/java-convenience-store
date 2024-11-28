package store.dto;


import store.domain.Product;
import store.domain.Products;
import store.domain.Promotion;

public record ProductsStatus(
        Product product,
        Promotion promotion,
        int quantity
) {
    public static ProductsStatus of(final Products product) {
        return new ProductsStatus(
                product.getProduct(),
                product.getPromotion(),
                product.getQuantity()
        );
    }
}
