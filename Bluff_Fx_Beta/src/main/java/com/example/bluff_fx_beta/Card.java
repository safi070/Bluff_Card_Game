package com.example.bluff_fx_beta;
public class Card {
    private String suit;
    private String rank;

    public Card(String rank, String suit) {
        this.suit = suit;
        this.rank = rank;
    }

    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }

    public boolean startsWith(String currentRank) {
        if(this.rank.equalsIgnoreCase(currentRank))
            return true;
        return false;
    }
}//Ct