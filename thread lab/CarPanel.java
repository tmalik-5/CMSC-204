import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;

/**
   This component draws a car shape and moves it.
*/
public class CarPanel extends JComponent
{  
   private Car car1;
   private int x,y, delay;
   private CarQueue carQueue;
   private int direction;
	
   CarPanel(int x1, int y1, int d, CarQueue queue)
   {
      delay = d;
      x = x1;
      y = y1;
      car1 = new Car(x, y, this);
      carQueue = queue;
   }

   public void startAnimation()
   {
      class AnimationRunnable implements Runnable
      {
         public void run()
         {
            try
            {
               // keep the car moving forever
               while (true)
               {
                  // get the next direction from the queue
                  direction = carQueue.deleteQueue();

                  // we "try" this move first
                  int newX = x;
                  int newY = y;

                  // 0 = up, 1 = down, 2 = right, 3 = left
                  if (direction == 0)
                     newY = y - 10;
                  else if (direction == 1)
                     newY = y + 10;
                  else if (direction == 2)
                     newX = x + 10;
                  else if (direction == 3)
                     newX = x - 10;

                  // figure out panel size (for boundaries)
                  int panelWidth = getParent().getWidth();
                  int panelHeight = getParent().getHeight();

                  // approximate car size from Car.draw (60 wide, ~30 tall)
                  int carWidth = 60;
                  int carHeight = 30;

                  boolean hitsBoundary =
                        (newX < 0) ||
                        (newX + carWidth > panelWidth) ||
                        (newY < 0) ||
                        (newY + carHeight > panelHeight);

                  if (hitsBoundary)
                  {
                     // if the move would go out of bounds,
                     // go in the opposite direction instead
                     if (direction == 0)          // up -> go down
                        y = y + 10;
                     else if (direction == 1)     // down -> go up
                        y = y - 10;
                     else if (direction == 2)     // right -> go left
                        x = x - 10;
                     else if (direction == 3)     // left -> go right
                        x = x + 10;
                  }
                  else
                  {
                     // safe, just take the move
                     x = newX;
                     y = newY;
                  }

                  repaint();

                  // delay is given in seconds in constructor
                  Thread.sleep(delay * 1000);
               }
            }
            catch (InterruptedException exception)
            {
               // quietly exit if interrupted
            }
         }
      }
	      
      Runnable r = new AnimationRunnable();
      Thread t = new Thread(r);
      t.start();
   }
	
   public void paintComponent(Graphics g)
   {  
      Graphics2D g2 = (Graphics2D) g;

      car1.draw(g2,x,y);    
   }
}
