package chapter_05.java.Item_30;

import java.util.function.UnaryOperator;

/*
 *  제네릭 싱글턴 팩터리 패턴을 사용한 메서드를 가진 클래스
 */
public class Item_30_2 {
    
    private static UnaryOperator<Object> IDENTITY_FN = (t) -> t;
    
    /*
     *  Code 30-4. 제네릭 싱글턴 팩터리 패턴
     */
    @SuppressWarnings("unchecked")  // type cast warning unchecked
    public static <T> UnaryOperator<T> identityFunction() {
        return (UnaryOperator<T>) IDENTITY_FN;      // 형변환 경고 발생
    }

    /*
     *  Code 30-5. 제네릭 싱글턴을 사용하는 예
     */
    public static void main(String[] args) {
        String[] strings = {"삼베", "대마", "나일론"};
        UnaryOperator<String> sameString = identityFunction();
        for(String s : strings) 
            System.out.println(sameString.apply(s));

        Number[] numbers = {1, 2.0, 3L};
        UnaryOperator<Number> sameNumber = identityFunction();
        for(Number n : numbers) 
            System.out.println(sameNumber.apply(n));
    }
}
