import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;

public class GamePanel extends JPanel {
    private Box square;
    private ArrayList<Rectangle> obstacles = new ArrayList<>();
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private Timer obstacleTimer;
    private Timer bulletTimer;

    public GamePanel() {
        createGameEntities();

        // Timer to update bullets (moves them upwards)
        bulletTimer = new Timer(50, e -> updateBullets());
        bulletTimer.start();
    }

    public void createGameEntities() {
        square = new Box(this, 50, 300);
        obstacles.add(new Rectangle(this, getWidth(), 250));
        obstacles.add(new Rectangle(this, getWidth() + 100, 250));
    }

    public void drawGameEntities() {
        if (square != null) {
            square.draw();
        }

        for (Rectangle obstacle : obstacles) {
            obstacle.draw();
        }

        for (Bullet bullet : bullets) {
            bullet.draw(getGraphics());
        }
    }

    public void updateGameEntities(int direction) {
        if (square == null) return;

        square.erase();
        square.move(direction);
        square.draw();
    }

    public void moveObstacles() {
        for (Rectangle obstacle : obstacles) {
            obstacle.erase();
            obstacle.move();
            obstacle.draw();
        }
    }

    public void dropObstacle() {
        obstacles.add(new Rectangle(this, getWidth(), 250));
    }


    public void shoot() {
        if (square == null) return;
        Bullet bullet = new Bullet(square.getX() + square.getSize() / 2, square.getY(), 5);
        bullets.add(bullet);
        System.out.println("Bullet shot! Total bullets: " + bullets.size());
    }

    private void updateBullets() {
        Graphics g = getGraphics();
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            bullet.erase(g);
            bullet.move();
            if (bullet.getY() < 0) {
                bullets.remove(i);
                i--;
            } else {
                bullet.draw(g);
            }
        }
        g.dispose();
    }
}






