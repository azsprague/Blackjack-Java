package blackjack;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class holds the information regarding what cards have been dealt,
 * and which cards remain.
 * 
 * @author Aidan Sprague
 * @version 2020.07.06
 */
public class Deck {

	private ArrayList<Card> cards;
	private ArrayList<Card> discardPile;
	
	/**
	 * Constructor for the class that creates a full deck of 52 cards.
	 */
	public Deck() {
		cards = new ArrayList<>();
		discardPile = new ArrayList<>();
		createNewDeck();
	}
	
	/**
	 * Helper method responsible for creating a new deck.
	 */
	private void createNewDeck() {
		for (int i = 1; i < 14; i++) {
			cards.add(new Card(i, "H"));
		}
		
		for (int i = 1; i < 14; i++) {
			cards.add(new Card(i, "D"));
		}
		
		for (int i = 1; i < 14; i++) {
			cards.add(new Card(i, "S"));
		}
		
		for (int i = 1; i < 14; i++) {
			cards.add(new Card(i, "C"));
		}
		
		shuffle();
	}
	
	/**
	 * Returns the size of the deck.
	 * @return the size
	 */
	public int getSize() {
		return cards.size();
	}
	
	/**
	 * Returns the size of the discard pile.
	 * @return the size
	 */
	public int getDiscardSize() {
		return discardPile.size();
	}
	
	/**
	 * Removes and returns the top card of the deck. If the deck is empty,
	 * it re-shuffles the discard pile and uses it as the deck.
	 * @return the top card.
	 */
	public Card getCard() {
		if (cards.isEmpty()) {
			reShuffle();
		}
		return cards.remove(0);
	}
	
	/**
	 * Helper method to re-shuffle the discard pile and use as the deck.
	 */
	private void reShuffle() {
		for (int i = 0; i < discardPile.size(); i++) {
			Card temp = discardPile.get(i);
			cards.add(temp);
		}
		discardPile.clear();
		shuffle();
	}
	
	/**
	 * Adds a card to the discard pile.
	 * @param card		The card to discard
	 */
	public void discard(Card card) {
		discardPile.add(card);
	}
	
	/**
	 * Shuffles the deck pseudo-randomly using the Java Collections class.
	 */
	public void shuffle() {
		Collections.shuffle(cards);
	}
	
	/**
	 * Shows the entire deck.
	 * @return the string representation of every card in the deck.
	 */
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < cards.size(); i++) {
			builder.append(cards.get(i).toString() + "\n");
		}
		
		return builder.toString();
	}
	
}
