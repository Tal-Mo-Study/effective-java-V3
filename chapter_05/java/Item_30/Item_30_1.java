package chapter_05.java.Item_30;

import java.util.HashSet;
import java.util.Set;

/*
 *  두 집합의 합집합을 반환하는 메서드를 가진 클래스
 */
public class Item_30_1 {

    /*
     *  Code 30-1. 로 타입(row type) 사용 메서드 - 수용 불가!(Item 26)
     *  컴파일은 되지만 경고 발생
     */
    public static Set union(Set s1, Set s2) {
        Set result = new HashSet(s1);   // unchecked call to HashSet
        result.addAll(s2);  // unchecked call to addAll
        return result;
    }

    /*
     *  Code 30-2. 제네릭 메서드
     *             집합 3개(입력2, 반환1)의 타입이 모두 같아야 함
     */
    public static <E> Set<E> stableUnion(Set<E> s1, Set<E> s2) {
        Set<E> result = new HashSet<>(s1);
        result.addAll(s2);
        return result;
    }

    /*
     *  Code 30-3. 제네릭 메서드를 활용하는 간단한 프로그램
     *             직접 형변환 하지 않아도 컴파일 되고 사용 가능
     */
    public static void main(String[] args) {
        Set<String> guys = Set.of("톰", "딕", "해리");
        Set<String> stooges = Set.of("래리", "모에", "컬리");
        Set<String> aflCio = stableUnion(guys, stooges);
        System.out.println(aflCio);
    }
}