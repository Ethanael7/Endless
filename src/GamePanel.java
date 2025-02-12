import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class GamePanel extends JPanel {
    private Ship ship;
    private JPanel p;
    private ArrayList<ShapeEntity> shapes;
    private ArrayList<Bullet> bullets;
    private int lives = 3;
    private boolean gameOver = false;
    private int score = 0;
    private long last = 0;
    private static final int delay = 200;

    public GamePanel() {

        shapes = new ArrayList<>();
        bullets = new ArrayList<>();
        setBackground(new Color(15,15,40));
    }

    public void createGameEntities() {
        this.ship = new Ship(this, 50, 350);
        shapes.add(new ShapeEntity(this, 200, 10, 30, 30, "circle", Color.YELLOW, ship));
        shapes.add(new ShapeEntity(this, 300, 50, 40, 40, "rectangle", Color.RED, ship));
        shapes.add(new ShapeEntity(this, 400, 20, 35, 35, "circle", Color.BLUE, ship));
        shapes.add(new ShapeEntity(this, 500, 60, 45, 45, "rectangle", Color.GREEN, ship));
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
        if(!gameOver && ship != null){
            ship.erase();
            ship.move(direction);
            }
        }
    

    public void shootBullet() {
        if (ship == null || System.currentTimeMillis() - last < delay) return;  
        last = System.currentTimeMillis();
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
            bullet.draw();  

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

       


    public void dropShapes() {
        if(gameOver) return;
        for (ShapeEntity shape : shapes) {
            shape.start();
        }
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
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
    public int getScore(){
      return score;
    }

    public int getLives(){
        return lives;
    }

    public void resetGame() {
        lives = 3;
        score = 0;
        gameOver = false;
        shapes.clear();
        createGameEntities();
        dropShapes();
    }

    public boolean isOnBat(int x, int y) {
        return ship != null && ship.isOnBat(x, y);
    }
}

