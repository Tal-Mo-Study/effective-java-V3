package chapter_10.java.item69;

import java.util.Arrays;

public class InvalidException {

    private static int[] range = new int[10];

    public static void main(String[] args) {
        try {
            int i = 0;
            while (true) {
                range[i] = i;
                i++;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(Arrays.toString(range));
            System.out.println(e.getMessage());
        }
    }
}
