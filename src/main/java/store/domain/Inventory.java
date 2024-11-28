package store.domain;

import java.util.List;

public class Inventory {
    private List<Products> products;
    private int stocks;

    public List<Products> getProducts() {
        return this.products;
    }
}
