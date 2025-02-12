import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import javax.swing.JPanel;

public class Alien extends Thread {

   private JPanel panel;

   private int x;
   private int y;

   private int diameter;     // Diameter of the alien circle

   private int dx;        // increment to move along x-axis
   private int dy;        // increment to move along y-axis

   private Color backgroundColour;
   private Dimension dimension;

   boolean isRunning;

   Random random;

   private Bat bat;

   public Alien(JPanel p, int xPos, int yPos, Bat bat) {
      panel = p;
      dimension = panel.getSize();
      backgroundColour = panel.getBackground();

      x = xPos;      // Start off the screen (right side)
      y = yPos;
      diameter = 30;  // Circle diameter

      this.bat = bat;

      random = new Random();

      dx = -10;         // Alien moves left across the screen
      dy = 0;           // No vertical movement, only horizontal
   }

   public void setLocation() {
      int panelWidth = panel.getWidth();
      // Start at a random position on the right side of the screen
      x = panelWidth;
      y = random.nextInt(panel.getHeight() - diameter);
   }

   public void draw() {
      Graphics g = panel.getGraphics();
      Graphics2D g2 = (Graphics2D) g;

      // Draw the alien as a circle
      Ellipse2D.Double circle = new Ellipse2D.Double(x, y, diameter, diameter);

      g2.setColor(Color.YELLOW); 
      g2.fill(circle);    // Fill the circle with color

      g2.setColor(Color.BLACK);  
      g2.draw(circle);    // Draw the outline of the circle

      g.dispose();
   }

   public void erase() {
      Graphics g = panel.getGraphics();
      Graphics2D g2 = (Graphics2D) g;

      // Erase the circle by drawing a rectangle on top of it
      g2.setColor(backgroundColour);
      g2.fill(new Ellipse2D.Double(x, y, diameter, diameter));
      g2.setColor(backgroundColour);
      g2.draw(new Ellipse2D.Double(x, y, diameter, diameter));

      g.dispose();
   }

   public void move() {
      if (!panel.isVisible()) return;

      x = x + dx;
      y = y + dy;

      int width = panel.getWidth();
      // If the alien moves off the left side, regenerate it at the right
      if (x + diameter < 0) {
         setLocation();
      }
   }

   public void run() {
      isRunning = true;

      try {
         while (isRunning) {
            erase();
            move();
            draw();
            sleep(50);    // Sleep for 50ms to control speed
         }
      }
      catch (InterruptedException e) {}
   }

   public boolean isOnHead(int x, int y) {
      // Check if the point (x, y) is within the circle bounds
      Ellipse2D.Double circle = new Ellipse2D.Double(x, y, diameter, diameter);
      return circle.contains(x, y);
   }

   public Rectangle2D.Double getBoundingRectangle() {
      return new Rectangle2D.Double(x, y, diameter, diameter);
   }

   public boolean collidesWithBat() {
      Rectangle2D.Double myRect = getBoundingRectangle();
      Rectangle2D.Double batRect = bat.getBoundingRectangle();

      return myRect.intersects(batRect); 
   }
}


