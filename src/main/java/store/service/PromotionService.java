package store.service;

import java.util.LinkedList;
import java.util.List;
import store.domain.Inventory;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotion;
import store.enums.PromotionName;
import store.utils.Finder;

public class PromotionService {


    // 프로모션 적용 품목 증정 리스트 추가
    public List<Products> addToPresent(List<Inventory> inventories, List<Products> buyProducts) {
        List<Products> presentations = new LinkedList<>();

        for (Products product : buyProducts) {
            if (product.getPromotion() != null) {
                Inventory inv = Finder.findInventoryByName(inventories, product.getProduct().getName());
                presentations.add(getPresent(product, inv));
            }
        }
        return presentations;
    }

    public Products getPresent(Products product, Inventory inventory) {
        String promotionName = product.getPromotion().getName();
        Product pr = product.getProduct();
        Promotion promo = product.getPromotion();
        Products invStock = Finder.findProductByName(inventory.getProducts(), pr.getName());
        int presentNumber = product.getQuantity();
        if (invStock.getQuantity() < product.getQuantity()) {
            presentNumber = invStock.getQuantity();
        }
        return casesPresent(product, promotionName, pr, promo, presentNumber);
    }

    public Products casesPresent(Products product, String promotionName, Product pr, Promotion promo,
                                 int presentNumber) {
        if (promotionName.equals(PromotionName.SPARKLING.getName())) {
            return new Products(pr, promo, presentNumber / 3);
        } else if (promotionName.equals(PromotionName.MD_RECOMMAND.getName())) {
            return new Products(pr, promo, presentNumber / 2);
        } else if (promotionName.equals(PromotionName.BRIEF_DISCOUNT.getName())) {
            return new Products(pr, promo, presentNumber / 2);
        }
        return null;
    }

    // 프로모션 미적용 품목 있음을 알림
    public List<Products> addNotPresent(List<Inventory> inventories, List<Products> buyProducts) {
        List<Products> cannotPresent = new LinkedList<>();

        for (Products product : buyProducts) {
            if (product.getPromotion() != null) {
                Inventory inv = Finder.findInventoryByName(inventories, product.getProduct().getName());
                cannotPresent.add(getCannotPresent(product, inv));
            }
        }
        return cannotPresent;
    }

    public Products getCannotPresent(Products product, Inventory inventory) {
        String promotionName = product.getPromotion().getName();
        Product pr = product.getProduct();
        Promotion promo = product.getPromotion();
        Products invStock = Finder.findProductByName(inventory.getProducts(), pr.getName());
        int notPresentNumber = product.getQuantity() - invStock.getQuantity() + casesNotPresent(product, promotionName);
        return new Products(pr, promo, notPresentNumber);
    }

    public int casesNotPresent(Products product, String promotionName) {
        if (promotionName.equals(PromotionName.SPARKLING.getName())) {
            return product.getQuantity() % 3;
        } else if (promotionName.equals(PromotionName.MD_RECOMMAND.getName())) {
            return product.getQuantity() % 2;
        } else if (promotionName.equals(PromotionName.BRIEF_DISCOUNT.getName())) {
            return product.getQuantity() % 2;
        }
        return 0;
    }

    // 프로모션 적용 가능 품목 있음을 알림
    public List<Products> addYesPresent(List<Inventory> inventories, List<Products> buyProducts) {
        List<Products> canPresent = new LinkedList<>();

        for (Products product : buyProducts) {
            if (product.getPromotion() != null) {
                Inventory inv = Finder.findInventoryByName(inventories, product.getProduct().getName());
                canPresent.add(getCanPresent(product, inv));
            }
        }
        return canPresent;
    }

    public Products getCanPresent(Products product, Inventory inventory) {
        String promotionName = product.getPromotion().getName();
        Product pr = product.getProduct();
        Promotion promo = product.getPromotion();
        Products invStock = Finder.findProductByName(inventory.getProducts(), pr.getName());
        if (invStock.getQuantity() < product.getQuantity()) {
            return null;
        }
        int yesPresentNumber = casesYesPresent(product, promotionName);
        return new Products(pr, promo, yesPresentNumber);
    }

    public int casesYesPresent(Products product, String promotionName) {
        if (promotionName.equals(PromotionName.SPARKLING.getName()) && product.getQuantity() % 3 == 2) {
            return 1;
        } else if (promotionName.equals(PromotionName.MD_RECOMMAND.getName()) && product.getQuantity() % 2 == 1) {
            return 1;
        } else if (promotionName.equals(PromotionName.BRIEF_DISCOUNT.getName()) && product.getQuantity() % 2 == 1) {
            return 1;
        }
        return 0;
    }
}
