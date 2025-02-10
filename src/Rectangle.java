import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.util.Random;
import java.awt.geom.*;

// Rectangle class
public class Rectangle {
    private JPanel panel;
    private int x, y;
    private int width, height;
    private boolean alive = true;
    private Rectangle rectangle;
    private int speed = 5;
    private int originalX;
    private int originalY;
    private int dx;
    private int dy;
    private Color backgroundColour;
    private Dimension dimension;
    private Random random;
    private Bullet bullet;

    public Rectangle(JPanel p, int xPos, int yPos, Bullet bullet) {
        panel = p;
        x = xPos;
        y = yPos;
        width = 30;
        height = 20;
        random = new Random();
        
        setLocation();

       dx = 5;
       dy = 5;

       this.bullet = bullet;
    }

    public void setLocation(){
        int panelWidth = panel.getWidth();
        x = random.nextInt(panelWidth - width);
        y = 10;
    }

    public void draw() {
        if (alive) {
            g.setColor(Color.GREEN);
            g.fillRect(x, y, width, height);
        }
    }

    public void erase(Graphics g) {
        if (alive) {
            g.setColor(panel.getBackground());
            g.fillRect(x, y, width, height);
        }
    }

    public void move() {
        x += speed;
        if (x > panel.getWidth()) {
            x = 0;
            y += height; 
            if (y > panel.getHeight()) {
                y = 0;
            }
        }
        rectangle.setLocation(x, y);
    }

    public void destroy() {
        alive = false;
        System.out.println("Rectangle destroyed!");
    }

    public boolean checkCollision(Bullet bullet) {
        if (!alive) return false;
        java.awt.Rectangle bulletRect = new java.awt.Rectangle(bullet.getX(), bullet.getY(), bullet.getSize(), bullet.getSize());
        return rectangle.intersects(bulletRect);
    }

    public boolean checkCollision(int shipX, int shipY, int shipWidth, int shipHeight) {
        if (!alive) return false;
        java.awt.Rectangle shipRect = new java.awt.Rectangle(shipX, shipY, shipWidth, shipHeight);
        return rectangle.intersects(shipRect);
    }

    public boolean isAlive() {
        return alive;
    }

    public int getY() {
        return y;
    }
}







  


   


   
