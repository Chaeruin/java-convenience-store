package store.utils;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import store.domain.Promotion;

public class DateConverter {

    public static boolean isDateInPromotions(Promotion promotion) {
        LocalDate now = LocalDate.from(DateTimes.now());
        if (promotion == null) {
            return false;
        }
        if ((now.isAfter(promotion.getStartDate()) && now.isBefore(promotion.getEndDate())) ||
                now.isEqual(promotion.getStartDate()) ||
                now.isEqual(promotion.getEndDate())) {
            return true;
        }
        return false;
    }
}
