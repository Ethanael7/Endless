import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel {
    private Ship ship;
    private ArrayList<Rectangle> obstacles;
    private ArrayList<Bullet> bullets;
    private ArrayList<Triangle> triangles;
    private Timer bulletTimer;
    private int score = 0;
    private int lives = 3;
    private boolean gameOver = false;
    private int level = 1;
    private final int baseObstacles = 10;

    public GamePanel() {
        obstacles = new ArrayList<>();
        bullets = new ArrayList<>();
        bulletTimer = new Timer(50, e -> updateBullets());
        bulletTimer.start();
        triangles = new ArrayList<>();
    }

    public void startLevel() {
        if (ship == null) {
            ship = new Ship(this, getWidth() / 2, getHeight() / 2);
        } else {
            ship.setPosition(getWidth() / 2, getHeight() / 2);
        }

        obstacles.clear();
        triangles.clear();

        int numObstacles = baseObstacles + (level - 1) * 2;
        Random rand = new Random();
        for (int i = 0; i < numObstacles; i++) {
            int x = rand.nextInt(Math.max(getWidth() - 50, 1));
            int y = 0;
            obstacles.add(new Rectangle(this, x, y));
        }

        dropTriangle();
        drawGameEntities();
    }


    public void updateGame() {
   
        Graphics g = getGraphics();  
        if (g == null) return;

        for (Rectangle obstacle : obstacles) {
            obstacle.erase(g);
            obstacle.move();
            obstacle.draw(g);

            if (obstacle.checkCollision(ship.getX(), ship.getY(), ship.getSize(), ship.getSize())) {
                loseLife(1);  
                obstacles.remove(obstacle);  
            }
        }

        for (Triangle triangle : triangles) {
            triangle.erase(g);
            triangle.move();
            triangle.draw(g);

            if (triangle.checkCollision(ship.getX(), ship.getY(), ship.getSize(), ship.getSize())) {
                loseLife(2);  
                triangles.remove(triangle);  
                ship = null;  
            }
        }
        if (obstacles.isEmpty()) {
            nextLevel();  
        }

        g.dispose();  
    }

    public void drawGameEntities() {
        Graphics g = getGraphics();
        if (g == null) return;
        if (ship != null) ship.draw();  
        for (Rectangle obstacle : obstacles) {
            obstacle.draw(g);
        }
        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }
        for (Triangle triangle : triangles) {
            triangle.draw(g);
        }
        g.dispose();
    }

    public void updateGameEntities(int direction) {
        if (ship == null) return;
        ship.erase();
        ship.move(direction);
        ship.draw();
    }

    public void dropTriangle() {
        Random random = new Random();
        int spawnSide = random.nextInt(4); 
        int x = 0, y = 0;
        int speedX = 0, speedY = 0;
        if (spawnSide == 0) {
            x = getWidth();
            y = random.nextInt(getHeight());
            speedX = -5;
        } else if (spawnSide == 1) {
            x = -20;
            y = random.nextInt(getHeight());
            speedX = 5;
        } else if (spawnSide == 2) {
            x = random.nextInt(getWidth());
            y = -20;
            speedY = 5;
        } else if (spawnSide == 3) {
            x = random.nextInt(getWidth());
            y = getHeight();
            speedY = -5;
        }
        triangles.add(new Triangle(this, x, y, speedX, speedY));
    }

    public void shoot() {
        if (ship == null) return;
        Bullet bullet = new Bullet(ship.getX() + ship.getSize() / 2, ship.getY(), 5);
        bullets.add(bullet);
        System.out.println("Bullet shot! Total bullets: " + bullets.size());
    }

    private void updateBullets() {
        Graphics g = getGraphics();
        if (g == null) return;
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            bullet.erase(g);
            bullet.move();

            for (int j = 0; j < obstacles.size(); j++) {
                Rectangle obstacle = obstacles.get(j);
                if (obstacle.isAlive() && obstacle.checkCollision(bullet)) {
                    obstacle.destroy();
                    bullets.remove(i);
                    i--;
                    score += 100;
                    System.out.println("Hit! Score: " + score);
                    break;
                }
            }
            if (i >= 0 && i < bullets.size()) {
                if (bullets.get(i).getY() < 0) {
                    bullets.remove(i);
                    i--;
                } else {
                    bullets.get(i).draw(g);
                }
            }
        }
        g.dispose();
    }

    public void loseLife(int livesLost) {
        lives -= livesLost;
        System.out.println("Life lost! Remaining lives: " + lives);
        if (ship != null) {
            ship.setPosition(getWidth() / 2, getHeight() / 2);  // Reset the ship position
        }
        if (lives <= 0) {
            gameOver();
        }
    }

    public void gameOver() {
        gameOver = true;
        System.out.println("Game Over! Click Start Game to restart.");
        if (bulletTimer != null) bulletTimer.stop();
    }

    public void nextLevel() {

        level++;
        System.out.println("Level " + level + " starting!");
       
        if (ship != null) {
            ship.setPosition(getWidth() / 2, getHeight() / 2);
        }
       
        int numObstacles = baseObstacles + (level - 1) * 2;
        Random rand = new Random();
        obstacles.clear();
        for (int i = 0; i < numObstacles; i++) {
            int x = rand.nextInt(Math.max(getWidth() - 50, 1));
            int y = 0;
            obstacles.add(new Rectangle(this, x, y));
        }
     
        triangles.clear();
        dropTriangle();
    }

    public int getScore() {
        return score;
    }
}









