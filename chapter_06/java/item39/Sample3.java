package chapter_06.java.item39;

import java.util.ArrayList;
import java.util.List;

public class Sample3 {

    @RepeatableExceptionTest(IndexOutOfBoundsException.class)
    @RepeatableExceptionTest(NullPointerException.class)
    public static void doublyBad() {
        List<String> list = new ArrayList<>();

        // 다음 메서드는 IndexOutOfBoundsException을 던짐
        list.addAll(5, null);
    }
}
