package chapter_07.java;

import java.math.BigInteger;
import java.util.stream.LongStream;

public class Item_48_2 {
    
    static long pi(long n) {
        
        return LongStream.rangeClosed(2, n)
                   .parallel()
                .mapToObj(BigInteger::valueOf)
                .filter(i -> i.isProbablePrime(50))
                .count();
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        System.out.println(pi(10_000_000));
        long end = System.currentTimeMillis();
        System.out.println(end- start);
    }
}
