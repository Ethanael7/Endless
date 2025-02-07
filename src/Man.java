import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Line2D;
import javax.swing.JPanel;

public class Man{

   private JPanel panel;
   private int x;
   private int y;
   private int topY;		// regenerated alien drawn at this y coordinate

   Ellipse2D.Double head;	// ellipse drawn for face

   private int dx;		// increment to move along x-axis
   private int dy;		// increment to move along y-axis

   private Color backgroundColour;
   private Dimension dimension;

   public Man (JPanel p, int xPos, int yPos) {
      panel = p;
      dimension = panel.getSize();
      backgroundColour = panel.getBackground ();

      x = xPos;
      y = yPos;
      topY = yPos;
	
      dx = 10;			// no movement along x-axis
      dy = 0;			// would like the alien to drop down
   }


   public void draw() {
      Graphics g = panel.getGraphics();
      Graphics2D g2 = (Graphics2D) g;
  
      // Draw the head
      head = new Ellipse2D.Double(x, y, 30, 45);
      g2.setColor(Color.WHITE); 
      g2.fill(head);      // color the head
      g2.setColor(Color.BLACK);    
      g2.draw(head);      // draw outline around head
  
      g2.setColor(Color.BLACK);
  
      // Draw the eyes
      Line2D.Double eye1 = new Line2D.Double(x+8, y+15, x+12, y+24);
      g2.draw(eye1);
  
      Line2D.Double eye2 = new Line2D.Double(x+20, y+24, x+24, y+15);
      g2.draw(eye2);
  
      // Draw the mouth
      Rectangle2D.Double mouth = new Rectangle2D.Double(x+9, y+33, 14, 3);
      g2.setColor(Color.GREEN);
      g2.fill(mouth);
  
      // Draw the body
      Rectangle2D.Double body = new Rectangle2D.Double(x+5, y+45, 20, 30); 
      g2.setColor(Color.BLUE);
      g2.fill(body);      
      g2.setColor(Color.BLACK);    
      g2.draw(body); 
      
      Line2D.Double arm1 = new Line2D.Double(x-5, y+50, x+5, y+50); // Left arm
    g2.draw(arm1);

    Line2D.Double arm2 = new Line2D.Double(x+25, y+50, x+35, y+50); // Right arm
    g2.draw(arm2);

    Line2D.Double leg1 = new Line2D.Double(x+5, y+75, x+5, y+90); // Left leg
    g2.draw(leg1);

    Line2D.Double leg2 = new Line2D.Double(x+15, y+75, x+15, y+90); // Right leg
    g2.draw(leg2);

    g.dispose();
  
    }
  


   public void erase () {
      Graphics g = panel.getGraphics ();
      Graphics2D g2 = (Graphics2D) g;


      g2.setColor (backgroundColour);
      g2.fill (new Rectangle2D.Double (x-10, y-10, 30+20, 45+20));

      g.dispose();
   }


   public void move() {

      if (!panel.isVisible ()) return;

      int panelHeight = panel.getHeight();

      x = x + dx;
      y = y + dy;
 
      if (y > panelHeight)
	  y = topY;		

   }


   public boolean isOnHead (int x, int y) {
      if (head == null)
      	  return false;

      return head.contains(x, y);
   }

}