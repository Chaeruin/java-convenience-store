package store;

import java.io.IOException;
import java.text.ParseException;
import store.controller.RecieptController;
import store.controller.StoreController;
import store.service.InventoryService;
import store.service.PromotionService;
import store.view.InputView;
import store.view.OutputView;

public class Application {
    public static void main(String[] args) throws IOException, ParseException {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        InventoryService inventoryService = new InventoryService();
        PromotionService promotionService = new PromotionService();
        RecieptController recieptController = new RecieptController(promotionService);

        StoreController storeController = new StoreController(inputView, outputView, inventoryService, promotionService,
                recieptController);

        storeController.run();
    }
}
