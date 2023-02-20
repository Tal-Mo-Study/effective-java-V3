package chapter_06.java.item39;

import java.util.ArrayList;
import java.util.List;

public class Sample2 {
    @ExceptionTest(ArithmeticException.class)
    public static void m1() { // 성공해야 한다.
        int i = 0;
        i = i / i;
    }

    @ExceptionTest(ArithmeticException.class)
    public static void m2() { // 실패해야 한다. (다른 예외 발생)
        int[] a = new int[0];
        int i = a[1];
    }

    @ExceptionTest(ArithmeticException.class)
    public static void m3() {} // 실패해야 한다. (예외가 발생하지 않음)


    @ExceptionTest({ IndexOutOfBoundsException.class, NullPointerException.class })
    public static void doublyBad() {
        List<String> list = new ArrayList<>();

        // 다음 메서드는 IndexOutOfBoundsException을 던짐
        list.addAll(5, null);
    }
}
