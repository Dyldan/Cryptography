/* @author Dylan Moreno
 * @version 2/18/2022
 *
 * This work complies with the JMU Honor Code.
 */

/**
 * Separate thread to update user about deciphering.
 */
public class LoadingThread extends Thread {

  private boolean print;

  /**
   * Constructor.
   */
  LoadingThread() {
    print = true;
  }

  /**
   * Run the thread.
   */
  public void run() {

    while (print) {
      try {
        System.out.println("\nLoading...");
        Thread.sleep(4500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * End the thread.
   */
  public void quit() {
    print = false;
    System.out.println("\nDeciphered!");
  }
}
