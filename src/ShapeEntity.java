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
   private boolean alive = true;
   private Random random;
   private String shapeType; 
   private Ship ship;
   private int startX, startY;

   public ShapeEntity(JPanel p, int xPos, int yPos, int width, int height, String shapeType, Color shapeColor, Ship ship) {
      panel = p;
      dimension = panel.getSize();
      backgroundColour = panel.getBackground();
      this.shapeColor = shapeColor;
      this.shapeType = shapeType;
      this.ship = ship;
      this.startX = xPos;
      this.startY = yPos;
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
      if(!alive) return;

      // Drawing the shape onto the screen
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
      } else if (shapeType.equalsIgnoreCase("triangle")) {
         int[] xPoints = { x, x - width / 2, x + width / 2 };  
         int[] yPoints = { y, y + height, y + height };  
         g2.fillPolygon(xPoints, yPoints, 3);  
         g2.setColor(Color.BLACK);
         g2.drawPolygon(xPoints, yPoints, 3);  
      }

      g.dispose();
   }
   public void erase() {
      Graphics g = panel.getGraphics();
      Graphics2D g2 = (Graphics2D) g;
      g2.setColor(backgroundColour);
   
    
      if (shapeType.equals("triangle")) {
        
         g2.fill(new Rectangle2D.Double(x - width / 2 - 5, y - 5, width + 10, height + 10));  
      } else {
         g2.fill(new Rectangle2D.Double(x - 5, y - 5, width + 10, height + 10));  
      }
   
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
            sleep(50);
         }
      } catch (InterruptedException e) {}
   }

   public void stopRun() {
      isRunning = false;  
      this.interrupt();   
   }

   public boolean checkCollision(Bullet bullet){
      return alive && bullet.getBoundingRectangle().intersects(getBoundingRectangle());
   }

   public void destroy() {
      if (!alive) return; 
  
      Graphics g = panel.getGraphics();
      if (g != null) {
          erase(); 
          g.dispose();
      }
  
      alive = false;
      respawn(); 
  }
  

   public void respawn() {
      alive = true;
      x = startX;  
      y = startY;
      draw();
  }




   public boolean isAlives(){
      return alive;
   }

   public boolean collidesWithBat() {
      Rectangle2D.Double shapeBounds = new Rectangle2D.Double(x, y, width, height);
      return shapeBounds.intersects(ship.getBoundingRectangle());
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



