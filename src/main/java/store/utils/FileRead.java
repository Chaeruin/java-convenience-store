package store.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotion;

public class FileRead {

    public static List<Products> fileReadToInventory(List<Promotion> promotions) throws IOException {
        File aText = new File("../../resources/products.md");
        List<Products> products = new LinkedList<>();

        FileReader aReader = new FileReader(aText);
        BufferedReader br = new BufferedReader(aReader);
        String start = br.readLine();
        String input;
        while ((input = br.readLine()) != null) {
            String[] inputElement = input.split(",");
            Promotion promotion = Finder.findPromotionByName(promotions, inputElement[3]);
            Products product = new Products(new Product(inputElement[0], Integer.parseInt(inputElement[1])), promotion,
                    Integer.parseInt(inputElement[2]));
            products.add(product);
        }
        br.close();

        return products;
    }

    public static List<Promotion> fileReadToPromotion() throws IOException, ParseException {
        File aText = new File("../../resources/promotions.md");
        List<Promotion> promotions = new LinkedList<>();

        FileReader aReader = new FileReader(aText);
        BufferedReader br = new BufferedReader(aReader);
        String start = br.readLine();
        String input;
        while ((input = br.readLine()) != null) {
            String[] inputs = input.split(",");
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = sf.parse(inputs[3]);
            Date endDate = sf.parse(inputs[4]);
            Promotion promotion = new Promotion(inputs[0], Integer.parseInt(inputs[1]), Integer.parseInt(inputs[2]),
                    startDate, endDate);
            promotions.add(promotion);
        }
        br.close();

        return promotions;
    }
}
