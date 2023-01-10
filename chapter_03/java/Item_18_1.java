package chapter_03.java;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class Item_18_1<E> extends HashSet<E> {

    // 추가된 원소의 수
    private int addCount = 0;

    public Item_18_1(int initCap, float loadFactor) {
        super(initCap, loadFactor);
    }

    @Override
    public boolean add(E e) {
        addCount++;
        return super.add(e);
    }

    // HashSet.addAll 메서드는 this.add 메서드를 호출하기 때문에 override된 Instruemnted.add 메서드가 대신 호출되고 addCount가 2번 증가한다.
    @Override
    public boolean addAll(Collection<? extends E> c) {
        addCount += c.size();
        return super.addAll(c);
    }

    public int getAddCount() {
        return addCount;
    }

    public static void main(String[] args) {
        Item_18_1<String> s = new Item_18_1<>(16, 0.75f);
        List<String> list = List.of("Snap", "Crackle", "Pop");
        s.addAll(list);
        System.out.println("expected count is " + list.size() + ", but result count is " + s.getAddCount());
    }
}
