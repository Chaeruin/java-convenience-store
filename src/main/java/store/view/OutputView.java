package store.view;

import java.util.List;
import store.domain.Inventory;
import store.domain.Products;
import store.domain.Reciept;

public class OutputView {

    public void printWelcome() {
        System.out.println("안녕하세요. W편의점입니다.\n"
                + "현재 보유하고 있는 상품입니다.");
    }

    public void printInvetoryStatus(List<Inventory> inventories) {
        for (Inventory inv : inventories) {
            printProducts(inv);
        }
    }

    public void printProducts(Inventory inventory) {
        for (Products product : inventory.getProducts()) {

        }
    }

    public void printReciept(Reciept reciept) {

    }
}
