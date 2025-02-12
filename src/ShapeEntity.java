import java.awt.*;
import java.awt.geom.*;
import java.util.Random;
import javax.swing.JPanel;

public class ShapeEntity extends Thread {

   private JPanel panel;
   private int x, y, width, height, dx, dy;
   private Color backgroundColour, shapeColor;
   private Dimension dimension;
   private boolean isRunning;
   private Random random;
   private String shapeType; // "circle" or "rectangle"
   private Bat bat;

   public ShapeEntity(JPanel p, int xPos, int yPos, int width, int height, String shapeType, Color shapeColor, Bat bat) {
      panel = p;
      dimension = panel.getSize();
      backgroundColour = panel.getBackground();
      this.shapeColor = shapeColor;
      this.shapeType = shapeType;
      this.bat = bat;

      x = xPos;
      y = yPos;
      this.width = width;
      this.height = height;

      random = new Random();
      dx = -10; 
      dy = 0;   
   }

   public void setLocation() {
      x = panel.getWidth();
      y = random.nextInt(panel.getHeight() - height);
   }

   public void draw() {
      Graphics g = panel.getGraphics();
      Graphics2D g2 = (Graphics2D) g;
      g2.setColor(shapeColor);

      if (shapeType.equalsIgnoreCase("circle")) {
         Ellipse2D.Double circle = new Ellipse2D.Double(x, y, width, height);
         g2.fill(circle);
         g2.setColor(Color.BLACK);
         g2.draw(circle);
      } else if (shapeType.equalsIgnoreCase("rectangle")) {
         Rectangle2D.Double rect = new Rectangle2D.Double(x, y, width, height);
         g2.fill(rect);
         g2.setColor(Color.BLACK);
         g2.draw(rect);
      }
      g.dispose();
   }

   public void erase() {
      Graphics g = panel.getGraphics();
      Graphics2D g2 = (Graphics2D) g;
      g2.setColor(backgroundColour);
   
      int padding = 2; 
      g2.fill(new Rectangle2D.Double(x - padding, y - padding, width + 2 * padding, height + 2 * padding));
  
      g.dispose();
  }
  
   public void move() {
      if (!panel.isVisible()) return;

      x += dx;
      y += dy;

      if (x + width < 0) {
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
            checkCollision();
            sleep(50);
         }
      } catch (InterruptedException e) {}
   }

   public boolean collidesWithBat() {
      Rectangle2D.Double shapeBounds = new Rectangle2D.Double(x, y, width, height);
      return shapeBounds.intersects(bat.getBoundingRectangle());
   }

   public boolean hasReachedEnd() {
      return x + width < 0;
   }

   public void checkCollision() {
      if (collidesWithBat() && shapeType.equals("rectangle")) {
         System.out.println("Collision with bat! Lives lost.");
      }
      if (hasReachedEnd()) {
         System.out.println("Shape reached the end! Score increased.");
      }
   }

   public String getShapeType() {
      return shapeType;
   }

   public Rectangle2D.Double getBoundingRectangle() {
      return new Rectangle2D.Double(x, y, width, height);
   }
}



