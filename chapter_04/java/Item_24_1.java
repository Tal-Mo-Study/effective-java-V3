package chapter_04.java;

public class Item_24_1 {

    private static String name = "item_24_1";

    public static class StaticMemberClass {

        public void printName() {
            System.out.println("name: " + name);
        }
    }

    public static void main(String[] args) {
        Item_24_1.StaticMemberClass clazz = new Item_24_1.StaticMemberClass();
        clazz.printName(); // name: item_24_1
    }
}

