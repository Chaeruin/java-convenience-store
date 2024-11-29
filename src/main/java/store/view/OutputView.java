package store.view;

import java.text.DecimalFormat;
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
        StringBuilder sb = new StringBuilder();
        DecimalFormat df = new DecimalFormat();
        for (Products product : inventory.getProducts()) {
            sb.append("- ").append(product.getProduct().getName()).append(" ");
            sb.append(df.format(product.getProduct().getPrice())).append("원 ");
            if (product.getQuantity() == 0) {
                sb.append("재고 없음");
            } else {
                sb.append(product.getQuantity()).append("개 ");
            }
            if (product.getPromotion() != null) {
                sb.append(product.getPromotion().getName());
            }
            System.out.println(sb);
            sb = new StringBuilder();
        }
    }

    public void printReciept(Reciept reciept, int membershipDiscount) {
        printTotal(reciept);
        printPresent(reciept);
        printResult(reciept, membershipDiscount);
    }

    public void printTotal(Reciept reciept) {
        DecimalFormat df = new DecimalFormat();
        System.out.println("===========W 편의점=============");
        System.out.println("상품명\t수량\t금액");
        for (Products bp : reciept.getBuyingProducts()) {
            System.out.println(bp.getProduct().getName() + "\t" + bp.getQuantity() + "\t" + df.format(
                    bp.getProduct().getPrice() * bp.getQuantity()));
        }
    }

    public void printPresent(Reciept reciept) {
        if (reciept.getPresentProducts().size() != 0) {
            System.out.println("===========증 정=============");
        }
        for (Products pp : reciept.getPresentProducts()) {
            System.out.println(pp.getProduct().getName() + "\t" + pp.getQuantity());
        }
    }

    public void printResult(Reciept reciept, int membershipDiscount) {
        DecimalFormat df = new DecimalFormat();
        System.out.println("==============================");
        System.out.println("총구매액\t\t" + reciept.getTotalCount() + "\t" + df.format(reciept.getTotalPrice()));
        System.out.println("행사할인\t\t" + "-" + df.format(reciept.getPresentDiscount()));
        System.out.println("멤버십할인\t\t" + "-" + df.format(membershipDiscount));
        System.out.println(
                "내실돈\t\t" + df.format(reciept.getTotalPrice() - membershipDiscount - reciept.getPresentDiscount()));
    }
}
