package chapter_07.java.item44;

import java.util.Map;

@FunctionalInterface
public interface EldestEntryRemovalFunction<K,V> {
    boolean removeEldestEntry(Map<K,V> map, Map.Entry<K,V> eldest);
}
