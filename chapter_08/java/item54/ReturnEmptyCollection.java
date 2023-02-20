package chapter_08.java.item54;

import java.util.ArrayList;
import java.util.List;

public class ReturnEmptyCollection {

    private final List<Cheese> cheesesInStock = List.of(Cheese.STILTON, Cheese.CHEDDAR, Cheese.BRIE);

    public List<Cheese> getCheeses() {
        return new ArrayList<>(cheesesInStock);
    }

    public static void main(String[] args) {
        ReturnEmptyCollection rn = new ReturnEmptyCollection();
        List<Cheese> cheeses = rn.getCheeses();
        // null 체크를 깜빡해도 안전함
        if (cheeses.contains(Cheese.STILTON)) {
            System.out.println("좋았어, 바로 그거야.");
        }
    }
}
