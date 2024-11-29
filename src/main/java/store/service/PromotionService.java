package store.service;

import java.util.ArrayList;
import java.util.List;
import store.domain.Inventory;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotion;
import store.enums.PromotionName;
import store.utils.DateConverter;
import store.utils.Finder;

public class PromotionService {

    // addToPromotions
    public List<Products> addToPromotions(List<Inventory> inventories, List<Products> buyProducts) {
        List<Products> promoteProduct = new ArrayList<>();

        for (Products product : buyProducts) {
            addToPromoteProduct(product, inventories, promoteProduct);
        }
        return promoteProduct;
    }

    public void addToPromoteProduct(Products product, List<Inventory> inventories, List<Products> promoteProduct) {
        if (product.getPromotion() != null && DateConverter.isDateInPromotions(product.getPromotion())) {
            Inventory inv = Finder.findInventoryByName(inventories, product.getProduct().getName());
            promoteProduct.add(getPromote(product, inv));
        }
    }

    public Products getPromote(Products product, Inventory inventory) {
        String promotionName = product.getPromotion().getName();
        Product pr = product.getProduct();
        Promotion promo = product.getPromotion();
        Products invStock = Finder.findProductByName(inventory.getProducts(), pr.getName());
        int presentNumber = product.getQuantity();
        if (invStock.getQuantity() < product.getQuantity()) {
            presentNumber = invStock.getQuantity();
        }
        return casesPromote(promotionName, pr, promo, presentNumber);
    }

    public Products casesPromote(String promotionName, Product pr, Promotion promo,
                                 int presentNumber) {
        if (promotionName.equals(PromotionName.SPARKLING.getName())) {
            return new Products(pr, promo, (presentNumber / 3) * 3);
        } else if (promotionName.equals(PromotionName.MD_RECOMMAND.getName())) {
            return new Products(pr, promo, (presentNumber / 2) * 2);
        } else if (promotionName.equals(PromotionName.BRIEF_DISCOUNT.getName())) {
            return new Products(pr, promo, (presentNumber / 2) * 2);
        }
        return null;
    }


    // 프로모션 적용 품목 증정 리스트 추가
    public List<Products> addToPresent(List<Inventory> inventories, List<Products> buyProducts) {
        List<Products> presentations = new ArrayList<>();

        for (Products product : buyProducts) {
            addToPresentations(product, inventories, presentations);
        }
        return presentations;
    }

    public void addToPresentations(Products product, List<Inventory> inventories, List<Products> presentations) {
        if (product.getPromotion() != null && DateConverter.isDateInPromotions(product.getPromotion())) {
            Inventory inv = Finder.findInventoryByName(inventories, product.getProduct().getName());
            presentations.add(getPresent(product, inv));
        }
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
        List<Products> cannotPresent = new ArrayList<>();

        for (Products product : buyProducts) {
            addToCannotPresent(product, inventories, cannotPresent);
        }
        return cannotPresent;
    }

    public void addToCannotPresent(Products product, List<Inventory> inventories, List<Products> cannotPresent) {
        if (product.getPromotion() != null && DateConverter.isDateInPromotions(product.getPromotion())) {
            Inventory inv = Finder.findInventoryByName(inventories, product.getProduct().getName());
            Products products = getCannotPresent(product, inv);
            if (products != null) {
                cannotPresent.add(products);
            }
        }
    }

    public Products getCannotPresent(Products product, Inventory inventory) {
        String promotionName = product.getPromotion().getName();
        Product pr = product.getProduct();
        Promotion promo = product.getPromotion();
        Products invStock = Finder.findProductByName(inventory.getProducts(), pr.getName());
        int notPresentNumber = getNotPresentNumber(product, promotionName, invStock);
        if (product.getQuantity() < invStock.getQuantity()) {
            return null;
        }
        return new Products(pr, promo, notPresentNumber);
    }

    public int getNotPresentNumber(Products product, String promotionName, Products invStock) {
        int notPresentNumber = casesNotPresent(product, promotionName);
        if (product.getQuantity() > invStock.getQuantity()) {
            notPresentNumber = product.getQuantity() - casesPresentNotYet(invStock, promotionName);
        }
        return notPresentNumber;
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

    public int casesPresentNotYet(Products invStock, String promotionName) {
        if (promotionName.equals(PromotionName.SPARKLING.getName())) {
            return (invStock.getQuantity() / 3) * 3;
        } else if (promotionName.equals(PromotionName.MD_RECOMMAND.getName())) {
            return (invStock.getQuantity() / 2) * 2;
        } else if (promotionName.equals(PromotionName.BRIEF_DISCOUNT.getName())) {
            return (invStock.getQuantity() / 2) * 2;
        }
        return 0;
    }

    // 프로모션 적용 가능 품목 있음을 알림
    public List<Products> addYesPresent(List<Inventory> inventories, List<Products> buyProducts) {
        List<Products> canPresent = new ArrayList<>();

        for (Products product : buyProducts) {
            addToCanPresent(product, inventories, canPresent);
        }
        return canPresent;
    }

    public void addToCanPresent(Products product, List<Inventory> inventories, List<Products> canPresent) {
        if (product.getPromotion() != null && DateConverter.isDateInPromotions(product.getPromotion())) {
            Inventory inv = Finder.findInventoryByName(inventories, product.getProduct().getName());
            Products products = getCanPresent(product, inv);
            if (products != null) {
                canPresent.add(products);
            }
        }
    }

    public Products getCanPresent(Products product, Inventory inventory) {
        String promotionName = product.getPromotion().getName();
        Product pr = product.getProduct();
        Promotion promo = product.getPromotion();
        Products invStock = Finder.findProductByName(inventory.getProducts(), pr.getName());
        int yesPresentNumber = 0;
        if (invStock.getQuantity() <= product.getQuantity()
                || (yesPresentNumber = casesYesPresent(product, promotionName)) == 0) {
            return null;
        }
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
