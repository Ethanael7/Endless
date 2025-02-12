import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

public class Bat {

   private JPanel panel;
   private int x;
   private int y;
   private int width;
   private int height;

   private int dx;
   private int dy;

   private Rectangle2D.Double bat;

   private Color backgroundColour;
   private Dimension dimension;

   public Bat(JPanel p, int xPos, int yPos) {
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

      bat = new Rectangle2D.Double(x, y, width, height);
      g2.setColor(Color.RED);
      g2.fill(bat);

      g.dispose();
   }

   public void erase() {
      Graphics g = panel.getGraphics();
      Graphics2D g2 = (Graphics2D) g;

      g2.setColor(backgroundColour);
      g2.fill(new Rectangle2D.Double(x, y, width, height));

      g.dispose();
   }

   public void move(int direction) {
      if (!panel.isVisible()) return;

      dimension = panel.getSize();

      if (direction == 1) { // move up
         y = y - dy;
         if (y < 0)
            y = 0;
      } else if (direction == 2) { // move down
         y = y + dy;
         if (y + height > dimension.height)
            y = dimension.height - height;
      }
   }

   public boolean isOnBat(int x, int y) {
      if (bat == null)
         return false;

      return bat.contains(x, y);
   }

   public Rectangle2D.Double getBoundingRectangle() {
      return new Rectangle2D.Double(x, y, width, height);
   }
}
