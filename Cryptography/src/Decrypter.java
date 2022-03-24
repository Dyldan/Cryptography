import java.io.*;

/* @author Dylan Moreno
 * @version 2/18/2022
 *
 * This work complies with the JMU Honor Code.
 */

/**
 * Decrypts an encrypted message into the original message.
 */
public class Decrypter extends Encoder {

  private String ciphertext_file;
  private String output_file;
  private int a;
  private int b;

  /**
   * Constructor.
   *
   * @param args - command line args
   */
  public Decrypter(String[] args) {
    ciphertext_file = args[1];
    output_file = args[2];
    a = Integer.parseInt(args[3]);
    b = Integer.parseInt(args[4]);
  }

  /**
   * Decrypts the encryption.
   *
   * @throws IOException - exception
   */
  public void decrypt() throws IOException {

    // check for valid key pair
    if (!isValidPair(a, b)) {
      System.out.println("The key pair ({" + a + "}, {" + b + "}) is invalid" +
        ", please select another key.");
      return;
    }


    // decrypt and write decrypted message 1 byte at a time
    BufferedInputStream reader = new BufferedInputStream(new FileInputStream(ciphertext_file));
    BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(output_file));

    int c = reader.read();

    while (c != -1) {
      int decrypted = (modInverse(a, MOD) * (c + MOD - b)) % MOD;
      writer.write(decrypted);

      c = reader.read();
    }

    writer.close(); // close the file
  }
}
