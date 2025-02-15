import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

public class Bullet {
    private JPanel panel;
    private int x;
    private int y;
    private int width = 5;
    private int height = 10;
    private int speed = 5;
    private Color backgroundColour;
    private boolean active = true;

    public Bullet(JPanel panel, int x, int y, int width, int height) {
        this.panel = panel;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.backgroundColour = panel.getBackground(); // For erasing bullet
    }

    public void move() {
        x += speed; // Bullet moves horizontally
        if (x > panel.getWidth()) { // If the bullet moves off-screen
            active = false;
        }
    }

    public void erase(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(backgroundColour); // Use background color to erase
        g2.fillRect(x, y, width, height);
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE); // Draw the bullet in white
        g.fillRect(x, y, width, height);
    }

    public boolean isActive() {
        return active;
    }

    public boolean collidesWith(ShapeEntity shape) {
        return shape.getBoundingRectangle().intersects(new Rectangle(x, y, width, height));
    }

    public Rectangle2D.Double getBoundingRectangle() {
        return new Rectangle2D.Double(x, y, width, height);
    }
}
