import java.io.*;

/* @author Dylan Moreno
 * @version 2/18/2022
 *
 * This work complies with the JMU Honor Code.
 */

/**
 * Encrypts a message using affine cipher.
 */
public class Encrypter extends Encoder {

  private final String plaintext_file;
  private final String output_file;
  private final int a;
  private final int b;

  /**
   * Constructor.
   *
   * @param args - command line args
   */
  public Encrypter(String[] args) {
    plaintext_file = args[1];
    output_file = args[2];
    a = Integer.parseInt(args[3]);
    b = Integer.parseInt(args[4]);
  }

  /**
   * Encrypt the message.
   *
   * @throws IOException - exception
   */
  public void encrypt() throws IOException {

    // check for valid key pair
    if (!isValidPair(a, b)) {
      System.out.println("The key pair ({" + a + "}, {" + b + "}) is invalid" +
        ", please select another key.");
      return;
    }


    // encrypt and write encrypted message 1 byte at a time
    BufferedInputStream reader = new BufferedInputStream(new FileInputStream(plaintext_file));
    BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(output_file));

    int m = reader.read();

    while (m != -1) {
      int encrypted = (a * m + b) % MOD;
      writer.write(encrypted);

      m = reader.read();
    }

    writer.close(); // close the file
  }
}
