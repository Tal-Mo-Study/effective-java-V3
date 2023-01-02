package chapter_03.java;

import java.util.*;

public class Item_18_4 {

    public static void main(String[] args) {
        InstrumentSet<String> s = new InstrumentSet<>(new HashSet<>(16, 0.75f));
        List<String> list = List.of("Snap", "Crackle", "Pop");
        s.addAll(list);
        System.out.println("expected count is " + list.size() + ", but result count is " + s.getAddCount());
    }
}

class ForwardingSet<E> implements Set<E> {

    private final Set<E> s;

    public ForwardingSet(Set<E> s) {this.s = s;}

    @Override
    public void clear() {s.clear();}

    @Override
    public boolean contains(Object o) {return s.contains(o);}

    @Override
    public boolean isEmpty() {return s.isEmpty();}

    @Override
    public int size() {return s.size();}

    @Override
    public Iterator<E> iterator() {return null;}

    @Override
    public boolean add(E e) {
        return s.add(e);}

    @Override
    public boolean remove(Object o) {return s.remove(o);}

    @Override
    public boolean containsAll(Collection<?> c) {return s.containsAll(c);}

    @Override
    public boolean addAll(Collection<? extends E> c) {return s.addAll(c);}

    @Override
    public boolean retainAll(Collection<?> c) {return s.retainAll(c);}

    @Override
    public boolean removeAll(Collection<?> c) {return s.removeAll(c);}

    @Override
    public Object[] toArray() {return s.toArray();}

    @Override
    public <T> T[] toArray(T[] a) {return s.toArray(a);}

    @Override
    public boolean equals(Object o) {return s.equals(o);}

    @Override
    public int hashCode() {return s.hashCode();    }

    @Override
    public String toString() {return s.toString();}

}

class InstrumentSet<E> extends ForwardingSet<E> {

    private int addCount = 0;

    public InstrumentSet(Set<E> s) {
        super(s);
    }

    @Override
    public boolean add(E e) {
        addCount++;
        return super.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        addCount += c.size();
        return super.addAll(c);
    }

    public int getAddCount() {
        return addCount;
    }

}

