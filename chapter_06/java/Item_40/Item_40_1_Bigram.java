package chapter_06.java.Item_40;

import java.util.HashSet;
import java.util.Set;

/*
 *  Code 40-1. 영어 알파벳 2개로 구성된 문자열을 표현하는 클래스
 */
public class Item_40_1_Bigram {
    
    private final char first;
    private final char second;

    public Item_40_1_Bigram(char first, char second) {
        this.first = first;
        this.second = second;
    }

    // Case 1)
    // 재정의를 의도했다면 @Override 써야 함.
    // public boolean equals(Item_40_1_Bigram b) {
    //     return b.first == first && b.second == second;
    // }

    // // 재정의를 의도했다면 @Override 써야 함.
    // public int hashCode() {
    //     return 31 * first + second;
    // }

    // Case 2)
    // Compiler : The method equals(Bigram) of type Bigram must override or implement a supertype method
    // @Override
    // public boolean equals(Item_40_1_Bigram b) {
    //     return b.first == first && b.second == second;
    // }

    // Case 3)
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Item_40_1_Bigram))
            return false;

        Item_40_1_Bigram b = (Item_40_1_Bigram) o;

        return b.first == first && b.second == second;
    }

    @Override
    public int hashCode() {
        return 31 * first + second;
    }

    public static void main(String[] args) {
        Set<Item_40_1_Bigram> s = new HashSet<>();

        for (int i = 0; i < 10; i++)    // 같은 first, second 조합 10번씩 중복으로 알파벳 개수만큼(10 * 26)
            for (char ch = 'a'; ch <= 'z'; ch++)
                s.add(new Item_40_1_Bigram(ch, ch));
        
        System.out.println(s.size());   // Set에 들어가므로 10번의 중복 없이 <26>이 나올 것?? -> [260]
    }
}
