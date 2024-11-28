package store.dto;

import java.time.LocalDate;
import store.domain.Promotion;

public record PromotionStatus(
        String name,
        int buy,
        int get,
        LocalDate startDate,
        LocalDate endDate
) {
    public static PromotionStatus of(final Promotion promotion) {
        return new PromotionStatus(
                promotion.getName(),
                promotion.getBuy(),
                promotion.getGet(),
                promotion.getStartDate(),
                promotion.getEndDate()
        );
    }
}
