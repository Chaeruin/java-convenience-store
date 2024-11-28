package store.domain;

import java.util.List;

public class Reciept {
    private final List<Products> buyingProducts;
    private final List<Products> presentProducts;
    private final boolean isMembership;

    public Reciept(List<Products> buyingProducts, List<Products> presentProducts, boolean isMembership) {
        this.buyingProducts = buyingProducts;
        this.presentProducts = presentProducts;
        this.isMembership = isMembership;
    }

    public List<Products> getBuyingProducts() {
        return this.buyingProducts;
    }

    public List<Products> getPresentProducts() {
        return this.presentProducts;
    }

    /// 가격계산 여기서 처리
}
