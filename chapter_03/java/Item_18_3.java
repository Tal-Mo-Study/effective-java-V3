package chapter_03.java;

import java.util.AbstractList;
import java.util.Collection;

public class Item_18_3<E> extends AbstractList<E> {

    // 다른 방식으로 재정의
    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E e : c) {
            add(e);
        }
        // 원하는 기능은 여기까지지만, addAll 메서드는 boolean을 반환해야 하므로 return true까지 구현해야함
        if (true) throw new RuntimeException("로직이 길어질 경우 의도치 않은 예외가 발생 가능");
        return true;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public E get(int index) {
        return null;
    }
}
