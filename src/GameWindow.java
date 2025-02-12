import java.awt.*;
import java.awt.event.*;			
import javax.swing.*;		

public class GameWindow extends JFrame 
				implements ActionListener,
					   KeyListener,
					   MouseListener
{

	private Timer timer;
	// Labels
	private JLabel statusBarL, keyL, mouseL, scoreL,livesL;
	// Text Fields
	private JTextField statusBarTF, keyTF, mouseTF, scoreTF;

	// Buttons
	private JButton startB, pauseB, focusB, exitB;

	private Container c;
	private JPanel mainPanel;
	private GamePanel gamePanel;

	public GameWindow() {
		setTitle("Shape Invaders");
		setSize(600, 650);

		statusBarL = new JLabel("Application Status: ");
		keyL = new JLabel("Key Pressed: ");
		mouseL = new JLabel("Mouse Click Location: ");
		scoreL = new JLabel("Score: 0");
		livesL = new JLabel("Lives: 3");  

		statusBarL.setForeground(Color.WHITE);
		keyL.setForeground(Color.WHITE);
		mouseL.setForeground(Color.WHITE);
		scoreL.setForeground(Color.WHITE);

		// Create text fields (dark background, white text)
		statusBarTF = new JTextField(25);
		keyTF = new JTextField(25);
		mouseTF = new JTextField(25);
		scoreTF = new JTextField(10);

		statusBarTF.setEditable(false);
		keyTF.setEditable(false);
		mouseTF.setEditable(false);
		scoreTF.setEditable(false); 

		statusBarTF.setBackground(Color.DARK_GRAY);
		keyTF.setBackground(Color.DARK_GRAY);
		mouseTF.setBackground(Color.DARK_GRAY);
		scoreTF.setBackground(Color.DARK_GRAY);

		statusBarTF.setForeground(Color.WHITE);
		keyTF.setForeground(Color.WHITE);
		mouseTF.setForeground(Color.WHITE);
		scoreTF.setForeground(Color.WHITE);

		// Create buttons 
		startB = new JButton("Show Bat");
		// pauseB = new JButton("Drop Alien");
		// focusB = new JButton("Focus on Key");
		exitB = new JButton("Exit");


		// Add button listeners
		startB.addActionListener(this);
		// pauseB.addActionListener(this);
		// focusB.addActionListener(this);
		exitB.addActionListener(this);

		// Create main panel (black background)
		mainPanel = new JPanel();
		FlowLayout flowLayout = new FlowLayout();
		mainPanel.setLayout(flowLayout);

		// Create game panel (black background)
		gamePanel = new GamePanel();
		gamePanel.setPreferredSize(new Dimension(450, 450));
		gamePanel.createGameEntities();
	

		// Info panel (grid layout, black background)
		JPanel infoPanel = new JPanel(new GridLayout(4, 2));
		infoPanel.setBackground(Color.BLACK); 
		infoPanel.add(statusBarL);
		infoPanel.add(statusBarTF);
		infoPanel.add(keyL);
		infoPanel.add(keyTF);
		infoPanel.add(mouseL);
		infoPanel.add(mouseTF);
		infoPanel.add(scoreL);
		infoPanel.add(scoreTF);

		// Button panel (black background)
		JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
		buttonPanel.setBackground(Color.BLACK);
		buttonPanel.add(startB);
		// buttonPanel.add(pauseB);
		// buttonPanel.add(focusB);
		buttonPanel.add(exitB);

		// Add sub-panels to main panel
		mainPanel.add(infoPanel);
		mainPanel.add(gamePanel);
		mainPanel.add(buttonPanel);

		// Set up mainPanel to listen for key/mouse events
		gamePanel.addMouseListener(this);
		mainPanel.addKeyListener(this);

		// Add mainPanel to window
		c = getContentPane();
		c.add(mainPanel);

		// Window settings
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		// Initial status message
		statusBarTF.setText("Game started.");
	
	

	// Method to update score from GamePanel
	timer = new Timer(50, e -> {
		gamePanel.updateBullets();
		gamePanel.checkCollisions();
		scoreL.setText("Score: " + gamePanel.getScore());
		livesL.setText("Lives: " + gamePanel.getLives());
	
		if (gamePanel.getLives() <= 0) {
			startB.setEnabled(true);
		}
	});
	
	timer.start();
}


	// implement single method in ActionListener interface

	public void actionPerformed(ActionEvent e) {

		String command = e.getActionCommand();
		
		statusBarTF.setText(command + " button clicked.");


		if (command.equals(startB.getText())){
			gamePanel.drawGameEntities();
			gamePanel.dropShapes();
		}

		if (command.equals(exitB.getText())){
			System.exit(0);
		}

		mainPanel.requestFocus();
	}

	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		String keyText = e.getKeyText(keyCode);
		keyTF.setText(keyText + " pressed.");

		if (keyCode == KeyEvent.VK_DOWN) {
			gamePanel.updateGameEntities(2);
			gamePanel.drawGameEntities();
		}

		if (keyCode == KeyEvent.VK_UP) {
			gamePanel.updateGameEntities(1);
			gamePanel.drawGameEntities();
		}

		if(keyCode == KeyEvent.VK_SPACE) {
			gamePanel.shootBullet();
			gamePanel.drawGameEntities();
		}
	}

	public void keyReleased(KeyEvent e) {

	}

	public void keyTyped(KeyEvent e) {

	}

	// implement methods in MouseListener interface

	public void mouseClicked(MouseEvent e) {

		int x = e.getX();
		int y = e.getY();

		if (gamePanel.isOnBat(x, y)) {
			statusBarTF.setText ("Mouse click on bat!");
			statusBarTF.setBackground(Color.RED);
		}
		else {
			statusBarTF.setText ("");
			statusBarTF.setBackground(Color.CYAN);
		}

		mouseTF.setText("(" + x +", " + y + ")");

	}


	public void mouseEntered(MouseEvent e) {
	
	}

	public void mouseExited(MouseEvent e) {
	
	}

	public void mousePressed(MouseEvent e) {
	
	}

	public void mouseReleased(MouseEvent e) {
	
	}

}