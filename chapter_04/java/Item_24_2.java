package chapter_04.java;

public class Item_24_2 {

    private String name = "item_24_2";

    public class NoneStaticMemberClass {

        public void printName() {
            // 정규화된 this를 통해 참조 가능하다.
            // 정규화된 this란 클래스명.this 형태로 이름을 명시하는 용법
            System.out.println("name: " + Item_24_2.this.name);
        }
    }

    public NoneStaticMemberClass getNoneStaticMemberInstance() {
        return new NoneStaticMemberClass();
    }

    public static void main(String[] args) {
//        Item_24_2.NoneStaticMemberClass clazz = new Item_24_2.NoneStaticMemberClass(); // 비정적 멤버클래스는 바깥 인스턴스 없이는 생성할 수 없음
        Item_24_2 item = new Item_24_2();
        // 비정적 멤버 클래스의 인스턴스와 바깥 인스턴스 사이의 관계는 멤버 클래스가 인스턴스화 될 때 확립되며, 더 이상 변경할 수 없음
        Item_24_2.NoneStaticMemberClass clazz = item.getNoneStaticMemberInstance();
        clazz.printName();
    }

}

