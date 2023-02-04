package chapter_05.java.Item_30;

import java.util.Collection;
import java.util.Objects;

public class Item_30_3 {
    /*
     *  Code 30-6. 재귀적 타입 한정을 이용해 상호 비교할 수 있음을 표현
     *             모든 타입 E는 자신과 비교할 수 있다.
     */
//    public static <E extends Comparable<E>> E max(Collection<E> c);

    /*
     *  Code 30-7. 재귀적 타입 한정을 사용하여 컬렉션에서 최댓값을 반환하는 메서드의 구현
     */
    public static <E extends Comparable<E>> E max(Collection<E> c) {
        if(c.isEmpty())
            throw new IllegalArgumentException("컬렉션이 비어 있습니다.");  // Exception보다 return Optional<E>가 낫다.
        
        E result = null;
        for (E e : c) {
            if(result == null || e.compareTo(result) > 0)
                result = Objects.requireNonNull(e);
        }

        return result;
    }

}
