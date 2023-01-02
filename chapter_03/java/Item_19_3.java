package chapter_03.java;

import java.time.Instant;

public class Item_19_3 {

    public static void main(String[] args) {
        Sub sub = new Sub();
        sub.overrideMe();
        // null
        // 2023-01-02T14:52:04.684290400Z
    }

}

final class Sub extends Super {
    // 초기화되지 않은 final 필드. 생성자에서 초기화한다.
    private final Instant instant;

    Sub() {
        instant = Instant.now();
    }

    // 재정의 가능 메서드. 생성자에서 호출한다. - 안전 실패!
    @Override
    public void overrideMe() {
        System.out.println(instant);
    }


}

class Super {
    // 잘못된 예 - 생성자가 재정의 가능 메서드를 호출한다.
    public Super() {
        overrideMe();
    }

    public void overrideMe() {
    }
}
