package chapter_08.java.item54;

import java.util.List;

import static java.util.Arrays.stream;

public class ReturnEmptyArray {

    private final List<Cheese> cheesesInStock = List.of(Cheese.STILTON, Cheese.CHEDDAR, Cheese.BRIE);

    private final Cheese[] EMPTY_CHEESE_ARRAY = new Cheese[0];

    public Cheese[] getCheeses() {
        return cheesesInStock.toArray(EMPTY_CHEESE_ARRAY);
    }

    public static void main(String[] args) {
        ReturnEmptyArray rn = new ReturnEmptyArray();
        Cheese[] cheeses = rn.getCheeses();
        // null 체크를 깜빡해도 안전함
        if (stream(cheeses).anyMatch(cheese -> cheese == Cheese.STILTON)) {
            System.out.println("좋았어, 바로 그거야.");
        }
    }

}
