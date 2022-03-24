/* @author Dylan Moreno
 * @version 2/18/2022
 *
 * This work complies with the JMU Honor Code.
 */

/**
 * Parent class for Encrypter, Decrypter, and Decipherer.
 */
public abstract class Encoder {

  protected final int MOD = 128;

  /**
   * Get the mod inverse (reference from GeeksForGeeks).
   *
   * @param a - a
   * @param m - m
   * @return the mod inverse
   */
  protected int modInverse(int a, int m) {

    int m0 = m;
    int y = 0, x = 1;

    if (m == 1)
      return 0;

    while (a > 1) {

      int q = a / m;

      int t = m;

      m = a % m;
      a = t;
      t = y;

      y = x - q * y;
      x = t;
    }

    // make x positive
    if (x < 0)
      x += m0;

    return x;
  }

  /**
   * @param a - a
   * @param b - b
   * @return true if this key pair is valid (if its mult inverse modulo = 1)
   */
  protected boolean isValidPair(int a, int b) {

    return egcd(a, MOD) == 1 && egcd(a, b) == 1;
  }

  /**
   * Provided by Bowers. Determines if a and b are valid pairs by checking their gcd.
   *
   * @param a - a
   * @param b - b
   * @return the gcd
   */
  protected int egcd(int a, int b) {

    int s = 1;
    int t = 0;
    int u = 0;
    int v = 1;

    while (b != 0) {
      int q = a / b;
      int c = b;
      b = a % b;
      a = c;
      int old_s = s;
      int old_t = t;
      int old_u = u;
      int old_v = v;
      s = old_u;
      t = old_v;
      u = old_s - old_u * q;
      v = old_t - old_v * q;
    }

    if (a == 1) {
      return 1;
    }

    return -1; // if a,b are not relative prime

  }
}
