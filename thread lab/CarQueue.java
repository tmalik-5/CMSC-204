import java.util.LinkedList;
import java.util.Queue;

public class CarQueue
{
   private Queue<Integer> queue;

   public CarQueue()
   {
      queue = new LinkedList<Integer>();

      // put 5-6 starting directions in the queue
      for (int i = 0; i < 6; i++)
      {
         int dir = (int)(Math.random() * 4); // 0,1,2,3
         queue.add(dir);
      }
   }

   /**
    * Adds 0,1,2 or 3 to queue
    *  0 = up
    *  1 = down
    *  2 = right
    *  3 = left
    *
    * This starts a thread that keeps adding random directions.
    */
   public void addToQueue()
   {
      class AddRunnable implements Runnable
      {
         public void run()
         {
            try
            {
               while (true)
               {
                  int dir = (int)(Math.random() * 4); // 0-3
                  queue.add(dir);

                  // slow it down a bit so it doesn't spam the queue
                  Thread.sleep(500);  // 0.5 seconds
               }
            }
            catch (InterruptedException e)
            {
               // if interrupted, just stop quietly
            }
         }
      }

      Runnable r = new AddRunnable();
      Thread t = new Thread(r);
      t.start();
   }

   // returns the next direction from the queue
   public Integer deleteQueue()
   {
      if (queue.isEmpty())
      {
         // shouldn't really happen since we keep adding,
         // but just in case, default to "right"
         return 2;
      }
      return queue.remove();
   }
}
