import java.io.*;

/* @author Dylan Moreno
 * @version 2/18/2022
 *
 * This work complies with the JMU Honor Code.
 */

/**
 * Command line utility for encrypting, decrypting, and deciphering.
 */
public class affine {

  /**
   * main.
   *
   * @param args - command line arguments
   * @throws IOException - exception
   */
  public static void main(String[] args) throws IOException {

    switch (args[0]) {
      case "encrypt":
        Encrypter encrypter = new Encrypter(args);
        encrypter.encrypt();
        break;
      case "decrypt":
        Decrypter decrypter = new Decrypter(args);
        decrypter.decrypt();
        break;
      case "decipher":
        LoadingThread thread = new LoadingThread(); // run a user-friendly loading message
        thread.start();
        Decipherer decipherer = new Decipherer(args);
        if (decipherer.decipher()) {
          thread.quit();
        }
        break;
    }

  }

}
