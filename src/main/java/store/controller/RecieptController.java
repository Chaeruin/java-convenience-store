package store.controller;

import java.util.List;
import store.domain.Inventory;
import store.domain.Products;
import store.domain.Reciept;
import store.service.PromotionService;

public class RecieptController {

    final PromotionService promotionService;

    public RecieptController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    public int getMembershipDiscount(Reciept reciept, List<Inventory> inventories, List<Products> buyProducts) {
        List<Products> promoteProducts = promotionService.addToPromotions(inventories, buyProducts);
        int totalPrice = reciept.getTotalPrice();
        int eventTotal = getPromoteProductDiscount(promoteProducts);
        int membershipDiscount = (int) ((totalPrice - eventTotal) * 0.3);
        if (membershipDiscount > 8000) {
            membershipDiscount = 8000;
        }
        return membershipDiscount;
    }

    public int getPromoteProductDiscount(List<Products> promoteProducts) {
        int sum = 0;
        for (Products product : promoteProducts) {
            sum += product.getQuantity() * product.getProduct().getPrice();
        }
        return sum;
    }
}
