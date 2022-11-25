import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import java.util.prefs.Preferences;



public class GuessingGame extends JFrame {
	
	//Declared Variables
	private JTextField txtGuess;
	private JLabel lblOutput;
	private JLabel instructionLabel;
	private int range;
	private int theNumber;
	private int guessCounter;
	private JButton againButton;
	private JButton guessButton;
	private long startTime;
	private float endTime;
	public static Preferences prefs;
	public static String prefName;
	public static int r;//range preference variable
	
	//End Declared Variables
	
	public void checkGuess() {
		String guessText = txtGuess.getText();
		String message = "";
		
		try {
		
		int guess = Integer.parseInt(guessText);
		guessCounter += 1;
		if (guess < theNumber) {
			message = guess + " is too low. Try again.";
			
		}
		else if (guess > theNumber) {
			message = guess + " is too high. Try again.";
			
		}
		else {
			endTime = (float) ((System.currentTimeMillis() - startTime)/1000.00);
			message= String.format("%d is correct! You win! It took %d tries in %.2f seconds!", guess, guessCounter, endTime );
			againButton.setVisible(true);
			guessButton.setEnabled(false);
			}
		} catch(Exception e) {
			message = String.format("Must enter a whole number between 1 and %d", range);
			
			
			
		} finally {
			lblOutput.setText(message);
			txtGuess.requestFocus();
			txtGuess.selectAll();
			
		}
	}
	public void newGame(int range) {

		guessCounter = 0;
		againButton.setVisible(false);
		guessButton.setEnabled(true);
		theNumber = (int)(Math.random() * range + 1);
		lblOutput.setText("Enter a number above and click Guess!");
		txtGuess.setText("");
		instructionLabel.setText(String.format("Guess a number between %d and %d:", 1,range));
		startTime= System.currentTimeMillis();
	}
	
	public GuessingGame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Thrilla's Hi-Low Guessing Game");
		getContentPane().setLayout(null);
		
		JLabel titleLabel = new JLabel("Thrilla's Hi-Low Guessing Game");
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(10, 36, 414, 30);
		getContentPane().add(titleLabel);
		
		instructionLabel = new JLabel();
		instructionLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		instructionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		instructionLabel.setBounds(0, 102, 231, 14);
		getContentPane().add(instructionLabel);
		
		txtGuess = new JTextField();
		txtGuess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkGuess();
			}
		});
		txtGuess.setHorizontalAlignment(SwingConstants.RIGHT);
		txtGuess.setBounds(241, 99, 86, 20);
		getContentPane().add(txtGuess);
		txtGuess.setColumns(10);
		
		guessButton = new JButton("Guess!");
		guessButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkGuess();
			}
		});
		guessButton.setBounds(172, 152, 89, 23);
		getContentPane().add(guessButton);
		
		lblOutput = new JLabel("Enter a number above and click Guess!");
		lblOutput.setHorizontalAlignment(SwingConstants.CENTER);
		lblOutput.setBounds(10, 211, 414, 14);
		getContentPane().add(lblOutput);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(10, 11, 434, 22);
		getContentPane().add(menuBar);
		
		JMenu menuSettings = new JMenu("Settings");
		menuBar.add(menuSettings);
		
		JMenuItem menuItemExit = new JMenuItem("Exit");
		menuItemExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		menuSettings.add(menuItemExit);
		//Range Menu
		JMenu menuRange = new JMenu("Change Guessing Range");
		menuBar.add(menuRange);
		//Range SubMenu. Selects the maximum number to guess, or random - currently between 1 and 100,000
		//Radio Button Group
		ButtonGroup rangeGroup = new ButtonGroup();
		
		JRadioButtonMenuItem rangeSubMenuSmall = new JRadioButtonMenuItem("10");
		menuRange.add(rangeSubMenuSmall);
		rangeGroup.add(rangeSubMenuSmall);
		rangeSubMenuSmall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				range = 10;
				prefs.putInt(prefName,range);
				r = prefs.getInt(prefName, range);
				newGame(r);	
				return;				
			}
		});
		
		
		JRadioButtonMenuItem rangeSubMenuMedium = new JRadioButtonMenuItem("100");
		menuRange.add(rangeSubMenuMedium);
		rangeGroup.add(rangeSubMenuMedium);
		rangeSubMenuMedium.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				range = 100;
				prefs.putInt(prefName,range);
				r = prefs.getInt(prefName, range);
				newGame(r);	
				return;	
			}
		});
		
		JRadioButtonMenuItem rangeSubMenuLarge = new JRadioButtonMenuItem("1000");
		menuRange.add(rangeSubMenuLarge);		
		rangeGroup.add(rangeSubMenuLarge);
		rangeSubMenuLarge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				range = 1000;
				prefs.putInt(prefName,range);
				r = prefs.getInt(prefName, range);
				newGame(r);	
				return;
			}
		});
		JRadioButtonMenuItem rangeSubMenuRandom = new JRadioButtonMenuItem("random");
		menuRange.add(rangeSubMenuRandom);
		rangeGroup.add(rangeSubMenuRandom);
		rangeSubMenuRandom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				range = (int)(Math.random() * 100000 + 1);
				prefs.putInt(prefName,range);
				r = prefs.getInt(prefName, range);
				newGame(r);	
				return;			
			}
		});
		
		//End Range SubMenu

		
		
		againButton = new JButton("Again?");
		againButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newGame(prefs.getInt(prefName, 100));
			}
		});
		againButton.setBounds(172, 186, 89, 23);
		getContentPane().add(againButton);
		

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		prefs = Preferences.userRoot();
		prefName = "rangePref";

		GuessingGame theGame = new GuessingGame();
		theGame.newGame(prefs.getInt(prefName, 100));
		theGame.setSize(new Dimension(450,300));
		theGame.setVisible(true);
		
		
		
        
	}

}
