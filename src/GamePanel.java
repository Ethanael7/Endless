import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class GamePanel extends JPanel {
    private Bat bat;
    private ArrayList<ShapeEntity> shapes;
    private ArrayList<Bullet> bullets;
    private int lives = 3;
    private boolean gameOver = false;
    private int score = 0;

    public GamePanel() {
        shapes = new ArrayList<>();
        bullets = new ArrayList<>();
    }

    public void createGameEntities() {
        this.bat = new Bat(this, 50, 350);
        shapes.add(new ShapeEntity(this, 200, 10, 30, 30, "circle", Color.YELLOW, bat));
        shapes.add(new ShapeEntity(this, 300, 50, 40, 40, "rectangle", Color.RED, bat));
        shapes.add(new ShapeEntity(this, 400, 20, 35, 35, "circle", Color.BLUE, bat));
        shapes.add(new ShapeEntity(this, 500, 60, 45, 45, "rectangle", Color.GREEN, bat));
    }

    public void drawGameEntities() {
        if (bat != null) {
            bat.draw();
        }
        for (ShapeEntity shape : shapes) {
            shape.draw();
        }
        for(Bullet bullet : bullets) {
            bullet.draw();
    }
}

    public void updateGameEntities(int direction) {
        if(!gameOver){
            
        if (bat != null) {
            bat.erase();
            bat.move(direction);
        }
    }
}

    public void shootBullet() {
        if(!gameOver){
            bullets.add(new Bullet(this, bat.getX() + bat.getWidth() / 2, bat.getY()));
        }
    }

    public void updateBullets() {
        bullets.removeIf(bullet -> {
            bullet.move();
            for (ShapeEntity shape : shapes) {
                if (bullet.collidesWith(shape)) {
                    score += 100;
                    shapes.remove(shape);
                    return true;
                }
            }
            return bullet.isOutOfBounds();
        });
    }


    public void dropShapes() {
        for (ShapeEntity shape : shapes) {
            shape.start();
        }
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void checkCollisions() {
        for (Bullet bullet : getBullets()) {
            for (ShapeEntity shape : shapes) {
                if (shape.getBoundingRectangle().intersects(bullet.getBoundingRectangle())) {
                    score += 100;
                    bullet.erase();
                    shapes.remove(shape);
                    return;
                }
            }
        }

        for (ShapeEntity shape : shapes) {
            if (shape.collidesWithBat()) {
                lives--;
                if (lives <= 0) {
                    gameOver = true;
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
    }

    public boolean isOnBat(int x, int y) {
        return bat != null && bat.isOnBat(x, y);
    }
}

