package store.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import store.domain.Inventory;
import store.domain.Products;
import store.domain.Promotion;
import store.domain.Reciept;
import store.service.InventoryService;
import store.service.PromotionService;
import store.utils.FileRead;
import store.utils.Finder;
import store.utils.InputParser;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {
    final InputView inputView;
    final OutputView outputView;
    final InventoryService inventoryService;
    final PromotionService promotionService;
    final RecieptController recieptController;

    public StoreController(InputView inputView, OutputView outputView, InventoryService inventoryService,
                           PromotionService promotionService, RecieptController recieptController)
            throws IOException, ParseException {
        this.inputView = inputView;
        this.outputView = outputView;
        this.inventoryService = inventoryService;
        this.promotionService = promotionService;
        this.recieptController = recieptController;
    }

    final List<Promotion> promotions = FileRead.fileReadToPromotion();
    List<Products> products = FileRead.fileReadToInventory(promotions);
    List<Inventory> setInventory;

    public void run() {
        // 재고 세팅 (파일읽기로부터)
        setInventory = inventoryService.resettingReadInventory(products);
        String restart = "Y";
        while (restart.equals("Y")) {
            outputView.printWelcome();
            outputView.printInvetoryStatus(setInventory);
            List<Products> buyProducts = getBuyProductsHandler();
            List<Products> presentations = promotionService.addToPresent(setInventory, buyProducts);
            List<Products> cannotPresents = promotionService.addNotPresent(setInventory, buyProducts);
            List<Products> canPresents = promotionService.addYesPresent(setInventory, buyProducts);

            if (canPresents.size() != 0) {
                setPresent(buyProducts, canPresents, presentations);
            }
            if (cannotPresents.size() != 0) {
                setNotPresent(restart, cannotPresents);
            }
            String membership;
            if ((membership = getMemebershipHandler()).equals("Y")) {
                Reciept reciept = new Reciept(buyProducts, presentations, true);
                outputView.printReciept(reciept,
                        recieptController.getMembershipDiscount(reciept, setInventory, buyProducts));
            } else if (membership.equals("N")) {
                Reciept reciept = new Reciept(buyProducts, presentations, false);
                outputView.printReciept(reciept, 0);
            }

            restart = getReBuyHandler();
            setInventory = inventoryService.resettingInventoryAfterBuying(setInventory, buyProducts);
        }
    }

    public void setPresent(List<Products> buyProducts, List<Products> canPresents, List<Products> presentations) {
        for (Products prs : canPresents) {
            if (getYesMorePromotionHandler(prs).equals("Y")) {
                Products invProduct = Finder.findProductByName(buyProducts, prs.getProduct().getName());
                invProduct.setQuantity(invProduct.getQuantity() + prs.getQuantity());
                Products present = Finder.findProductByName(presentations, prs.getProduct().getName());
                present.setQuantity(present.getQuantity() + 1);
            }
        }
    }

    public void setNotPresent(String restart, List<Products> cannotPresents) {
        for (Products prs : cannotPresents) {
            if (getNoMorePromotionHandler(prs).equals("Y")) {
                continue;
            } else if (getNoMorePromotionHandler(prs).equals("N")) {
                restart = getReBuyHandler();
                break;
            }
        }
    }

    public List<Products> getBuyProductsHandler() {
        List<Products> buyProducts = null;
        while (buyProducts == null) {
            try {
                buyProducts = InputParser.parseBuyProducts(setInventory, products, inputView.getProductAndCount());
                return buyProducts;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return buyProducts;
    }

    public String getYesMorePromotionHandler(Products canPresent) {
        String input = null;
        while (input == null) {
            try {
                input = InputParser.parseAnswer(inputView.getYesMorePromotion(canPresent));
                return input;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return input;
    }

    public String getNoMorePromotionHandler(Products cannotPresent) {
        String input = null;
        while (input == null) {
            try {
                input = InputParser.parseAnswer(inputView.getNoMorePromotion(cannotPresent));
                return input;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return input;
    }

    public String getMemebershipHandler() {
        String input = null;
        while (input == null) {
            try {
                input = InputParser.parseAnswer(inputView.getMembership());
                return input;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return input;
    }

    public String getReBuyHandler() {
        String input = null;
        while (input == null) {
            try {
                input = InputParser.parseAnswer(inputView.getReBuy());
                return input;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return input;
    }

}
