package store.enums;

public enum PromotionName {
    SPARKLING("탄산2+1"),
    MD_RECOMMAND("MD추천상품"),
    BRIEF_DISCOUNT("반짝할인");

    private final String name;

    PromotionName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
