package store.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import store.domain.Inventory;
import store.domain.Product;
import store.domain.Products;
import store.utils.Finder;

public class InventoryService {

    // 파일 입력 products to inventory  resetting service
    // 1. inventory stock setting / 프로모만 존재 일반재고 미존재 추가
    public List<Inventory> resettingReadInventory(List<Products> products) {
        List<Inventory> inventories = new ArrayList<>();
        for (Products product : products) {
            addToInvetoryProduct(inventories, product);
        }
        for (Inventory inventory : inventories) {
            if (inventory.getProducts().size() == 1) {
                addToNullProduct(inventory);
            }
        }
        return inventories;
    }

    public void addToInvetoryProduct(List<Inventory> inventories, Products product) {
        // 같은 이름의 상품이 존재하는 경우
        Inventory sameInv;
        if ((sameInv = isExistInventory(inventories, product.getProduct().getName())) != null) {
            sameInv.getProducts().add(product);
            sameInv.setStocks(sameInv.getAllQuantities());
        } else {
            List<Products> pr = new LinkedList<>();
            pr.add(product);
            inventories.add(new Inventory(pr, product.getQuantity()));
        }
    }

    public void addToNullProduct(Inventory inventory) {
        for (Products product : inventory.getProducts()) {
            if (product.getPromotion() != null) {
                Product pr = product.getProduct();
                inventory.getProducts().add(new Products(pr, null, 0));
                break;
            }
        }
    }

    public Inventory isExistInventory(List<Inventory> inventories, String name) {
        for (Inventory inventory : inventories) {
            if (Finder.findProductByName(inventory.getProducts(), name) != null) {
                return inventory;
            }
        }
        return null;
    }

    // 구매 후 inventory resetting service
    public List<Inventory> resettingInventoryAfterBuying(List<Inventory> inventories, List<Products> buyList) {
        for (Inventory inventory : inventories) {
            findProductAtInventoryAndSetting(inventory, buyList);
        }
        return inventories;
    }

    public void findProductAtInventoryAndSetting(Inventory inventory, List<Products> buyList) {
        for (Products product : buyList) {
            Products findProduct = Finder.findProductByName(inventory.getProducts(), product.getProduct().getName());
            if (findProduct != null) {
                setConditionalStocks(inventory, product, findProduct);
            }
        }
    }

    public void setConditionalStocks(Inventory inventory, Products product, Products findProduct) {
        inventory.setStocks(inventory.getStocks() - product.getQuantity());
        if (product.getQuantity() > findProduct.getQuantity()) {
            Products findNullPromotionProduct = Finder.findNullPromotionProduct(inventory.getProducts(), findProduct);
            findNullPromotionProduct.setQuantity(findNullPromotionProduct.getQuantity() - (product.getQuantity()
                    - findProduct.getQuantity()));
            findProduct.setQuantity(0);
        } else if (product.getQuantity() <= findProduct.getQuantity()) {
            findProduct.setQuantity(findProduct.getQuantity() - product.getQuantity());
        }
    }
}
