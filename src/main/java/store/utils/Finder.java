package store.utils;

import java.util.List;
import store.domain.Promotion;
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
}
