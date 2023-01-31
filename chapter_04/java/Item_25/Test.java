package chapter_04.java.Item_25;

/*
 *  톱레벨 클래스들을 정적 멤버 클래스로 바꿔보기
 */
public class Test {
    public static void main(String[] args) {
        System.out.println(Utensil.NAME + Dessert.NAME);    // [pancake] 고정 출력
    }

    // Utensil, Dessert를 정적 멤버 클래스로 변경
    private static class Utensil {
        static final String NAME = "pan";
    }

    private static class Dessert {
        static final String NAME = "cake";
    }
}