package store.utils;

import java.util.LinkedList;
import java.util.List;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotion;
import store.enums.ErrorMessage;

public class InputParser {

    public List<Products> parseBuyProducts(List<Products> products, String input) {
        String[] inputs = input.replaceAll("[\\\\[\\\\]]", "").replaceAll(",", "-").split("-");
        List<Products> buyProducts = new LinkedList<>();
        if (InputValidator.isProductExist(input) && InputValidator.isCountZero(input)) {
            addToList(inputs, products, buyProducts);
        }

        return buyProducts;
    }

    public void addToList(String[] inputs, List<Products> products, List<Products> buyProducts) {
        for (int i = 0; i < inputs.length; i += 2) {
            if (Finder.findProductByName(products, inputs[i]) == null) {
                throw new IllegalArgumentException(ErrorMessage.INVALID_NOT_EXISTED_PRODUCT.getErrorMessage());
            }
            Product pro = Finder.findProductByName(products, inputs[i]).getProduct();
            Promotion promo = Finder.findProductByName(products, inputs[i]).getPromotion();
            buyProducts.add(new Products(pro, promo, Integer.parseInt(inputs[i + 1])));
        }
    }

    public String parseAnswer(String input) {
        if (InputValidator.isYesOrNo(input)) {
            return input;
        }
        return null;
    }
}
