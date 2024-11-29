package store.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotion;

public class FileRead {

    public static List<Products> fileReadToInventory(List<Promotion> promotions) throws IOException {
        ClassLoader classLoader = FileRead.class.getClassLoader();
        File aText = new File(classLoader.getResource("products.md").getFile());
        List<Products> products = new ArrayList<>();
        FileReader aReader = new FileReader(aText);
        BufferedReader br = new BufferedReader(aReader);
        String input = br.readLine();
        while ((input = br.readLine()) != null) {
            setProducts(input, promotions, products);
        }
        br.close();
        return products;
    }

    public static void setProducts(String input, List<Promotion> promotions, List<Products> products) {
        String[] inputElement = input.split(",");
        Promotion promotion = Finder.findPromotionByName(promotions, inputElement[3]);
        Products product = new Products(new Product(inputElement[0], Integer.parseInt(inputElement[1])), promotion,
                Integer.parseInt(inputElement[2]));
        products.add(product);
    }

    public static List<Promotion> fileReadToPromotion() throws IOException, ParseException {
        ClassLoader classLoader = FileRead.class.getClassLoader();
        File aText = new File(classLoader.getResource("promotions.md").getFile());
        List<Promotion> promotions = new ArrayList<>();
        FileReader aReader = new FileReader(aText);
        BufferedReader br = new BufferedReader(aReader);
        String input = br.readLine();
        while ((input = br.readLine()) != null) {
            setPromotions(input, promotions);
        }
        br.close();
        return promotions;
    }

    public static void setPromotions(String input, List<Promotion> promotions) {
        String[] inputs = input.split(",");
        DateTimeFormatter sf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(inputs[3], sf);
        LocalDate endDate = LocalDate.parse(inputs[4], sf);
        Promotion promotion = new Promotion(inputs[0], Integer.parseInt(inputs[1]), Integer.parseInt(inputs[2]),
                startDate, endDate);
        promotions.add(promotion);
    }
}
