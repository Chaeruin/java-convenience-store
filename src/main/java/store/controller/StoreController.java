package store.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import store.domain.Inventory;
import store.domain.Products;
import store.domain.Promotion;
import store.service.InventoryService;
import store.utils.FileRead;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {
    final InputView inputView;
    final OutputView outputView;
    final InventoryService inventoryService;

    public StoreController(InputView inputView, OutputView outputView, InventoryService inventoryService)
            throws IOException, ParseException {
        this.inputView = inputView;
        this.outputView = outputView;
        this.inventoryService = inventoryService;
    }

    final List<Promotion> promotions = FileRead.fileReadToPromotion();
    List<Products> products = FileRead.fileReadToInventory(promotions);
    List<Inventory> setInventory;

    public void run() {
        // 재고 세팅 (파일읽기로부터)
        setInventory = inventoryService.resettingReadInventory(products);

        outputView.printWelcome();

    }

    
}
