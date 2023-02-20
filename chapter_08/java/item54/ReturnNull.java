package chapter_08.java.item54;

import java.util.ArrayList;
import java.util.List;

public class ReturnNull {

    private final List<Cheese> cheesesInStock = List.of();

    public List<Cheese> getCheeses() {
        return cheesesInStock.isEmpty() ? null : new ArrayList<>(cheesesInStock);
    }

    public static void main(String[] args) {
        ReturnNull rn = new ReturnNull();
        List<Cheese> cheeses = rn.getCheeses();
        // null 체크를 깜빡하면 NPE 발생 -> 방어코드를 넣어줘야 함
        if (cheeses != null && cheeses.contains(Cheese.STILTON)) {
            System.out.println("STILTON 치즈");
        } else {
            System.out.println("STILTON 치즈 없음");
        }

        if (cheeses.contains(Cheese.CHEDDAR)) {
            System.out.println("CHEDDAR 치즈");
        }
    }
}
