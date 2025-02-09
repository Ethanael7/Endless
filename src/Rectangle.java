import java.awt.*;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

public class Rectangle {

    private JPanel panel;
    private int x;
    private int y;
    private int width;
    private int height;
    boolean alive = true;

    private int dx;
    private int dy;

    private Rectangle2D.Double rectangle;

    public Rectangle(JPanel p, int xPos, int yPos){
        panel = p;
        x = xPos;
        y = 0;  
        dx = 2;  
        dy = 10; 

        width = 30;
        height = 20;
    }

    public void draw() {
        Graphics g = panel.getGraphics();
        Graphics2D g2 = (Graphics2D) g;

        rectangle = new Rectangle2D.Double(x, y, width, height);
        g2.setColor(Color.GREEN);
        g2.fill(rectangle);

        g.dispose();
    }

    public void erase() {
        Graphics g = panel.getGraphics();
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(panel.getBackground());
        g2.fill(new Rectangle2D.Double(x, y, width, height));

        g.dispose();
    }

    public void move() {
        x -= dx;  

        if (x + width < 0 || x > panel.getWidth()) {
            dx = -dx;  
            y += dy;   
        }

        if (y >= panel.getHeight() - height) {
            y = panel.getHeight() - height;  
        }
    }

    public void update() {
        erase();  
        move();  
        draw();  
    }
}



  


   


   
