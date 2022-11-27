// Shreya Ray
// 4-11-22
// BiologyBall.java
// This program is a game where you get to shoot a ball into a goal and
// answer questions about biology if you score. The goal of the game is
// to answer as many questions as possible and refresh your biology skills
// along the way.

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import javax.swing.JFrame;	
import javax.swing.JPanel;
import javax.swing.BorderFactory;

import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.Timer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.io.File;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

public class BiologyBall
{
	public static void main(String[] args) 
	{
		BiologyBall bb = new BiologyBall();
		bb.run();
	}
	
	public void run()
	{
		JFrame frame = new JFrame("BIOLOGY BALL");
		frame.setSize(960, 650);
		frame.setLocation(100, 50);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		BBCardHolder hold = new BBCardHolder();
		frame.getContentPane().add(hold);
		frame.setVisible(true);
	}
}

// This is the card holder, it holds all the panels, like the instructions
// page, the leaderboard, and the home page. 
class BBCardHolder extends JPanel
{
	private CardLayout cards; // card layout, so we can go to next card.
							  // sent in as parameters to each constructor
							  // because each panel needs to go to a page
	public BBCardHolder()
	{
		cards = new CardLayout();
		setLayout(cards);
		
		GameInfo gi = new GameInfo();
		
		InstructionsPanel ip = new InstructionsPanel(this, cards);
		StartGamePanel sgp = new StartGamePanel(this, cards, ip);
		add(sgp, "Start game");
		add(ip, "Instructions");
		BoardPanel bp = new BoardPanel(this, cards, gi, ip);
		add(bp, "Leaderboard");
		ChooseTopicPanel ctp = new ChooseTopicPanel(this, cards, ip);
		add(ctp, "Choose topic");
		QuestionPanel qp = new QuestionPanel(gi, cards, this, ctp);
		add(qp, "Questions");
		GamePlayPanel gpp = new GamePlayPanel(this, cards, qp);
		add(gpp, "Game play");
		GameOverPanel gop = new GameOverPanel(this, cards);
		add(gop, "Game over");
		EnterNamePanel enp = new EnterNamePanel(this, cards, gi, qp);
		add(enp, "Enter name");
	}
}
 
// This is the title page, which is the first card in the stack. It has 
// BB1CardHolder's card layout so it can go to the necessary card when you
// press the button leading you there. The layout is null, and it has buttons
// a menu and a JLabel. It also loads an image for the background.
class StartGamePanel extends JPanel
{
	private BBCardHolder panelCards; // card holder, used in both handler
									  // classes so we can access the cards
	private CardLayout cards; // card layout, also used in both handlers
	private Image picture; // var for Image to be drawn, used in reading
						   // the image and drawing it
	private String pictName; // name of image to be drawn, used in try-catch
							 // and paintComponent
	private InstructionsPanel ip; // instance of instructions panel class,
								  // used for overloaded constructor and 
								  // menu handler
	
	public StartGamePanel(BBCardHolder panelCardsIn, CardLayout cardsIn,
		InstructionsPanel ipIn)
	{
		panelCards = panelCardsIn;
		cards = cardsIn;
		ip = ipIn;
		pictName = "bioBackground.jpg";
		
		getMyImage();
		repaint();
		
		setLayout(null);
		Font titleFont = new Font("Times", Font.PLAIN, 100);
		Font font = new Font("Serif", Font.PLAIN, 30);
		
		// title looks different on school comp than on mac
		JLabel title = new JLabel("Biology Ball");
		title.setFont(titleFont);
		title.setOpaque(true);
		title.setBackground(Color.WHITE);
		title.setBounds(220, 100, 515, 150);
		//title.setBounds(220, 100, 570, 150);
		add(title);
		
		JMenuBar bar = new JMenuBar();
		JMenu menu = new JMenu("menu");
		menu.setFont(font);
		JMenuItem homePage = new JMenuItem("home");
		JMenuItem instructions = new JMenuItem("how to play");
		JMenuItem highScores = new JMenuItem("leaderboard");
		
		MenuHandler mh = new MenuHandler();
		homePage.addActionListener(mh);
		instructions.addActionListener(mh);
		highScores.addActionListener(mh);
		
		menu.add(homePage);
		menu.add(instructions);
		menu.add(highScores);
		bar.add(menu);
		
		bar.setBounds(870, 7, 79, 40);
		add(bar);
		
		JButton start = new JButton("START");
		ButtonHandler bh = new ButtonHandler();
		start.addActionListener(bh);
		start.setFont(font);
		start.setBounds(400, 350, 150, 50);
		add(start);
		
		JButton exit = new JButton("EXIT");
		exit.addActionListener(bh);
		exit.setFont(font);
		exit.setBounds(7, 7, 105, 40);
		add(exit);
	}
	
	public void getMyImage() 
	{
		File pictFile = new File(pictName);
		try
		{
			picture = ImageIO.read(pictFile);
		}
		catch(IOException e)
		{
			System.err.println("\n\n" + pictName + " can't be found. \n\n");
			e.printStackTrace();
		}
	}
	
	public void paintComponent(Graphics g)
	{
		g.drawImage(picture, 0, 0, 960, 650, this);
	}
	
	// This is the listener for the menu, and has utilizes the field vars
	// panelCards (card holder) and cards (cardLayout) to go to the specified
	// next page.
	class MenuHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			if(command.equals("home"))
				cards.show(panelCards, "Start game");
			else if(command.equals("how to play"))
			{
				ip.displayButton(false);
				cards.show(panelCards, "Instructions");
			}
			else if(command.equals("leaderboard"))
				cards.show(panelCards, "Leaderboard");	
		}
	}
	
	// This is the listener for the button and does essentially the same
	// thing as the menu, except it also uses System.exit to leave the program
	// when the proper button is pressed.
	class ButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			if(command.equals("START"))
				cards.show(panelCards, "Choose topic");
			if(command.equals("EXIT"))
				System.exit(1);
		}
	}
}

// This is the instructions page which is accessed through the menu. It 
// also has a menu to either take you back to the home page or to the 
// scoreboard. It has a textArea where the instructions are written.
class InstructionsPanel extends JPanel
{
	private BBCardHolder panelCards; // card holder, used in the menu handler
									  // to access all necessary cards
	private CardLayout cards; // card layout, so we can go to next card
	//private JPanel taPanel;
	
	public InstructionsPanel(BBCardHolder panelCardsIn, CardLayout cardsIn)
	{
		panelCards = panelCardsIn;
		cards = cardsIn;
		setLayout(new BorderLayout());
		Font taFont = new Font("Tahoma", Font.PLAIN, 30);
		Font menuFont = new Font("Serif", Font.PLAIN, 30);
		
		JTextArea ta = new JTextArea("\nHow to play:\n\n\nWelcome to Biology Ball!"
			+ " The goal of the game is to answer as many questions as possible "
			+ "over the course of 30 seconds. To get a question, you have to score"
			+ " a soccer ball in the goal. To position the ball, use the two side"
			+ " arrow keys to move it left and right. The ball shoots straight "
			+ " forwards, and you can shoot it by pressing the space key. Or "
			+ "the up arrow. Have fun!");
		ta.setMargin(new Insets(10, 20, 10, 10));
		ta.setLineWrap(true);
		ta.setWrapStyleWord(true);
		ta.setEditable(false);
		ta.setFont(taFont);
		ta.setBackground(Color.PINK);
		add(ta, BorderLayout.CENTER);
		
		JPanel menuPanel = new JPanel();
		add(menuPanel, BorderLayout.EAST);
		menuPanel.setBackground(Color.PINK);
		
		JMenuBar bar = new JMenuBar();
		JMenu menu = new JMenu("menu");
		menu.setFont(menuFont);
		JMenuItem homePage = new JMenuItem("home");
		JMenuItem instructions = new JMenuItem("how to play");
		JMenuItem highScores = new JMenuItem("leaderboard");
		
		MenuHandler mh = new MenuHandler();
		homePage.addActionListener(mh);
		instructions.addActionListener(mh);
		highScores.addActionListener(mh);
		
		menu.add(homePage);
		menu.add(instructions);
		menu.add(highScores);
		bar.add(menu);
	
		menuPanel.add(bar);
	}
	
	// Listener for the menu, this is the same listener as the previous 
	// class, had to make multiple listeners instead of using the same for
	// all because classes are not nested.
	class MenuHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			if(command.equals("home"))
				cards.show(panelCards, "Start game");
			else if(command.equals("how to play"))
				cards.show(panelCards, "Instructions");
			else if(command.equals("leaderboard"))
				cards.show(panelCards, "Leaderboard");	
		}
	}
	
	// This is a method called from all panels that can be taken to this
	// one. It decides whether the back button should be displayed or not.
	// The back button should only be displayed when this method is called
	// from ChooseTopicPanel.
	public void displayButton(boolean check)
	{	
		ButtonHandler bh = new ButtonHandler();
		
		JButton back = new JButton("Back");
		back.setFont(new Font("Tahoma", Font.PLAIN, 50));
		back.addActionListener(bh);
		add(back, BorderLayout.SOUTH);
		
		if(!check)
			remove(back);
	}
	
	// This is the handler class for the back button. It is added in the
	// display button method but is only used if that method was called
	// from ChooseTopicPanel.
	class ButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			if(command.equals("Back"))
				cards.show(panelCards, "Choose topic");
		}
	}
}

// This is the leaderboard panel which is also accessed through the menu.
// The scores are stored in a text file and printed here. A text area 
// displays the scores
class BoardPanel extends JPanel
{
	private BBCardHolder panelCards; // card holder, used in the menu handler
									  // to access all necessary cards
	private CardLayout cards; // card layout, so we can go to next card
	private GameInfo info; // instance of GameInfo class, it is used to
						   // get the information for the leaderboard. It
						   // it is the overloaded constructor and paint
						   // component.
	
	private JTextArea ta; // this is the text area that describes the
						  // instructions for how to play. It is used in
						  // the constructor and paint component, where it
						  // has to set the text to the appropriate scores.
	private InstructionsPanel ip; // instance of InstructionsPanel class,
								  // it is sent into the overloaded const
								  // and is used in the menu handler class.
	
	public BoardPanel(BBCardHolder panelCardsIn, CardLayout cardsIn, 
		GameInfo infoIn, InstructionsPanel ipIn)
	{
		panelCards = panelCardsIn;
		cards = cardsIn;
		info = infoIn;
		ip = ipIn;
		Font taFont = new Font("Courier", Font.PLAIN, 30);
		Font menuFont = new Font("Serif", Font.PLAIN, 30);
		
		setLayout(new BorderLayout());
		
		ta = new JTextArea("Leaderboard set here");
		ta.setLineWrap(true);
		ta.setWrapStyleWord(true);
		ta.setEditable(false);
		ta.setFont(taFont);
		ta.setBackground(Color.GREEN);
		add(ta, BorderLayout.CENTER);
		
		
		JPanel menuPanel = new JPanel();
		add(menuPanel, BorderLayout.EAST);
		menuPanel.setBackground(Color.GREEN);
		
		JMenuBar bar = new JMenuBar();
		JMenu menu = new JMenu("menu");
		menu.setFont(menuFont);
		JMenuItem homePage = new JMenuItem("home");
		JMenuItem instructions = new JMenuItem("how to play");
		JMenuItem highScores = new JMenuItem("leaderboard");
		
		MenuHandler mh = new MenuHandler();
		homePage.addActionListener(mh);
		instructions.addActionListener(mh);
		highScores.addActionListener(mh);
		
		menu.add(homePage);
		menu.add(instructions);
		menu.add(highScores);
		bar.add(menu);
	
		menuPanel.add(bar);
	}
	
	// It gets the text for the scoreboard by calling a method from the
	// GameInfo class and uses substring to make it just the top five
	// scores. Then it sets that as the text for the text area.
	public void paintComponent(Graphics g)
	{
		info.scoreboard(false);
		String text = info.printScores();
		text = text.substring(0, text.indexOf("6."));
		ta.setText("\n  Leaderboard\n\n" + text);
	}
	
	// Listener for menu, same as previous panels.
	class MenuHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			if(command.equals("home"))
				cards.show(panelCards, "Start game");
			else if(command.equals("how to play"))
			{
				ip.displayButton(false);
				cards.show(panelCards, "Instructions");
			}
			else if(command.equals("leaderboard"))
				cards.show(panelCards, "Leaderboard");	
		}
	}
}

// This is the panel where you can choose what subject questions you want.
// The radio buttons are in a button group so the user can only pick one
// subject. It does not allow the user to continue until they pick a button.
class ChooseTopicPanel extends JPanel
{
	private BBCardHolder panelCards; // card holder, used in both the radio
									  // button handler and the button handler
									  // to access the cards 
	private CardLayout cards; // card layout, so we can go to next card, used
							  // in both handlers
	private JRadioButton gen; // * choices for topics, used for isSelected()
	private JRadioButton cellBio; // *
	private JRadioButton physio; // *
	private int val; // used to check which radio button is chosen
	private ButtonGroup bg; // used in constructor to make sure only one
							// button is chosen at a time, and used in the
							// ButtonHandler class to clear selection.
	private InstructionsPanel ip; // instance of InstructionsPanel class,
								  // overloads the constructor and is used
								  // in the ButtonHandler class to call
								  // a method in that class to add a back
								  // button to that panel.
	
	public ChooseTopicPanel(BBCardHolder panelCardsIn, CardLayout cardsIn,
		InstructionsPanel ipIn)
	{
		panelCards = panelCardsIn;
		cards = cardsIn;
		ip = ipIn;
		val = 0;
		Font font = new Font("Tahoma", Font.PLAIN, 50);
		Font font2 = new Font("Serif", Font.BOLD, 30);
		
		setLayout(new GridLayout(3, 1));
		JPanel labelPanel = new JPanel();
		add(labelPanel);
		
		labelPanel.setBackground(Color.CYAN);
		labelPanel.setLayout(null);
		JLabel title = new JLabel("Choose a topic");
		title.setFont(font);
		title.setBounds(315, 10, 360, 60);
		labelPanel.add(title);
		
		ButtonHandler bh = new ButtonHandler();
		
		JButton instructions = new JButton("Instructions");
		instructions.setFont(new Font("Tahoma", Font.PLAIN, 20));
		instructions.addActionListener(bh);
		instructions.setBounds(800, 20, 150, 40);
		labelPanel.add(instructions);
		
		JPanel buttonPanel = new JPanel();
		add(buttonPanel);
		buttonPanel.setLayout(new GridLayout(1, 3));
		
		JPanel b1Panel = new JPanel();
		JPanel b2Panel = new JPanel();
		JPanel b3Panel = new JPanel();
		
		buttonPanel.add(b1Panel);
		buttonPanel.add(b2Panel);
		buttonPanel.add(b3Panel);
		
		b1Panel.setBackground(Color.CYAN);
		b2Panel.setBackground(Color.CYAN);
		b3Panel.setBackground(Color.CYAN);
		
		bg = new ButtonGroup();
		gen = new JRadioButton("Genetics");
		cellBio = new JRadioButton("Cell Biology");
		physio = new JRadioButton("Physiology");
		
		RButtonHandler rbh = new RButtonHandler();
		gen.addActionListener(rbh);
		cellBio.addActionListener(rbh);
		physio.addActionListener(rbh);
		
		gen.setFont(font2);
		cellBio.setFont(font2);
		physio.setFont(font2);
		
		gen.setBackground(Color.CYAN);
		cellBio.setBackground(Color.CYAN);
		physio.setBackground(Color.CYAN);
		
		bg.add(gen);
		bg.add(cellBio);
		bg.add(physio);
		
		b1Panel.add(gen);
		b2Panel.add(cellBio);
		b3Panel.add(physio);
		
		JPanel contPanel = new JPanel();
		add(contPanel);
		contPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
		contPanel.setBackground(Color.CYAN);
		
		JButton back = new JButton("Back");
		back.setFont(font);
		back.addActionListener(bh);
		contPanel.add(back);
		
		JButton cont = new JButton("Continue");
		cont.setFont(font);
		cont.addActionListener(bh);
		contPanel.add(cont);
	}
	
	// handler for radio buttons, uses isSelected to figure out which button
	// was picked.
	class RButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			if(gen.isSelected())
				val = 1;
			else if(cellBio.isSelected())
				val = 2;
			else if(physio.isSelected())
				val = 3;
		}
	}
	
	// getter method for the int val, which tells us which topic was chosen
	// this is utilized in QuestionPanel, where it is calling one of the 
	// methods in the class GameInfo and sending this in as a parameter
	public int getVal()
	{
		return val;
	}
	
	// handler for buttons, uses getActionCommand to see which button was
	// pressed and execute the proper response.
	class ButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String command = "";
			command = evt.getActionCommand();
			if(command.equals("Continue") && val > 0 && val < 4)
				cards.show(panelCards, "Game play");
			if(command.equals("Back"))
				cards.show(panelCards, "Start game");
			if(command.equals("Instructions"))
			{
				ip.displayButton(true);
				cards.show(panelCards, "Instructions");
			}
			bg.clearSelection();
		}
	}
}

// This is the game play panel, where the actual soccer game is played. 
// It implements both ActionListener and KeyListener. The action listener
// is used for the two radio buttons we use to either start the game or 
// quit the game, and it is also used for the two timers we use (countdown
// and mover timers). The key listener is used to read when the user clicks
// the arrow keys to move the ball (keyPressed, and the space bar to shoot
// it (keyTyped).
class GamePlayPanel extends JPanel implements ActionListener, KeyListener
{
	private BBCardHolder panelCards; // card holder, only used in home
									  // handler to go back to home page
	private CardLayout cards; // card layout, so we can go to needed card
	
	private Image picture; // var for Image to be drawn, used in pc and try-catch
	private String pictName; // name of image to be drawn, pc & try-catch
	
	private Timer timer; // var for timer, used in constructor and actionPerformed
	private Timer ballTimer; // var for mover timer, used in const and ap
	private int time; // var for amt of time on timer, used in const, ap, & pc
	private int tenthSec; // ** used in constructor and paintComponent 
	private int elapsedSeconds; // **
	private int elapsedMinutes; // **
	private int seconds; // **
	private int secondsDisplay; // **
	private JRadioButton start; // used in const and handler for isSelected
	private JRadioButton home; // used in const and handler for isSelected
	private JTextArea message; // used to display either a starting message,
							   // or the words goal or miss. Used in const
							   // and actionPerformed
	
	private int xCoor; // x of circle, used in timer handler and pc
	private int yCoor; // x of circle, used in timer handler and pc
	private int val; // used to see if start button was pressed, all handlers
	private char keychar; // used to see if space button was pressed, keyTyped and ap
	
	private boolean next; // used to make the page go to questions panel
						  // after the ball is scored while having some
						  // delay. It is primarily used in actionPerformed
						  // but it needs to be initialized in the const.
	private boolean clicked; // used in the keyPressed method and action
							 // Performed to figure out whether the up 
							 // arrow was pressed.
	private int code; // used to see what the keyCode is in methods other
					  // than keyPressed, like actionPerformed
	
	private ButtonGroup bg; // used in constructor and actionPerformed
	private QuestionPanel qp; // instance of QuestionPanel class, used to
							  // reset question in actionPerformed. It is
							  // also in the overloaded constructor
	
	public GamePlayPanel(BBCardHolder panelCardsIn, CardLayout cardsIn, 
		QuestionPanel qpIn)
	{
		qp = qpIn;
		
		xCoor = (int)(Math.random()*851+10);
		yCoor = 500;
		
		addKeyListener(this);
		panelCards = panelCardsIn;
		cards = cardsIn;
		setLayout(null);
		
		next = false;
		
		setBackground(Color.WHITE);
		pictName = new String("SoccerField.jpg");
		getMyImage();
		
		
		timer = new Timer(1000, this);
		time = 30;
		tenthSec = 0;
		elapsedSeconds = 0;
		elapsedMinutes = 0;
		seconds = 0;
		
		start = new JRadioButton("START");
		start.setFont(new Font("Serif", Font.BOLD, 25));
		start.addActionListener(this);
		start.setBackground(Color.LIGHT_GRAY);
		bg = new ButtonGroup();
		bg.add(start);
		start.setBounds(10, 10, 120, 35);
		add(start);
		
		ballTimer = new Timer(2, this);
		
		home = new JRadioButton("HOME");
		home.setFont(new Font("Serif", Font.BOLD, 25));
		home.addActionListener(this);
		home.setBackground(Color.LIGHT_GRAY);
		bg.add(home);
		home.setBounds(830, 10, 120, 35);
		add(home);
		
		message = new JTextArea("PRESS START TO BEGIN");
		message.setOpaque(false);
		message.setForeground(Color.RED);
		message.setFont(new Font("Serif", Font.BOLD, 50));
		message.setBounds(175, 200, 600, 100);
		add(message);
	}
	
	// uses a try-catch block to load a picture for the background of the
	// game play panel
	public void getMyImage() 
	{
		File pictFile = new File(pictName);
		try
		{
			picture = ImageIO.read(pictFile);
		}
		catch(IOException e)
		{
			System.err.println("\n\n" + pictName + " can't be found. \n\n");
			e.printStackTrace();
		}
	}
	
	// This method does most of its function only when the start button
	// is selected. Once the button is selected, the countdown timer is
	// able to start, allowing the user to begin playing the game. When
	// the user presses the space key (determined by a var from keyTyped)
	// , the mover timer can start moving the ball forwards, and either
	// scoring in the goal or missing. If the user scores, it goes to the
	// next page. If the user misses, it just resets.
	public void actionPerformed(ActionEvent evt)
	{
		if(start.isSelected())
		{
			if(next == true)
			{
				cards.show(panelCards, "Questions");
				qp.resetQuestion();
				next = false;
				yCoor = 500;
				xCoor = (int)(Math.random()*851+10);
			}
			message.setText("");
			val = 1;
			timer.start();
			if(evt.getSource() == timer)
			{
				time--;
				if(time < 0)
				{
					timer.stop();
					cards.show(panelCards, "Game over");
					time = 30;
					val = 0;
					bg.clearSelection();
					message.setText("PRESS START TO BEGIN");
					message.setBounds(175, 200, 600, 100);
				}
			}
			if(keychar == ' ' || code == KeyEvent.VK_UP)
			{
				ballTimer.start();
				yCoor--;
				if(yCoor == 380)
				{
					keychar = '!';
					code = -1;
					ballTimer.stop();
					if(xCoor >= 321 && xCoor <= 525)
					{
						message.setBounds(xCoor - 20, yCoor - 20, 200, 50);
						message.setText("GOAL!");
						next = true;
						clicked = false;
					}
					else
					{
						message.setBounds(xCoor - 20, yCoor - 20, 150, 50);
						message.setText("MISS!");
						yCoor = 500;
						xCoor = (int)(Math.random()*851+10);
						clicked = false;
					}
				}
			}
		}
		if(home.isSelected())
		{
			bg.clearSelection();
			time = 30;
			val = 0;
			cards.show(panelCards, "Start game");
		}
		repaint();
	}
	
	// checks to see which arrow was pressed, and moves the ball in the
	// corresponding direction by changing its x coordinates
	public void keyPressed(KeyEvent evt)
	{
		if(val == 1)
		{
			if(keychar != ' ')
			{
				code = evt.getKeyCode();
				if(code == KeyEvent.VK_UP)
					clicked = true;
				if(code == KeyEvent.VK_RIGHT)
				{
					if(!clicked)
					{
						xCoor += 20;
						if(xCoor > 855)
							xCoor = 855;
					}
					else
						code = KeyEvent.VK_UP;
				}
				else if(code == KeyEvent.VK_LEFT)
				{
					if(!clicked)
					{
						xCoor -= 20;
						if(xCoor < 0)
							xCoor = 0;
					}
					else
						code = KeyEvent.VK_UP;
				}
				else
					code = KeyEvent.VK_UP;
				repaint();
			}
		}
	}
	
	// checks to see if the space key was typed
	public void keyTyped(KeyEvent evt)
	{
		if(val == 1)
			keychar = evt.getKeyChar();
	}
	
	// draws the ball and the countdown timer
	public void paintComponent(Graphics g)
	{	
		requestFocusInWindow();
		super.paintComponent(g);
		g.drawImage(picture, 0, 50, 960, 600, this);
		
		g.drawOval(xCoor, yCoor, 100, 100);
		g.setColor(Color.WHITE);
		g.fillOval(xCoor, yCoor, 100, 100);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Tahoma", Font.BOLD, 30));
		
		seconds = time - tenthSec / 10;
		secondsDisplay = seconds % 60; 	
		elapsedMinutes = seconds / 60;
    		
		g.drawString(elapsedMinutes + ": " + String.format("%d", secondsDisplay), 440, 35);
		g.setColor(Color.BLACK);
	}
	
	public void keyReleased(KeyEvent evt){}
}

class QuestionPanel extends JPanel implements ActionListener
{
	private GameInfo info; // needed to call the file reader method and 
						   // the question and answer getter methods
	private CardLayout cards; // needed to go back to the game play page
							  // when the continue button has been pressed
	private BBCardHolder panelCards; // card holder, needed same as cardLayout
	private ButtonGroup group; // So you can only click one button at a 
							   // time, in actionPerformed method
	private JTextArea questionArea; // displays the question, needed in 
									// reset question method
	private JRadioButton [] answer; // holds different answers for 1 
									// question which is got from getter
									// method, used in actionPerformed
									// and resetQuestion
	private JButton submit; // button to submit answer, used in actionPerformed
	private JButton next; // used to continue, used in actionPerformed
	
	private ChooseTopicPanel ctp; // used to get the val from ChooseTopicPanel
								  // to send to GameInfo, used in resetQuestion
	private int myScore = 0; // used in actionPerformed, its getter setter
							 // methods getScore and setScore, and in reset()
	
	public QuestionPanel(GameInfo infoIn, CardLayout cardsIn, BBCardHolder panelCardsIn,
		ChooseTopicPanel ctpIn)
	{
		info = infoIn;
		cards = cardsIn;
		panelCards = panelCardsIn;
		ctp = ctpIn;
		
		
		setBackground(Color.BLACK);
		setLayout(new BorderLayout(10, 10));
		Font myFont = new Font("Tahoma", Font.BOLD, 22);
		
		answer = new JRadioButton[4];
		for(int i=0; i<4; i++)
			answer[i] = new JRadioButton("");
		JPanel question = new JPanel();
		question.setBackground(Color.CYAN);
		question.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
		question.setLayout(new BorderLayout());
		add(question, BorderLayout.NORTH);
		
		questionArea = new JTextArea("", 3, 30);
		questionArea.setFont(new Font("Tahoma", Font.BOLD, 26));
		questionArea.setLineWrap(true);
		questionArea.setWrapStyleWord(true);
		questionArea.setOpaque(false);
		questionArea.setEditable(false);

		question.add(questionArea, BorderLayout.SOUTH);
		resetQuestion();
		
		JPanel answers = new JPanel();
		answers.setBackground(Color.BLUE);
		answers.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		answers.setLayout(new GridLayout(2, 2, 20, 20));
		add(answers, BorderLayout.CENTER);
		
		group = new ButtonGroup();
		
		for(int i = 0; i < answer.length; i++)
		{
			answer[i] = new JRadioButton("" + (char)(65 + i) + ". " + info.getAnswer(i));
			group.add(answer[i]);
			answer[i].setOpaque(true);
			answer[i].setBackground(new Color(230, 230, 230));
			answer[i].setFont(myFont);
			answer[i].addActionListener(this);

			answers.add(answer[i]);
		}
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.CYAN);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 30));
		add(buttonPanel, BorderLayout.SOUTH);
		
		submit = new JButton("SUBMIT");
		submit.setFont(myFont);
		submit.addActionListener(this);
		submit.setEnabled(false);
		submit.setBackground(Color.BLUE);
		submit.setForeground(Color.CYAN);
		buttonPanel.add(submit);
		
		next = new JButton("CONTINUE");
		next.setFont(myFont);
		next.addActionListener(this);
		next.setEnabled(false);
		next.setBackground(Color.BLUE);
		next.setForeground(Color.CYAN);
		buttonPanel.add(next);
		
	}
	
	// decides whether the answer you picked was right or wrong using the
	// array value from GameInfo, turns background green if right, red if not
	public void actionPerformed(ActionEvent evt) 
	{
		String command = evt.getActionCommand();
		
		if(group.getSelection() != null)
			submit.setEnabled(true);
		
		if(command.equals("SUBMIT"))
		{	
			answer[info.getCorrectAnswer()].setBackground(Color.GREEN);
			for(int i = 0; i < answer.length; i++)
			{
				if(answer[i].isSelected())
				{
					if(i != info.getCorrectAnswer())
						answer[i].setBackground(Color.RED);
					else
					{
						myScore += 1;
						setScore(myScore);
					}
				}
			}
			group.clearSelection();
			for(int i = 0; i < answer.length; i++)
			{
				answer[i].setEnabled(false);
			}
			submit.setEnabled(false);
			next.setEnabled(true);
		}
		else
			next.setEnabled(false);
		
		if(command.equals("CONTINUE"))
		{
			cards.show(panelCards, "Game play");
		}
	}
	
	// gets the question and answers and sets them to their respective
	// text areas and radio buttons
	public void resetQuestion ( )
	{
		info.grabQuestionFromFile(ctp.getVal());
		questionArea.setText(info.getQuestion());
		answer[0].setText("A. " + info.getAnswer(0));
		answer[1].setText("B. " + info.getAnswer(1));
		answer[2].setText("C. " + info.getAnswer(2));
		answer[3].setText("D. " + info.getAnswer(3));
		for(int i = 0; i < answer.length; i++)
		{
			answer[i].setEnabled(true);
			answer[i].setBackground(new Color(230, 230, 230));
		}
	}
	
	// the setter method for myScore, it is called from actionPerformed
	// of this class, and the getter method in this class, getScore().
	public void setScore(int updatedScore)
	{
		myScore = updatedScore;
		getScore();
	}
	
	// the getter method for myScore, the value is sent to GameInfo, where
	// it has its own setter method.
	public int getScore()
	{
		info.setScore(myScore);
		return myScore;
	}
	
	// this method resets the score so each new time a user begins a round,
	// the score resets and doesn't add up.
	public void reset()
	{
		myScore = 0;
	}
}

// This panel is shown after the time has ended and the game is over. It
// displays a simple red screen showing you a message that the game has
// ended, and there is one button that takes to the next page, where you
// enter your name to be added to the list of scores.
class GameOverPanel extends JPanel implements ActionListener
{
	private BBCardHolder panelCards; // this is the card holder which is
									 // sent in through the constructor,
									 // it is used in the actionPerformed
									 // method.
	private CardLayout cards; // This is the card layout which is used in
							  // actionPerformed to allow the next panel
							  // to be displayed.
	
	public GameOverPanel(BBCardHolder panelCardsIn, CardLayout cardsIn)
	{
		panelCards = panelCardsIn;
		cards = cardsIn;
		
		setLayout(null);
		setBackground(Color.RED);
	
		JTextArea label = new JTextArea("  TIME'S UP!\nGAME OVER");
		label.setFont(new Font("Serif", Font.BOLD, 100));
		label.setForeground(Color.WHITE);
		label.setBackground(Color.RED);
		label.setEditable(false);
		label.setBounds(150, 200, 630, 310);
		add(label);
		
		JButton next = new JButton("next");
		next.setFont(new Font("Serif", Font.PLAIN, 50));
		next.addActionListener(this);
		next.setBounds(420, 500, 150, 50);
		add(next);
	}
	
	// it used the evt.getActionCommand method to figure out whether the
	// button was pressed, and if it was it uses the card holder/layout
	// to move to the next page.
	public void actionPerformed(ActionEvent evt)
	{
		String command = evt.getActionCommand();
		if(command.equals("next"))
			cards.show(panelCards, "Enter name");
	}
}

// this panel has a text field where you are able to type your name and
// add it to the list of scores. Once a name has been typed into the 
// text field, the list is diplayed using a scroll pane. There is also a
// button to lead you back to the home page.
class EnterNamePanel extends JPanel implements ActionListener
{
	private BBCardHolder panelCards; // used in the constructor and the 
									 // actionPerformed method
	private CardLayout cards; // used in the constructor and the action
							  // Performed method
	private GameInfo info; // sent in by overloading the constructor, used
						   // in actionPerformed to gather data on the 
						   // scores and print that data in a text area.
	private QuestionPanel qp; // instance of question panel class, used in
							  // the actionPerformed method of the Button
							  // Handler class to call the reset method,
							  // which resets the score
	private JLabel score; // This label displays what your score was, it
						  // is used in the constructor and paintComponent
	private JTextArea printScores; // This is a text area that displays
								   // the scores using a string from a 
								   // getter method in the GameInfo class.
								   // It is used in the constructor and 
								   // actionPerformed.
	
	private JTextField name;
	
	public EnterNamePanel(BBCardHolder panelCardsIn, CardLayout cardsIn, 
		GameInfo infoIn, QuestionPanel qpIn)
	{
		panelCards = panelCardsIn;
		cards = cardsIn;
		info = infoIn;
		qp = qpIn;
		
		setLayout(new BorderLayout());
		
		JPanel left = new JPanel();
		left.setPreferredSize(new Dimension(400, 650));
		left.setLayout(new GridLayout(3, 1));
		add(left, BorderLayout.WEST);
		
		JPanel top = new JPanel();
		top.setBackground(Color.YELLOW);
		left.add(top);
		score = new JLabel("Your score was: ");
		score.setFont(new Font("Serif", Font.BOLD, 40));
		top.add(score);
		
		JPanel middle = new JPanel();
		middle.setBackground(Color.YELLOW);
		left.add(middle);
		JLabel enter = new JLabel("Enter your name:");
		enter.setFont(new Font("Serif", Font.BOLD, 25));
		middle.add(enter);
		
		name = new JTextField("", 10);
		name.addActionListener(this);
		middle.add(name);
		
		JTextArea ta = new JTextArea("Your name will be added to the list"
			+ " of scores and if it is in the top 5, it will be added to"
			+ " the leaderboard");
		ta.setMargin(new Insets(10, 10, 10, 10));
		ta.setLineWrap(true);
		ta.setWrapStyleWord(true);
		ta.setEditable(false);
		ta.setBackground(Color.YELLOW);
		ta.setFont(new Font("Serif", Font.BOLD, 25));
		left.add(ta);
		
		JPanel right = new JPanel();
		right.setPreferredSize(new Dimension(560, 650));
		right.setBackground(Color.YELLOW);
		add(right, BorderLayout.EAST);
		
		printScores = new JTextArea("", 20, 35);
		printScores.setEditable(false);
		printScores.setBackground(Color.YELLOW);
		printScores.setFont(new Font("Courier", Font.BOLD, 25));
		JScrollPane scroll = new JScrollPane(printScores);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		right.add(scroll);
		
		ButtonHandler bh = new ButtonHandler();
		
		JButton home = new JButton("home");
		home.setFont(new Font("Serif", Font.PLAIN, 30));
		home.addActionListener(bh);
		right.add(home);
	}
	
	// calls a setter method in the GameInfo class to store the name value
	// they entered in the text field. It calls a getter method called
	// scoreboard to get the string which the text area needs to print,
	// and then sets the text.
	public void actionPerformed(ActionEvent evt)
	{
		info.setName(name.getText());
		name.setEditable(false);
		info.scoreboard(true);
		printScores.setText(info.printScores());
	}
	
	public void paintComponent(Graphics g)
	{
		score.setText("Your score was: " + qp.getScore());
	}
	
	// this handler class used the get action command method to figure
	// out if the home button was pressed, and if it was, it goes to the
	// appropriate page.
	class ButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			if(command.equals("home"))
			{
				name.setText("");
				name.setEditable(true);
				printScores.setText("");
				qp.reset();
				cards.show(panelCards, "Start game");
			}
		}
	}
}

// gotten from GameModuleFiles.java
class GameInfo
{
	private String [][] answers; // 2D array which holds the 4 different
								 // answer choices to each of the 20 
								 // questions, used in grabQuestion
								 // FromFile and the getAnswer method.
	private String [] questions; // array which holds all the questions, 
								 // used in grabQuestionFromFile and the
								 // getQuestion method.
	private int [] correct; // array of indexes for the other arrays, shows
							// what the correct answer is, used in grab
							// QuestionFromFile and the getCorrectAnswer
							// method.
	private int index; // randomly generated number used to choose which
					   // question to display. the same number is then
					   // used to determine which answers need to be 
					   // displayed, and the correct answer as well. It 
					   // is used in most of the getter methods.
	private String name; // the singular name the user entered from Enter
						 // NamePanel. Used in scoreboard and its getter
						 // setter methods.
	private String [] names; // array containing all the names entered,
							 // which are read from a text file. Used in
							 // scoreboard and the printScores method
	private int score; // the singular score the user of that round got. 
					   // used in scoreboard and its getter setter methods.
	private int [] scores; // all scores ever gotten, which is read from
						   // a text file. Used in scoreboard and the 
						   // printScores method.
	private int val; // used as a counter to figure out how many names and
					 // scores are in the text file. Used in scoreboard 
					 // and printScores.
	private boolean [] chosen; // array with values all initialized to false.
							   // when a question is selected to be displayed,
							   // the value is set to true so the question
							   // wont be repeated. Used in constructor
							   // and getQuestion.
	public GameInfo()
	{
		names = new String [100];
		scores = new int [100];
		chosen = new boolean [20];
	}
	
	// Reads the specified file using has next method and sets them equal
	// to a value in their specified array. 
	public void grabQuestionFromFile(int choice)
	{
		Scanner inFile = null;

		String fileName = "GeneticsQA.txt";
		if(choice == 1)
			fileName = "GeneticsQA.txt";
		else if(choice == 2)
			fileName = "CellBioQA.txt";
		else if(choice == 3)
			fileName = "PhysiologyQA.txt";
			
		File inputFile = new File(fileName);
		try
		{
			inFile = new Scanner(inputFile);
		}
		catch(FileNotFoundException e)
		{
			System.err.printf("ERROR: Cannot open %s/n", fileName);
			System.out.println(e);
			System.exit(1);
		}

		questions = new String[20];
		answers = new String[20][4];
		correct = new int[20];
		int num = 0;
		while(inFile.hasNext())
		{
			questions[num] = inFile.nextLine();
			for(int i = 0; i < 4; i++)
				answers[num][i] = inFile.nextLine();
			String read = inFile.nextLine();
			correct[num] = Integer.parseInt(read);
			num++;
		}
		inFile.close();
	}
	
	// question getter method, randomly chooses which question to display
	// and returns the chosen question to QuestionPanel
	public String getQuestion()
	{
		index = (int)(Math.random()*20);
		while(chosen[index] == true)
			index = (int)(Math.random()*20);
		chosen[index] = true;
		return questions[index];
	}
	
	// answers getter method, uses the randomly chosen number in the 
	// question getter method to find the answer set to that specific
	// question and returns it to QuestionPanel
	public String getAnswer(int num)
	{
		return answers[index][num];
	}
	
	// finds the right answer to that question using the same variable
	// and returns it
	public int getCorrectAnswer()
	{
		return correct[index];
	}
	
	// sets the name using the parameter sent into the method
	public void setName(String nameIn)
	{
		name = nameIn;
	}
	
	// returns the name that is set in the setter method
	public String getName()
	{
		return name;
	}
	
	// sets the score using the parameter sent into the method
	public void setScore(int scoreIn)
	{
		score = scoreIn;
	}
	
	// returns the score that is set in the setter method
	public int getScore()
	{
		return score;
	}
	
	// this method both reads and writes to the file to create a scoreboard.
	// it first reads to get all the data that is in the file, and store
	// the values into their respective arrays. then it uses a print writer
	// to write the new name and score values to the file. there is a 
	// parameter sent into this method so we can know what class called it.
	// if the scoreboard class called it, the boolean is false, and we 
	// don't want to print anything to the file. if the enter name class
	// called it, the value is true and we do want it to write to the file.
	public void scoreboard(boolean check)
	{
		String fileName = "scores.txt";
		Scanner inFile = null;
		File inputFile = new File(fileName);
		try 
		{
			inFile = new Scanner(inputFile);
		} 
		catch(FileNotFoundException e) 
		{
			System.err.printf("ERROR: Cannot open %s\n", fileName);
			System.out.println(e);
			System.exit(1);
		}
		
		val = 0;
		while(inFile.hasNext()) 
		{
			names[val] = inFile.nextLine();
			scores[val] = inFile.nextInt();
			inFile.nextLine();
			val++;
		}
		inFile.close();
		
		order();
		
		File ioFile = new File("scores.txt");
		PrintWriter outFile = null;
		try
		{
			outFile = new PrintWriter(ioFile);
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		for(int i = 0; i < val; i++)
		{
			outFile.println(names[i]);
			outFile.println(scores[i]);
		}
		if(check)
		{
			outFile.println(name);
			outFile.println(score);
		}
		outFile.close();
	}
	
	// this method uses bubble sorting to order the scores from greatest
	// to least
	public void order()
	{
		int switchVal = 0;
		String switchString = "";
		for(int i = 0; i < val; i++)
		{
			for(int j = i+1; j < val; j++)
			{
				if(scores[i] < scores [j])
				{
					switchVal = scores[i];
					scores[i] = scores[j];
					scores[j] = switchVal;
					
					switchString = names[i];
					names[i] = names[j];
					names[j] = switchString;				
				}
			}
		}
	}
	
	// this method puts it all together and formats it to make the names
	// and scores in scoreboard format.
	public String printScores()
	{
		order();
		String textArea = "";
		for(int i = 0; i < val; i++)
		{
			textArea += (String.format("\n  %-4s%-25s%d\n", ((i+1) + "."), names[i], scores[i]));
		}
		return textArea;
	}
}
