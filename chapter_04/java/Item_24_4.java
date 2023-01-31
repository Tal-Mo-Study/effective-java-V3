package chapter_04.java;

public class Item_24_4 {

    public static void main(String[] args) {
        class LocalInnerClass {
            public void printA() {
                System.out.println("LocalInnerClass A");
            }

            public void printB() {
                System.out.println("LocalInnerClass B");
            }
        }

        // 이름이 존재
        LocalInnerClass localInnerClass = new LocalInnerClass();
        // 반복해서 사용가능
        localInnerClass.printA();
        localInnerClass.printB();
    }

}

