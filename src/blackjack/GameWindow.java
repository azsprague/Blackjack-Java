package blackjack;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class is responsible for the GUI setup and game representation.
 * 
 * @author Aidan Sprague
 * @version 2020.07.06
 */
public class GameWindow implements ActionListener {

	private JButton playButton;
	private JButton quitButton;
	private JButton hitButton;
	private JButton stayButton;
	private JButton playAgainButton;
	private JLabel title;
	private JLabel playerText;
	private JLabel AIText;
	private JFrame frame;
	private JPanel textPanel;
	private JPanel buttonPanel;
	private JPanel masterPanel;
	private JPanel playerCards;
	private JPanel AICards;
	
	private Image backCard;
	
	private BlackJack game;
	
	/**
	 * Constructor for the class that sets up the majority of the GUI.
	 */
	public GameWindow() {
		game = new BlackJack();
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame = new JFrame("BlackJack");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		playButton = new JButton("Play!");
		playButton.addActionListener(this);
		quitButton = new JButton("Quit");
		quitButton.addActionListener(this);
		hitButton = new JButton("Hit!");
		hitButton.addActionListener(this);
		stayButton = new JButton("Stay");
		stayButton.addActionListener(this);
		playAgainButton = new JButton("Play Again");
		playAgainButton.addActionListener(this);
		
		title = new JLabel("BlackJack", JLabel.CENTER);
		title.setFont(new Font("Verdana", Font.ITALIC, 30));
		title.setPreferredSize(new Dimension(250, 100));
		title.setForeground(Color.WHITE);
		
		playerText = new JLabel("Your Hand:     ", JLabel.CENTER);
		playerText.setFont(new Font("Veranda", Font.BOLD, 20));
		
		AIText = new JLabel("Dealer's Hand:", JLabel.CENTER);
		AIText.setFont(new Font("Veranda", Font.BOLD, 20));
		
		Color background = new Color(66, 189, 36);
		
		FlowLayout fLayout = new FlowLayout(FlowLayout.LEFT);
		fLayout.setHgap(0);
		
		textPanel = new JPanel();
		FlowLayout textLayout = new FlowLayout();
		textLayout.setHgap(60);
		textPanel.setLayout(textLayout);
		textPanel.setBackground(background);
		textPanel.add(title);
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.setBackground(background);
		buttonPanel.add(playButton);
		buttonPanel.add(quitButton);
		
		backCard = null;
		try {
		    backCard = ImageIO.read(new File("Cards/red_back.png"));
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		
		JLabel pic = new JLabel(new ImageIcon(backCard));
		pic.setVisible(false);
		
		playerCards = new JPanel();
		playerCards.setLayout(fLayout);
		playerCards.setBackground(background);
		playerCards.add(pic);
		AICards = new JPanel();
		AICards.setLayout(fLayout);
		AICards.setBackground(background);
		AICards.add(pic);
		
		masterPanel = new JPanel();
		BoxLayout bLayout = new BoxLayout(masterPanel, BoxLayout.Y_AXIS);
		masterPanel.setLayout(bLayout);
		masterPanel.setBackground(background);
		masterPanel.add(textPanel);
		masterPanel.add(playerCards);
		masterPanel.add(AICards);
		masterPanel.add(buttonPanel);
		
		frame.add(masterPanel);
		frame.setSize(1400, 750);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(false);
	}
	
	/**
	 * Starts a game when the play button is pressed.
	 */
	private void newGame() {
		game.deal();
		
		JLabel hidden = new JLabel(new ImageIcon(backCard));
		
		playerCards.add(playerText);
		Image playerCard1 = getImage(game.getPlayerHand().get(0));
		Image playerCard2 = getImage(game.getPlayerHand().get(1));
		JLabel playerPic1 = new JLabel(new ImageIcon(playerCard1));
		JLabel playerPic2 = new JLabel(new ImageIcon(playerCard2));
		playerCards.add(playerPic1);
		playerCards.add(playerPic2);
		
		AICards.add(AIText);
		AICards.add(hidden);
		Image AICard = getImage(game.getAIHand().get(1));
		JLabel AIPic = new JLabel(new ImageIcon(AICard));
		AICards.add(AIPic);
		
		buttonPanel.removeAll();
		buttonPanel.add(hitButton);
		buttonPanel.add(stayButton);
		
		hitButton.setEnabled(true);
		stayButton.setEnabled(true);
		
		checkBlackJack();

		frame.revalidate();
		frame.repaint();
	}
	
	/**
	 * Helper method that retrieves the image associated with a card.
	 */
	private Image getImage(Card card) {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("Cards/" + card.getNumber() + card.getSuit() + ".png"));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}

		return img;
	}
	
	/**
	 * Adds a new Card to the screen if the player presses hit.
	 */
	private void hit() {
		if (!game.isOver21(game.getPlayerHand())) {
			Card newCard = game.hit();
			game.getPlayerHand().add(newCard);
			
			JLabel pic = new JLabel(new ImageIcon(getImage(newCard)));
			playerCards.add(pic);
		}
		
		checkBusted();
		checkBlackJack();
		
		frame.revalidate();
		frame.repaint();
	}
	
	/**
	 * Disables the hit button and displays a message if the player has
	 * busted (gone over 21).
	 */
	private void checkBusted() {
		if (game.isOver21(game.getPlayerHand())) {
			JLabel bustedText = new JLabel("BUSTED", JLabel.CENTER);
			bustedText.setFont(new Font("Verdana", Font.BOLD, 50));
			playerCards.add(bustedText);
			
			JLabel winText = new JLabel("Dealer Wins!", JLabel.CENTER);
			winText.setFont(new Font("Verdana", Font.PLAIN, 50));
			AICards.add(winText);
			
			buttonPanel.removeAll();
			buttonPanel.add(playAgainButton);
			buttonPanel.add(quitButton);
		}
	}
	
	/**
	 * Checks to see if the player has a blackjack.
	 */
	private void checkBlackJack() {
		if (game.count(game.getPlayerHand()) == 21) {
			hitButton.setEnabled(false);
			
			JLabel blackJackText = new JLabel("BlackJack!    ", JLabel.CENTER);
			blackJackText.setFont(new Font("Verdana", Font.BOLD, 50));
			blackJackText.setForeground(Color.WHITE);
			playerCards.add(blackJackText);
		}
	}
	
	/**
	 * Allows the AI to take its turn if the player presses stay.
	 */
	private void stay() {		
		Thread thread = new Thread(() -> {
			Card newCard = game.AIPlay();
			if (newCard != null) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				JLabel pic = new JLabel(new ImageIcon(getImage(newCard)));
				AICards.add(pic);
				frame.revalidate();
				frame.repaint();
				
				stay();
			}
			else {
				announceWinner();
			}
		});
		thread.start();
	}
	
	/**
	 * Flips over the dealer's hidden card.
	 */
	private void flip() {
		AICards.removeAll();
		Image AICard1 = getImage(game.getAIHand().get(0));
		Image AICard2 = getImage(game.getAIHand().get(1));
		JLabel AIPic1 = new JLabel(new ImageIcon(AICard1));
		JLabel AIPic2 = new JLabel(new ImageIcon(AICard2));
		AICards.add(AIText);
		AICards.add(AIPic1);
		AICards.add(AIPic2);
		
		frame.revalidate();
		frame.repaint();
	}
	
	/**
	 * Announces the winner after the user has pressed stay.
	 */
	private void announceWinner() {
		JLabel winText = new JLabel("You Win!", JLabel.CENTER);
		winText.setFont(new Font("Verdana", Font.PLAIN, 50));
		
		if (game.isOver21(game.getAIHand())) {
			JLabel bustedText = new JLabel("BUSTED", JLabel.CENTER);
			bustedText.setFont(new Font("Verdana", Font.BOLD, 50));
			AICards.add(bustedText);
			
			playerCards.add(winText);
		}
		else if (game.compareHands() == 1) {
			playerCards.add(winText);
		}
		else if (game.compareHands() == 0) {
			JLabel drawText = new JLabel("Draw!", JLabel.CENTER);
			drawText.setFont(new Font("Verdana", Font.PLAIN, 50));
			playerCards.add(drawText);
		}
		else {
			JLabel loseText = new JLabel("Dealer Wins!", JLabel.CENTER);
			loseText.setFont(new Font("Verdana", Font.PLAIN, 50));
			AICards.add(loseText);
		}
		
		buttonPanel.removeAll();
		buttonPanel.add(playAgainButton);
		buttonPanel.add(quitButton);
		
		frame.revalidate();
		frame.repaint();
	}
	
	/**
	 * Discards each hand and starts a new game.
	 */
	private void playAgain() {
		game.clearHands();
		
		AICards.removeAll();
		playerCards.removeAll();
		
		newGame();
	}

	/**
	 * Keeps track of all button presses and calls the necessary methods
	 * needed.
	 * @param e		The action event that is triggered
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(playButton)) {
			newGame();
		}
		else if (e.getSource().equals(quitButton)) {
			System.exit(0);
		}
		else if (e.getSource().equals(hitButton)) {
			hit();
		}
		else if (e.getSource().equals(stayButton)) {
			hitButton.setEnabled(false);
			stayButton.setEnabled(false);
			
			flip();
			stay();
		}
		else if (e.getSource().equals(playAgainButton)) {
			playAgain();
		}
	}
	
}
