import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

public class Bullet {
    private JPanel panel;
    private int x;
    private int y;
    private int width=5;
    private int height=10;
    private int speed = 5;
    private Color backgroundColour;
    private Dimension dimension;
    private boolean active = true;


    public Bullet(JPanel p, int x, int y) {
        this.panel = p;
        this.x = x;
        this.y = y;
        dimension = panel.getSize();
        backgroundColour = panel.getBackground();
}

    public void move(){
        x+=speed;
        if(x > panel.getWidth()){
            active = false;
        }
    }

    public void erase() {
        Graphics g = panel.getGraphics();
        Graphics2D g2 = (Graphics2D) g;
  
        g2.setColor(backgroundColour);
        g2.fillRect(x, y, width, height);
  
        g.dispose();
     }


    public Rectangle2D.Double getBoundingRectangle() {
        return new Rectangle2D.Double(x, y, width, height);
     }

    public void draw(){
        Graphics g = panel.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(x, y, width, height);
        g.dispose();
}

    public boolean isOutOfBounds(){
        return y<0;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public boolean collidesWith(ShapeEntity shape) {
        return shape.getBoundingRectangle().intersects(new Rectangle(x, y, width, height));
    }

    
} 
