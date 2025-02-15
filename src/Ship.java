import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

public class Ship {

   private JPanel panel;
   private int x;
   private int y;
   private int width;
   private int height;
   private int dx;
   private int dy;
   private Color backgroundColour;
   private Dimension dimension;

   public Ship(JPanel p, int xPos, int yPos) {
      panel = p;
      dimension = panel.getSize();
      backgroundColour = panel.getBackground();

      x = xPos;
      y = yPos;
      dx = 0;   
      dy = 20;  

      width = 30;  
      height = 30; 
   }

   public void draw() {
      Graphics g = panel.getGraphics();
      Graphics2D g2 = (Graphics2D) g;

      int[] xPoints = {x + width, x, x};  
      int[] yPoints = {y + height / 2, y, y + height};  

      Polygon bat = new Polygon(xPoints, yPoints, 3);

      g2.setColor(Color.RED);
      g2.fill(bat);

      g.dispose();
   }

   public int getSize(){
      return width+height;
   }

   public void erase() {
      Graphics g = panel.getGraphics();
      Graphics2D g2 = (Graphics2D) g;

      g2.setColor(backgroundColour);
      g2.fillRect(x, y, width, height);

      g.dispose();
   }

   public void move(int direction) {
      if (!panel.isVisible()) return;

      dimension = panel.getSize();

      if (direction == 1) { 
         y = y - dy;
         if (y < 0)
            y = 0;
      } else if (direction == 2) { 
         y = y + dy;
         if (y + height > dimension.height)
            y = dimension.height - height;
      }
   }

   public boolean isOnBat(int x, int y) {
      Polygon bat = new Polygon(
         new int[]{this.x + width, this.x, this.x}, 
         new int[]{this.y + height / 2, this.y, this.y + height}, 
         3
      );
      return bat.contains(x, y);
   }

   public int getX() {
      return x;
   }

   public int getY() {
      return y;   
   }

   public int getWidth() {
      return width;
   }

   public int getHeight() {   
      return height;
   }

   
   public Rectangle2D.Double getBoundingRectangle() {
      return new Rectangle2D.Double(x, y, width, height);
   }
}
