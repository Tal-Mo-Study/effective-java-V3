package chapter_06.java.item39;

public class NameSpace {

    public static void apple_method () {
        System.out.println("apple");
    }

    public static void orange_method () {
        System.out.println("orange");
    }

    public static void main(String[] args) {
        apple_method();
        orange_method();
    }
}
