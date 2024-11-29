package store.utils;

import java.util.ArrayList;
import java.util.List;
import store.domain.Inventory;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotion;
import store.enums.ErrorMessage;

public class InputParser {

    public static List<Products> parseBuyProducts(List<Inventory> inventories, List<Products> products, String input) {
        String[] inputs = input.replaceAll("[\\[\\]]", "").replaceAll(",", "-").split("-");
        List<Products> buyProducts = new ArrayList<>();
        if (InputValidator.isProductExist(input) && InputValidator.isCountZero(input)) {
            addToList(inputs, inventories, products, buyProducts);
        }

        return buyProducts;
    }

    public static void addToList(String[] inputs, List<Inventory> inventories, List<Products> products,
                                 List<Products> buyProducts) {
        for (int i = 0; i < inputs.length - 1; i += 2) {
            throwExceptions(inputs[i], inputs[i + 1], products, inventories);
            Product pro = Finder.findProductByName(products, inputs[i]).getProduct();
            Promotion promo = Finder.findProductByName(products, inputs[i]).getPromotion();
            buyProducts.add(new Products(pro, promo, Integer.parseInt(inputs[i + 1])));
        }
    }

    public static void throwExceptions(String inputI, String inputI2, List<Products> products,
                                       List<Inventory> inventories) {
        if (Finder.findProductByName(products, inputI) == null) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_NOT_EXISTED_PRODUCT.getErrorMessage());
        }
        if (Finder.findInventoryByName(inventories, inputI).getStocks() < Integer.parseInt(inputI2)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_OVER_STOCK.getErrorMessage());
        }
    }

    public static String parseAnswer(String input) {
        if (InputValidator.isYesOrNo(input)) {
            return input;
        }
        return null;
    }
}
