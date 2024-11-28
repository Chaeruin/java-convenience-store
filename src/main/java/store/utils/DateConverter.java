package store.utils;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import store.domain.Promotion;

public class DateConverter {

    public static boolean isDateInPromotions(Promotion promotion) {
        LocalDate now = LocalDate.from(DateTimes.now());
        if ((promotion.getStartDate().isAfter(now) && promotion.getEndDate().isBefore(now)) || promotion.getStartDate()
                .isEqual(now) || promotion.getEndDate().isEqual(now)) {
            return true;
        }
        return false;
    }
}
