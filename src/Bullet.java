import java.awt.*;

public class Bullet {
    private int x, y;
    private int size;
    private int speed;
    private Color color;

    public Bullet(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.speed = 5;  // Default speed
        this.color = Color.BLACK;
    }

    public void move() {
        y -= speed;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, size, size);
    }

    public void erase(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(x, y, size, size);
    }

  
    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getSize() {
        return size;
    }

   
    public void setSpeed(int speed) {
        this.speed = speed;
    }
}





