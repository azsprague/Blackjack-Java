package blackjack;

/**
 * This class holds information about the individual card, including
 * its number and suit.
 * 
 * @author Aidan Sprague
 * @version 2020.07.06
 */
public class Card {
	
	private String suit;
	private int number;
	
	/**
	 * Constructor for the class containing the following parameters:
	 * @param suit		The suit of the card
	 * @param number	The number of the card
	 * 
	 * Note: for ease of use, a '1' indicates an ace, a '11' indicates
	 * 		 a jack, '12' a queen, and '13' a king.
	 */
	public Card(int number, String suit) {
		this.number = number;
		this.suit = suit;
	}
	
	/**
	 * Returns the suit of the card.
	 * @return the suit
	 */
	public String getSuit() {
		return suit;
	}
	
	/**
	 * Returns the number of the card.
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}
	
	/**
	 * Returns the string representation of the card.
	 * @return the card as a string
	 */
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		switch (number) {
			case 1: 
				builder.append("A");
				break;
			case 11: 
				builder.append("J");
				break;
			case 12: 
				builder.append("Q");
				break;
			case 13: 
				builder.append("K");
				break;
			default: 
				builder.append(number);
		}
		
		builder.append(suit);
		return builder.toString();
	}

}
