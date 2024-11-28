package store.enums;

public enum ErrorMessage {

    INVALID_INPUT("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요."),
    INVALID_OVER_STOCK("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    INVALID_NOT_EXISTED_PRODUCT("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요."),
    INVALID_FORMATTING("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");

    private final String errorMessage;

    ErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }
}
