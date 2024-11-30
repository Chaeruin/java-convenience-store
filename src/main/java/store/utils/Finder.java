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

    // 1. promotion == Null -> product 반환
    // 2. promotion이 null이 아니고 dateConverter가 true거나 -> product 반환
    // 3. promotion이 null이 아닌데 dateConverter가 false(기간내 상품이 아닌 경우)인 경우
    public static Products findProductByName(List<Products> products, String productName) {
        for (Products product : products) {
            Products conditional = conditionalProducts(product, productName);
            if (conditional != null) {
                return conditional;
            }
        }
        return null;
    }

    public static Products conditionalProducts(Products product, String productName) {
        if (ProductsStatus.of(product).product().getName().equals(productName)) {
            if (product.getPromotion() == null) {
                return product;
            } else if (product.getPromotion() != null && DateConverter.isDateInPromotions(product.getPromotion())) {
                return product;
            } else if (product.getPromotion() != null && !DateConverter.isDateInPromotions(product.getPromotion())) {
                return null;
            }
        }
        return null;
    }

    public static Products findNullPromotionProduct(List<Products> products, Products product) {
        for (Products pr : products) {
            if (pr.getPromotion() == null && ProductsStatus.of(pr).product().getName()
                    .equals(ProductsStatus.of(product).product().getName())) {
                return pr;
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
