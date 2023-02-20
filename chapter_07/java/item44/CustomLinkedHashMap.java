package chapter_07.java.item44;

import java.util.LinkedHashMap;
import java.util.Map;

public class CustomLinkedHashMap<K,V> extends LinkedHashMap<K,V> {

    @Override
    protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
        return size() > 100;
    }

    protected boolean remove(EldestEntryRemovalFunction<K,V> function) {
        Map.Entry<K,V> eldest = this.entrySet().iterator().next();
        boolean result = function.removeEldestEntry(this, eldest);
        if (result) {
            this.remove(eldest.getKey());
        }
        return result;
    }

    public static void main(String[] args) {
//        example1();

        example2();
    }

    private static void example1() {
        CustomLinkedHashMap<String, Integer> map = new CustomLinkedHashMap<>();
        for (int i = 0; i < 110; i++) {
            map.put("key" + i, i);
        }

        System.out.println("map : " + map);
        System.out.println(map.size());
    }

    private static void example2() {
        CustomLinkedHashMap<String, Integer> map2 = new CustomLinkedHashMap<>();
        for (int i = 0; i < 110; i++) {
            map2.put("key" + i, i);
            if (map2.remove((m, e) -> m.size() > 100)) {
                System.out.println("Removed eldest entry");
            }
        }

        System.out.println("map2 : " + map2);
        System.out.println(map2.size());
    }

}
