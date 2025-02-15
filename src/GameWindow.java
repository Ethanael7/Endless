import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameWindow extends JFrame implements ActionListener, KeyListener, MouseListener {
    private Timer timer;
    private JLabel statusBarL, keyL, mouseL, scoreL, livesL;
    private JTextField statusBarTF, keyTF, mouseTF, scoreTF, livesTF;
    private JButton startB, pauseB, exitB;
    private JPanel mainPanel, infoPanel, buttonPanel;
    private GamePanel gamePanel;

    public GameWindow() {
        // Window properties
        setTitle("Shape Dodgers");
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
        setResizable(true);

        // Initialize labels
        statusBarL = new JLabel("Application Status:");
        keyL = new JLabel("Key Pressed:");
        mouseL = new JLabel("Mouse Click Location:");
        scoreL = new JLabel("Score:");
        livesL = new JLabel("Lives:");

        // Set label colors
        Color textColor = Color.WHITE;
        statusBarL.setForeground(textColor);
        keyL.setForeground(textColor);
        mouseL.setForeground(textColor);
        scoreL.setForeground(textColor);
        livesL.setForeground(textColor);

        // Initialize text fields
        statusBarTF = createTextField(25);
        keyTF = createTextField(25);
        mouseTF = createTextField(25);
        scoreTF = createTextField(10);
        livesTF = createTextField(10);

        // Initialize buttons
        startB = new JButton("Start Game");
        pauseB = new JButton("Restart Game");
        exitB = new JButton("Exit");

        // Add button listeners
        startB.addActionListener(this);
        pauseB.addActionListener(this);
        exitB.addActionListener(this);

        // Create game panel
        gamePanel = new GamePanel();
		gamePanel.setBackground(new Color(15, 15, 40));
        gamePanel.setPreferredSize(new Dimension(400, 400));
        gamePanel.createGameEntities();
        gamePanel.addMouseListener(this);

        // Create info panel
        infoPanel = new JPanel(new GridLayout(5, 2));
        infoPanel.setBackground(Color.BLACK);
        addToPanel(infoPanel, statusBarL, statusBarTF);
        addToPanel(infoPanel, keyL, keyTF);
        addToPanel(infoPanel, mouseL, mouseTF);
        addToPanel(infoPanel, scoreL, scoreTF);
        addToPanel(infoPanel, livesL, livesTF);

        // Create button panel
        buttonPanel = new JPanel(new GridLayout(1, 3));
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.add(startB);
        buttonPanel.add(pauseB);
        buttonPanel.add(exitB);

        // Create main panel
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setFocusable(true);
        mainPanel.add(infoPanel, BorderLayout.NORTH);
        mainPanel.add(gamePanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.addKeyListener(this);

        getContentPane().add(mainPanel);
        setVisible(true);

        statusBarTF.setText("Game started.");
    }

    private JTextField createTextField(int columns) {
        JTextField textField = new JTextField(columns);
        textField.setEditable(false);
        textField.setBackground(Color.DARK_GRAY);
        textField.setForeground(Color.WHITE);
        return textField;
    }

    private void addToPanel(JPanel panel, JLabel label, JTextField textField) {
        panel.add(label);
        panel.add(textField);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        statusBarTF.setText(command + " button clicked.");

        if (command.equals(startB.getText())) {
            gamePanel.drawGameEntities();
            gamePanel.dropShapes();
        } else if (command.equals(pauseB.getText())) {
            gamePanel.resetGame();
        } else if (command.equals(exitB.getText())) {
            System.exit(0);
        }

        mainPanel.requestFocus();
    }

    // Key event handling
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        keyTF.setText(KeyEvent.getKeyText(keyCode) + " pressed.");

        switch (keyCode) {
            case KeyEvent.VK_DOWN:
                gamePanel.updateGameEntities(2);
                break;
            case KeyEvent.VK_UP:
                gamePanel.updateGameEntities(1);
                break;
            case KeyEvent.VK_SPACE:
                gamePanel.shootBullet();
                break;
        }
        gamePanel.drawGameEntities();
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}


    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX(), y = e.getY();
        mouseTF.setText("(" + x + ", " + y + ")");

        if (gamePanel.isOnBat(x, y)) {
            statusBarTF.setText("Mouse click on bat!");
            statusBarTF.setBackground(Color.RED);
        } else {
            statusBarTF.setText("");
            statusBarTF.setBackground(Color.CYAN);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}


    public void updateScore() {
        scoreTF.setText(String.valueOf(gamePanel.getScore()));
    }

    public void updateLife() {
        livesTF.setText(String.valueOf(gamePanel.getLives()));
    }
}
