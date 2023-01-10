package chapter_03.java;

import java.util.ArrayList;
import java.util.Collections;

public class Item_19_2 {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(4);
        list.add(2);
        list.add(3);
        list.add(5);

        ChildAscList ascList = new ChildAscList(list);
        ascList.printAll();
        System.out.println();
        ChildDescList descList = new ChildDescList(list);
        descList.printAll();
        // 1 2 3 4 5
        // 5 4 3 2 1
    }
}

abstract class ParentList {

    private final ArrayList<Integer> list;

    public ParentList(ArrayList<Integer> list) {
        this.list = list;
    }

    public void printAll() {
        sort();
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i) + " ");
        }
    }

    abstract protected void sort();
}

class ChildAscList extends ParentList {

    private final ArrayList<Integer> list;

    public ChildAscList(ArrayList<Integer> list) {
        super(list);
        this.list = list;
    }

    @Override
    protected void sort() {
        Collections.sort(list);
    }

}

class ChildDescList extends ParentList {

    private final ArrayList<Integer> list;

    public ChildDescList(ArrayList<Integer> list) {
        super(list);
        this.list = list;
    }

    @Override
    protected void sort() {
        Collections.sort(list, Collections.reverseOrder());
    }

}