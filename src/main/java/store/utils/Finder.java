package store.utils;

import java.util.List;
import store.domain.Inventory;
import store.domain.Products;
import store.domain.Promotion;
import store.dto.ProductsStatus;
import store.dto.PromotionStatus;

public class Finder {

    public static Promotion findPromotionByName(List<Promotion> promotions, String promotionName) {
        for (Promotion promotion : promotions) {
            if (PromotionStatus.of(promotion).name().equals(promotionName)) {
                return promotion;
            }
        }
        return null;
    }

    public static Products findProductByName(List<Products> products, String productName) {
        for (Products product : products) {
            if (ProductsStatus.of(product).product().getName().equals(productName)) {
                return product;
            }
        }
        return null;
    }

    public static Inventory findInventoryByName(List<Inventory> inventories, String productName) {
        for (Inventory inventory : inventories) {
            if (findProductByName(inventory.getProducts(), productName) != null) {
                return inventory;
            }
        }
        return null;
    }
}
