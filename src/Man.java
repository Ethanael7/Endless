import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

public class Man extends Thread{

    private JPanel panel;
    private int x;
    private int y;
  
    

    public Man(int x, int y){
        this.x = x;
        this.y = y;
       
    }

   public void setPosition(int x, int y){
    this.x = x;
    this.y= y;
   }

   public void draw(Graphics2D g2){

    Ellipse2D.Double head = new Ellipse2D.Double(x + 5, y , 10, 10);
    g2.setColor(Color.BLACK);
    g2.fill(head);

    Rectangle2D.Double body = new Rectangle2D.Double(x+7,y+10,6,15);
    g2.setColor(Color.GREEN);
    g2.fill(body);

    Line2D.Double leftLeg = new Line2D.Double(x+7,y+25,x+3,y+35);
    Line2D.Double rightLeg = new Line2D.Double(x+13,y+25,x+17,y+35);
    g2.setColor(Color.BLACK);
    g2.draw(leftLeg);
    g2.draw(rightLeg);

   }

   public void move(int dx, int dy) {
    x += dx;
    y += dy;
}

   public int getX(){
    return x;
   }
   public int getY(){
    return y;
   }
}