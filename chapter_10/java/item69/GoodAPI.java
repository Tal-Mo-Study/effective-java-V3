package chapter_10.java.item69;

import java.util.Iterator;
import java.util.List;

public class GoodAPI {

    public static void main(String[] args) {
        goodCase();
//        badCase();
    }

    private static void goodCase() {
        for (Iterator<Integer> i = new Range(0, 3); i.hasNext();)
            System.out.println(i.next());
    }

    private static void badCase() {
        try {
            Iterator<Integer> i = new Range(0, 3);
            while (true) {
                int j = i.next();
                System.out.println(j);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
    }

    static class Range implements Iterator<Integer> {

        List<Integer> list = List.of(1, 2, 3);

        private int current;
        private final int max;

        public Range(int min, int max) {
            this.current = min;
            this.max = max;
        }

        @Override
        public boolean hasNext() {
            return current < max;
        }

        @Override
        public Integer next() {
            return list.get(current++);
        }
    }
}
