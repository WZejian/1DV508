package cookbook;

import java.math.BigInteger; 
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException; 

/**
 * Calculate SHA-256 hash value.
 */
public class PasswordEncode {

  /**
   * Accept a string as input and return an array of bytes.
   * https://www.geeksforgeeks.org/sha-256-hash-in-java/.
   */
  private static byte[] getSha(String input) throws NoSuchAlgorithmException { 
    MessageDigest md = MessageDigest.getInstance("SHA-256"); 
    // getBytes(): convert the encoding of string into the sequence of bytes
    // and keeps it in an array of bytes

    // digest(): calculate an input's message digest 
    return md.digest(input.getBytes(StandardCharsets.UTF_8)); 
  }
  
  private static String toHexString(byte[] hash) {
    // Signum function helps determine the sign of the real value function
    // +1: positive input values of the function
    // -1: negative input values of the function. 
    BigInteger number = new BigInteger(1, hash); 
    // Convert message digest into hex value 
    StringBuilder hexString = new StringBuilder(number.toString(16)); 
    // Pad with leading zeros
    while (hexString.length() < 32) { 
      // insert(): insert the string representation of the char argument 
      // into this sequence.
      hexString.insert(0, '0'); 
    } 
    return hexString.toString(); 
  }

  /**
   * Encode a user's password to a hashed string.
   */
  public static String passwordEncode(String input) throws NoSuchAlgorithmException {
    return PasswordEncode.toHexString(PasswordEncode.getSha(input));
  }
}

  