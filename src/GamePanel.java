import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

public class GamePanel extends JPanel {
    private Ship ship;
    private Timer timer;
    private JPanel p;
    private ArrayList<ShapeEntity> shapes;
    private ArrayList<Bullet> bullets;
    private ShapeEntity shape;
    private int lives = 3;
    private boolean gameOver = false;
    private int score = 0;
    private static final Random random = new Random();

    public GamePanel() {
        shapes = new ArrayList<>();
        bullets = new ArrayList<>();
        setBackground(new Color(15, 15, 40));

        timer = new Timer(50, e -> {
            updateBullets();
            checkCollisions();
            GameWindow gameWindow = (GameWindow) SwingUtilities.getWindowAncestor(this);
            if (gameWindow != null) {
                gameWindow.updateScore();
                gameWindow.updateLife();
            }
            if (lives <= 0) {
                gameOver = true;
                stopGame();

            }
        });
        timer.start();  
    }

    public void createGameEntities() {
   
        this.ship = new Ship(this, 50, 350);  

        spawnRandomShapes(5);  
    }

    public void spawnRandomShapes(int numShapes) {
 
        int shapeWidth = 40;  
        int shapeHeight = 40; 
    
        for (int i = 0; i < numShapes; i++) {
       
            String shapeType = random.nextInt(3) == 0 ? "circle" : (random.nextInt(2) == 0 ? "rectangle" : "triangle");
    
            int x = random.nextInt(800);  
            int y = random.nextInt(600);
    
            Color color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));  // Random color
    
            shapes.add(new ShapeEntity(this, x, y, shapeWidth, shapeHeight, shapeType, color, ship));
        }
    }
    
    

    public void drawGameEntities() {
        if (ship != null) {
            ship.draw();
        }
        for (ShapeEntity shape : shapes) {
            shape.draw();
        }
    }

    public void updateGameEntities(int direction) {
        if (!gameOver && ship != null) {
            ship.erase();
            ship.move(direction);
        }
    }

    public void shootBullet() {
        if (ship == null) return;
        Bullet bullet = new Bullet(this, ship.getX() + ship.getSize() / 2, ship.getY(), 5, 10);
        bullets.add(bullet);
    }

    public void updateBullets() {
        if (gameOver) return;

        Graphics g = getGraphics();
        if (g == null) return;

        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            bullet.erase(g);
            bullet.move();
            bullet.draw(g);

            if (!bullet.isActive()) {
                bullets.remove(i);
                i--;
                continue;
            }

            for (int j = 0; j < shapes.size(); j++) {
                ShapeEntity shape = shapes.get(j);
                if (shape.isAlive() && shape.checkCollision(bullet)) {
                    score += 100;
                    shape.destroy();
                    bullets.remove(i);
                    i--;
                    break;
                }
            }
        }
    }

    public void stopGame() {
        stopTimer();  
        stopShapes();  
    }

    public void stopShapes() {
        for (ShapeEntity shape : shapes) {
            shape.stopRun();  
        }
    }


    public void dropShapes() {
        if (gameOver) return;
        for (ShapeEntity shape : shapes) {
            shape.start();
        }
    }

    public void checkCollisions() {
        if (gameOver) return;

        for (ShapeEntity shape : shapes) {
            if (shape.isAlive() && shape.collidesWithBat()) {
                lives--;

                if (lives <= 0) {
                    gameOver = true;
                    lives = 0;
                    return;
                }
            }
        }
    }

    private void stopTimer() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
    }

    private void startTimer() {
        if (timer != null && !timer.isRunning()) {
            timer.start();
        }
    }


    public int getScore() {
        return score;
    }

    public int getLives() {
        return lives;
    }

    public void resetGame() {
        clearScreen(); 
        lives = 3;
        score = 0;
        gameOver = false;

        if (bullets != null) {
            bullets.clear();
        }
        if (shapes != null) {
            shapes.clear();
        }
        createGameEntities();
        dropShapes();
        startTimer();
    }

    public void clearScreen() {
 
        for (Bullet bullet : bullets) {
            bullet.erase(getGraphics());
        }
        bullets.clear();  
    
        for (ShapeEntity shape : shapes) {
            shape.erase();
        }
        shapes.clear();  
     

        if (ship != null) {
            ship.erase();
        }
    }

    public boolean isOnBat(int x, int y) {
        return ship != null && ship.isOnBat(x, y);
    }
}
