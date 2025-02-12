import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameWindow extends JFrame implements ActionListener, KeyListener, MouseListener {
    private JLabel scoreL;
    private JTextField scoreTF;
    private JButton startB;
    private JButton exitB;
    
    private Container c;
    private JPanel mainPanel;
    private GamePanel gamePanel;
    
    public GameWindow() {
        setTitle("Shape Invaders");
        setSize(600, 650);
        setLocationRelativeTo(null); // Center the window
        
        // Create labels and text field.
        scoreL = new JLabel("Score: ");
        scoreTF = new JTextField(10);
        scoreTF.setEditable(false);
        scoreTF.setBackground(Color.WHITE);
        scoreTF.setFont(new Font("Arial", Font.BOLD, 20));
        scoreTF.setPreferredSize(new Dimension(100, 50));
        
        // Create buttons.
        startB = new JButton("Start Game");
        exitB = new JButton("Exit");
        startB.addActionListener(this);
        exitB.addActionListener(this);
        
        // Create mainPanel.
        mainPanel = new JPanel(new FlowLayout());
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setFocusable(true);
        
        // Create the gamePanel.
        gamePanel = new GamePanel();
        gamePanel.setPreferredSize(new Dimension(500, 500));
        
        // Create info and button panels.
        JPanel infoPanel = new JPanel(new GridLayout(1, 2));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.add(scoreL);
        infoPanel.add(scoreTF);
        
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(startB);
        buttonPanel.add(exitB);
        
        // Add sub-panels.
        mainPanel.add(infoPanel);
        mainPanel.add(gamePanel);
        mainPanel.add(buttonPanel);
        
        // Set up listeners.
        gamePanel.addMouseListener(this);
        mainPanel.addKeyListener(this);
        
        c = getContentPane();
        c.add(mainPanel);
        
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
        scoreTF.setText("0");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if(command.equals(startB.getText())) {
            gamePanel.startLevel();
            mainPanel.requestFocusInWindow();
            startB.setEnabled(false);  // Disable start button once the game starts
        } else if(command.equals(exitB.getText())) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(keyCode == KeyEvent.VK_RIGHT) {
            gamePanel.updateGameEntities(2);
        } else if(keyCode == KeyEvent.VK_LEFT) {
            gamePanel.updateGameEntities(1);
        } else if(keyCode == KeyEvent.VK_DOWN) {
            gamePanel.updateGameEntities(3);
        } else if(keyCode == KeyEvent.VK_UP) {
            gamePanel.updateGameEntities(4);
        } else if(keyCode == KeyEvent.VK_SPACE) {
            System.out.println("Space pressed! Shooting bullet...");
            gamePanel.shoot();
        }
        updateScore();
    }
    
    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    
    public void updateScore() {
        int score = gamePanel.getScore();
        scoreTF.setText(String.valueOf(score));
    }
    
    // Method to re-enable the start button after game over
    public void enableStartButton() {
        startB.setEnabled(true);
    }
}



