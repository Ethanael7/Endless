import javax.swing.JPanel;

public class GamePanel extends JPanel {

   Bat bat;
   Alien alien;

   public GamePanel() {
      bat = null;
      alien = null;
   }

   public void createGameEntities() {
      bat = new Bat(this, 50, 350);  // Initialize bat starting at the left side, adjust y position as needed
      alien = new Alien(this, 200, 10, bat);
   }

   public void drawGameEntities() {
      if (bat != null) {
         bat.draw();
      }
   }

   public void updateGameEntities(int direction) {
      if (bat == null)
         return;

      bat.erase();
      bat.move(direction);
   }

   public void dropAlien() {
      if (alien != null) {
         alien.start();
      }
   }

   public boolean isOnBat(int x, int y) {
      if (bat != null)
         return bat.isOnBat(x, y);
      else
         return false;
   }
}
