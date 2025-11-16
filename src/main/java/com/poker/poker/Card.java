package com.poker.poker;


public class Card
{
    private final String face; // face of card ("Ace", "Deuce", ...)
    private final String suit; // suit of card ("Hearts", "Diamonds", ...)

    // two-argument constructor initializes card's face and suit
    public Card(String face, String suit)
    {
        this.face = face;
        this.suit = suit;
    }

    @Override
    public String toString()
    {
        return suit + " _ " + face;
    }

    public String getFace() {
        return face;
    }
    public String getSuit() {

        return suit;}




} // end class Card