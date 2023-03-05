package chapter_07.java.Item_45;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

import static java.math.BigInteger.TWO;

public class Item_45_4_Example {
    
    /*
     *  스트림의 원소가 소수임을 확인하는 메서드
     */
    static Stream<BigInteger> primes() {
        return Stream.iterate(TWO, BigInteger::nextProbablePrime);
    }

    /*
     *  45-4. 데카르트 곱 계산을 반복 방식으로 구현
     */
    private static List<Card> newDeck() {
        List<Card> result = new ArrayList<>();

        for(Suit suit : Suit.values())
            for (Rank rank : Rank.values())
                result.add(new Card(suit, rank));
        
        return result;
    }

    /*
     *  45-5. 데카르트 곱 계산을 스트림 방식으로 구현
     */
    private static List<Card> newDeck2() {
        return Stream.of(Suit.values())
                    .flatMap(suit ->
                        Stream.of(Rank.values())
                            .map(rank -> new Card(suit, rank)))
                    .collect(toList());
    }

    public static void main(String[] args) {
        primes().map(p -> BigInteger.TWO.pow(p.intValueExact()).subtract(BigInteger.ONE))
                .filter(mersenne -> mersenne.isProbablePrime(50))
                .limit(20)
                .forEach(System.out::println);     // 메르센 소수 출력
                // .forEach(mp -> System.out.println(mp.bitLength() + ": " + mp)); // 소수 앞에 지수(p)를 출력하고 싶은 경우

    }    
}

// Value Mapping은 생략
enum Rank {
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE,
    TEN,
    J,
    Q,
    K,
    A
}

enum Suit {
    SPADE,
    DIAMOND,
    HEART,
    CLOVER
}

class Card {
    Suit suit;
    Rank rank;
    
    Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }
}

