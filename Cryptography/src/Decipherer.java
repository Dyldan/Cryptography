import java.io.*;
import java.util.ArrayList;

/* @author Dylan Moreno
 * @version 2/18/2022
 *
 * This work complies with the JMU Honor Code.
 */

/**
 * Deciphers an encrypted message by testing different key pairs and cross-checking the given message with a dictionary.
 */
public class Decipherer extends Encoder {

  private String cipher_file;
  private String output_file;
  private String dictionary_file;
  private int correctA;
  private int correctB;
  private String decipheredText;

  /**
   * Constructor.
   *
   * @param args - command line args
   */
  public Decipherer(String[] args) {
    cipher_file = args[1];
    output_file = args[2];
    dictionary_file = args[3];

    correctA = -1;
    correctB = -1;
    decipheredText = "";
  }

  /**
   * Deciphers the message.
   *
   * @return true if the message was able to be deciphered
   * @throws IOException - exception
   */
  public boolean decipher() throws IOException {

    BufferedInputStream cipher_reader = new BufferedInputStream(new FileInputStream(cipher_file));
    BufferedReader dict_reader;


    // read in the ciphertext file
    byte[] bytes = cipher_reader.readAllBytes();
    String allText = "";
    for (byte aByte : bytes) {
      allText += (char) aByte;
    }


    // attempt to find the key pair (a,b) that was used to encrypt the file
    StringBuilder decrypted = new StringBuilder();
    boolean foundKeys = false;

    for (int a = 1; a < MOD && !foundKeys; a+=2) {
      for (int b = 1; b < MOD && !foundKeys; b++) {

        dict_reader = new BufferedReader(new FileReader(dictionary_file));

        // get the 'deciphered' text
        for (int i = 0; i < allText.length(); i++) {
          decrypted.append((char) ((modInverse(a, MOD) * (allText.charAt(i) + MOD - b)) % MOD));
        }

        // get deciphered text
        String[] words = decrypted.toString().split(" ");
        ArrayList<String> longerWordsList = new ArrayList<>();
        String[] longerWords; // words that are >= 4 letters
        int numLongerWords = 0; // num words greater than 3
        for (int i = 0; i < words.length; i++) {
          // concactenate for later, *then* remove punctuation, change to lowercase
          decipheredText += words[i];
          if (i != words.length - 1)
            decipheredText += " ";
          words[i] = words[i].replaceAll("[^a-zA-Z]", "");
          words[i] = words[i].toLowerCase();
          if (words[i].length() > 3) {
            numLongerWords++;
            longerWordsList.add(words[i]);
          }
        }
        longerWords = new String[numLongerWords];
        for (int i = 0; i < longerWordsList.size(); i++) {
          longerWords[i] = longerWordsList.get(i);
        }

        // compare to dictionary
        String line = dict_reader.readLine();
        int num_matches = 0;
        while (line != null) {
          line = line.toLowerCase();
          for (int i = 0; i < longerWords.length; i++) {
            if (line.equals((longerWords[i]))) {
              num_matches++;
            }
          }
          line = dict_reader.readLine();
        }

        // check if there are enough valid words to be the correct key pair. (algorithm comletely made up by me)
        double numWords = (double) longerWords.length;
        double matches = (double) num_matches;
        if (matches / numWords >= 0.8) {
          foundKeys = true;
        }

        // set members correctA and correctB
        if (foundKeys) {
          correctA = a;
          correctB = b;
        } else {
          decipheredText = new String("");
        }

        dict_reader.close(); // close file
        decrypted = new StringBuilder(); // clear decrypted
      }

    }

    // write to file if the algorithm believes the correct key pair was found
    if (foundKeys) {
      FileWriter writer = new FileWriter(output_file);
      writer.write(correctA + " " + correctB + "\n");
      writer.write("DECIPHERED MESSAGE:\n");

      for (int i = 0; i < decipheredText.length(); i++) {
        writer.write(decipheredText.charAt(i));
      }

      // close files
      writer.close();
      cipher_reader.close();
      return true;
    }

    // close other files
    cipher_reader.close();
    return false;

  }
}
