package chapter_06.java.item34;

public class IntegerEnum {

    public static final int APPLE_FUJI = 0;

    public static final int APPLE_PIPPIN = 1;

    public static final int APPLE_GRANNY_SMITH = 2;

    public static final int ORANGE_NAVEL = 0;

    public static final int ORANGE_TEMPLE = 1;

    public static final int ORANGE_BLOOD = 2;

    public static void main(String[] args) {
        int i = (APPLE_FUJI - ORANGE_NAVEL) / APPLE_PIPPIN;
        // 정수 열거 패턴과 열거 타입 출력 차이
        System.out.println("APPLE : " + i);
        System.out.println(Planet.MERCURY);
    }

}
