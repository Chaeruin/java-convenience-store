package store.service;

import java.util.List;
import store.domain.Inventory;
import store.domain.Products;

public class InventoryService {

    // 파일 입력 products to inventory  resetting service
    // 1. inventory stock setting / 프로모만 존재 일반재고 미존재 추가
    public List<Inventory> resettingReadInventory(List<Products> products) {
        return null;
    }

    // 구매 후 inventory resetting service
    public List<Inventory> resettingInventoryAfterBuying(List<Inventory> inventories, List<Products> buyList) {
        return null;
    }
}
