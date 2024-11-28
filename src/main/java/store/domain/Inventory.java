package store.domain;

import java.util.List;

public class Inventory {
    private List<Products> products;
    private int stocks;

    public Inventory(List<Products> products, int stocks) {
        this.products = products;
        this.stocks = stocks;
    }

    public List<Products> getProducts() {
        return this.products;
    }

    public int getStocks() {
        return this.stocks;
    }

    public void setStocks(int stocks) {
        this.stocks = stocks;
    }

    public int getAllQuantities() {
        int sum = 0;
        for (Products product : products) {
            sum += product.getQuantity();
        }
        return sum;
    }
}
