
import java.awt.*;
import javax.swing.*;

public class GamePanel extends JPanel {
    private Man man;  

    public GamePanel() {
        man = new Man(50, 350); // Initialize Man
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (man != null) {
            man.draw(g2); // Pass Graphics2D
        }
    }

    public void updateGameEntities(int direction) {
        if (direction == 1) man.move(-10, 0); // Move left
        else if (direction == 2) man.move(10, 0); // Move right
        else if (direction == 3) man.move(0, -20); // Jump
        else if (direction == 4) man.move(0, 10); // Duck

        repaint(); // Refresh the screen
    }
}
