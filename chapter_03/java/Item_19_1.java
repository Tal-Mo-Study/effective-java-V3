package chapter_03.java;

import java.util.*;

public class Item_19_1<E> extends AbstractCollection<E> {

    /**
     * {@inheritDoc}
     *
     *@implSpec
     * This implementation iterates over the collection looking for the
     * specified element.  If it finds the element, it removes the element
     * from the collection using the iterator's remove method.
     *
     *<p>Note that this implementation throws an
     * {@codeUnsupportedOperationException} if the iterator returned by this
     * collection's iterator method does not implement the {@coderemove}
     * method and this collection contains the specified object.
     *
     *@throwsUnsupportedOperationException {@inheritDoc}
     *@throwsClassCastException            {@inheritDoc}
     *@throwsNullPointerException          {@inheritDoc}
     */
    public boolean remove(Object o) {
        Iterator<E> it = iterator();
        if (o==null) {
            while (it.hasNext()) {
                if (it.next()==null) {
                    it.remove();
                    return true;
                }
            }
        } else {
            while (it.hasNext()) {
                if (o.equals(it.next())) {
                    it.remove();
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }
}