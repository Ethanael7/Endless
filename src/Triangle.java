import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Triangle {

    private JPanel panel;
    private int x, y;
    private int size = 20;
    private int speedX, speedY;

    public Triangle(JPanel p, int xPos, int yPos, int speedX, int speedY) {
        panel = p;
        x = xPos;
        y = yPos;
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public void draw(Graphics g) {
        if (g != null) {
            g.setColor(Color.RED);
            int[] xPoints = {x, x + size, x + size / 2};
            int[] yPoints = {y, y, y + size};
            g.fillPolygon(xPoints, yPoints, 3); 
        }
    }

    public void erase(Graphics g) {
        if (g != null) {
            g.setColor(panel.getBackground()); 
            int[] xPoints = {x, x + size, x + size / 2};
            int[] yPoints = {y, y, y + size};
            g.fillPolygon(xPoints, yPoints, 3); 
        }
    }

    public void move() {
        x += speedX;
        y += speedY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    public boolean checkCollision(int x, int y, int width, int height) {
        return x < this.x + this.size && x + width > this.x &&
               y < this.y + this.size && y + height > this.y;
    }
}




    





