package chapter_03.java;

import java.util.AbstractList;
import java.util.Collection;

public class Item_18_2<E> extends AbstractList<E> {

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for (E e : c)
            if (add(e))
                modified = true;
        return modified;
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
