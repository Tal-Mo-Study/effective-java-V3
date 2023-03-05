package chapter_07.java.Item_45;

public class Item_45_3_Test {
    public static void main(String[] args) {

        /*
         *  45_3-2
         */
        "Hello World!".chars().forEach(System.out::print);  // Hello world! 가 아닌 int값이 출력됨

        System.out.println();
        // 명시적 형변환이 필요
        "Hello World!".chars().forEach(x -> System.out.print((char) x));
    }
}
