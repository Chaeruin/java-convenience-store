package store.utils;

import store.enums.ErrorMessage;

public class InputValidator {
    public static boolean isProductExist(String input) {
        String[] inputs = input.replaceAll("[\\[\\]]", "").replaceAll(",", "-").split("-");
        try {
            for (int i = 0; i < inputs.length - 1; i += 2) {
                String name = inputs[i];
                int count = Integer.parseInt(inputs[i + 1]);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_FORMATTING.getErrorMessage());
        }
        return true;
    }

    public static boolean isCountZero(String input) {
        String[] inputs = input.replaceAll("[\\[\\]]", "").replaceAll(",", "-").split("-");
        for (int i = 0; i < inputs.length - 1; i += 2) {
            String name = inputs[i];
            if (name.equals("") || name.equals(" ")) {
                throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT.getErrorMessage());
            }
            int count = Integer.parseInt(inputs[i + 1]);
            if (count == 0) {
                throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT.getErrorMessage());
            }
        }
        return true;
    }

    public static boolean isYesOrNo(String input) {
        if (!(input.equals("Y") || input.equals("N"))) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT.getErrorMessage());
        }
        return true;
    }
}
