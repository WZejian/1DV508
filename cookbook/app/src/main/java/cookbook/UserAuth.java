package cookbook;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAuth {
  private static boolean status = false;
  /**
   * This is a class to authenticate a user using cookbook.users table.
   *
   * @param usrName - User name String.
   * @param password - Password as a string. Hashing will be added.
   * @return - confirms if the user is authenticated or not. 
   */


  public static User authenticate(String usrName, String password) {
    // Hash password and try to authenticate.
    String hashPass;
    try {
      hashPass = PasswordEncode.passwordEncode(password);
    } catch (NoSuchAlgorithmException e) {
      hashPass = "";
      e.printStackTrace();
    }
    ResultSet auth = DbConnector.runQuery(
        "SELECT * FROM users WHERE name = ? and pass_hash = ?;",
        usrName,
        hashPass
      );
    User temp = UserAuth.createUser(auth);
    if (temp != null) {
      UserAuth.setStatus(true);
    }
    return temp;
  }


  private static User createUser(ResultSet auth) {
    User currentUser = null;
  
    try {
      while (auth.next()) {
        currentUser = new User(
            auth.getInt("id"),
            auth.getString("name"),
            auth.getString("pass_hash"),
            auth.getInt("admin"),
            auth.getString("login_name")
          );
      }
    } catch (SQLException e) {
      e.printStackTrace();
      DbConnector.setError(e.toString());
      return null;
    } catch (NullPointerException n) {
      return null;
    }

    return currentUser;

  }

  // Check if authorised
  public static boolean getStatus() {
    return UserAuth.status;
  }

  private static void setStatus(boolean status) {
    UserAuth.status = status;
  }
}