package chapter_09.java;

import java.util.*;

public class Item_58_1 {
    
    enum Suit { CLUB, DIAMOND, HEART, SPADE}
    enum Rank { ACE, DEUCE, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, 
                NINE, TEN, JACK, QUEEN, KING}

    static Collection<Suit> suits = Arrays.asList(Suit.values());
    static Collection<Rank> ranks = Arrays.asList(Rank.values());

    static class Card {
        private final Suit suit;
        private final Rank rank;

        Card (Suit suit , Rank rank){
            this.suit = suit;
            this.rank = rank;
        }

        @Override
        public String toString() {
            return suit.toString() + " : " + rank.toString();
        }
    }

    public static void main(String[] args) {
        List<Card> deck = new ArrayList<>();
        for(Iterator<Suit> i = suits.iterator(); i.hasNext();){
            for(Iterator<Rank> j = ranks.iterator(); j.hasNext();){
                deck.add(new Card(i.next(), j.next()));
                System.out.println(deck);
            }
        }
    }
}
