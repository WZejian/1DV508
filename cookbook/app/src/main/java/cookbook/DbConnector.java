package cookbook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * This class is a static reference to establish a DB connection.
 * Connection can be modified from within the package, for example
 * when changing users.
 * More methods can be added to allow further manipulation. 
 * True status signifies successfull connection.
 */
public class DbConnector {

  public static Connection conn;
  static boolean status = false;
  static String error_msg = "";

  private static void setStatus(boolean status) {
    DbConnector.status = status;
  }

  public static boolean getStatus() {
    return DbConnector.status;
  }

  public static void setError(String error) {
    DbConnector.error_msg = error;
  }

  public static String getError() {
    return DbConnector.error_msg;
  }

  /**
   * A static method to connect to the DB.
   */
  public static void connect() {
    String connection = new String("jdbc:mysql://meichen.tech/cookbook?&useSSL=false");
    
    /**
     * Used for a local connection on Marek's computer for the presentation. Please keep!!!
     * Marek - please use the homebrew instance of the DB, not MAMP.
     * Use: brew services start mysql
     */
    //String connection = new String("jdbc:mysql://127.0.0.1:3306/cookbook?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
    try {
      DbConnector.conn = DriverManager.getConnection(connection, "aluminium", "aj7QuebU}}kX0AT");
      setStatus(true);
  
    } catch (SQLException e) {
      setError(e.toString());
      setStatus(false);
    }
  }


  /**
   * Run select query.
   * Please note, you, the programmer, must know how many parametres a query contains!!!
   */
  public static ResultSet runQuery(String query, Object... args) {
    try {
      PreparedStatement stmt = conn.prepareStatement(query);
      int column = 1;
      for (Object arg : args) {
        stmt.setObject(column, arg);
        column++;
      }
      ResultSet rs = stmt.executeQuery();
      return rs;
    } catch (SQLException e) {
      DbConnector.setError(e.toString());
      return null;
    }
  }

  /**
   * Run update query.
   * Please note, you, the programmer, must know how many parametres a query contains!!!
   * Can natively return an int if needed for debugging.
   */
  public static void runUpdateQuery(String query, Object... args) throws SQLException {
    PreparedStatement stmt = conn.prepareStatement(query);
    int column = 1;
    for (Object arg : args) {
      stmt.setObject(column, arg);
      column++;
    }
    stmt.executeUpdate();
  }
}
