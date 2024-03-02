package cookbook;

import java.security.NoSuchAlgorithmException;

public class test2 {
  public static void main(String[] args) {
    String passHash = "";
    try {
      passHash = PasswordEncode.passwordEncode("AluminiumPork");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    System.out.println("\n" + passHash + "\n");
  }
}
