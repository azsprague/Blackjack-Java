package blackjack;

import java.util.ArrayList;

/**
 * This class holds the algorithm responsible for running the game.
 * 
 * @author Aidan Sprague
 * @version 2020.07.06
 */
public class BlackJack {

	private Deck deck;
	private ArrayList<Card> playerHand;
	private ArrayList<Card> AIHand;
	
	/**
	 * Constructor for the class that instantiates a new Deck to be
	 * used.
	 */
	public BlackJack() {
		playerHand = new ArrayList<>();
		AIHand = new ArrayList<>();
		deck = new Deck();
	}
	
	/**
	 * Starts a new round by dealing two cards to the player and the AI.
	 */
	public void deal() {
		playerHand.add(hit());
		playerHand.add(hit());
		
		AIHand.add(hit());
		AIHand.add(hit());
	}
	
	/**
	 * Returns the player's actual hand.
	 * @return the player's hand
	 */
	public ArrayList<Card> getPlayerHand() {
		return playerHand;
	}
	
	/**
	 * Returns the AI's hand.
	 * @return the AI's hand
	 */
	public ArrayList<Card> getAIHand() {
		return AIHand;
	}
	
	/**
	 * Deals out a single card from the top of the deck.
	 * @return the top card
	 */
	public Card hit() {
		return deck.getCard();
	}
	
	/**
	 * Discards all cards in both the player's and AI's hands.
	 */
	public void clearHands() {
		while (!playerHand.isEmpty()) {
			deck.discard(playerHand.remove(0));
		}
		
		while (!AIHand.isEmpty()) {
			deck.discard(AIHand.remove(0));
		}
	}
	
	/**
	 * Helper method that counts the total of your hand without the aces.
	 * @return the hand total without aces
	 */
	private int countNotAce(ArrayList<Card> hand) {
		int total = 0;
		for (Card card : hand) {
			if (card.getNumber() != 1) {
				total += Math.min(card.getNumber(), 10);
			}
		}
		
		return total;
	}
	
	/**
	 * Counts the total of your hand.
	 * @return the hand total
	 */
	public int count(ArrayList<Card> hand) {
		int total = countNotAce(hand);
		
		for (Card card : hand) {
			if (card.getNumber() == 1 && total < 11) {
				total += 11;
			}
			else if (card.getNumber() == 1 && total >= 11) {
				total += 1;
			}
		}
		
		return total;
	}
	
	/**
	 * Checks to see if the player's hand has gone over 21.
	 * @param hand		The hand to check
	 * @return the boolean representing if the hand is over 21
	 */
	public boolean isOver21(ArrayList<Card> hand) {
		return (count(hand) > 21);
	}
	
	/**
	 * Compares the players hand to the AI's hand. If the player has
	 * more value, return 1; if the AI does, return -1; if it's a draw,
	 * return 0.
	 */
	public int compareHands() {
		if (count(playerHand) > count(AIHand)) {
			return 1;
		}
		else if (count(playerHand) < count(AIHand)) {
			return -1;
		}
		
		return 0;
	}
	
	
	/**
	 * Allows the AI to asses how to proceed after the player has taken
	 * their turn.
	 * @return the card given to the AI
	 */
	public Card AIPlay() {
		if (count(AIHand) < 17 && count(playerHand) <= 21) {
			Card card = hit();
			AIHand.add(card);
			return card;
		}
		
		return null;
	}
	
}
