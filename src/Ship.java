import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.Polygon;
import javax.swing.JPanel;
import java.util.*;
import javax.swing.Timer;


public class Ship {

    private JPanel panel;
    private int x;
    private int y;
    private int size;
    private int dx, dy;
    private Color backgroundColour;
    private Dimension dimension;
    private Polygon ship;
    


    public Ship(JPanel p, int xPos, int yPos) {
        panel = p;
        backgroundColour = panel.getBackground();
        x = xPos;
        y = yPos;
        size = 30;
        dx = 10;
        dy = 10;
       
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getSize(){
        return size;
    }

    public void draw() {
        Graphics g = panel.getGraphics();
        Graphics2D g2 = (Graphics2D) g;

        int[] xPoints = {x, x + size / 2, x + size};
        int[] yPoints = {y + size, y, y + size};

        ship = new Polygon(xPoints, yPoints, xPoints.length);

        g2.setColor(Color.BLACK);
        g2.fill(ship);

        g.dispose();
    }

    public void erase() {
        Graphics g = panel.getGraphics();
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(backgroundColour);
        g2.fill(new Polygon(new int[]{x, x + size / 2, x + size}, 
                            new int[]{y + size, y, y + size}, 3));

        g.dispose();
    }

    public void move(int direction) {
        if (!panel.isVisible()) return;

        dimension = panel.getSize();

        if (direction == 1) {
            x = Math.max(x - dx, 0);
        } else if (direction == 2) {
            x = Math.min(x + dx, dimension.width - size);
        } else if (direction == 3) {
            y = Math.min(y + dy, dimension.height - size);
        } else if (direction == 4) {
            y = Math.max(y - dy, 0);
        }
    }

    
   public Rectangle2D.Double getBoundingRectangle() {
    return new Rectangle2D.Double (x, y, size, size);
 }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        draw();
    }

}


