package cookbook;

import static cookbook.DbConnector.runUpdateQuery;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;


public class User {
  private int id;
  private String name;
  private String login_name; 
  private HashMap<LocalDate, HashMap<Recipe, Integer>> weeklyDinners;
  private String hashedPassword;
  private boolean admin;

  public User() {
  }
  
  
  /**
   * Creator method that must take on user id, name, hash parametres.
   * @param id - pulled from db - int
   * @param name - String username
   * @param passHash - String hashed version of the password.
   */

  public User(int id, String name, String passHash, int admin, String loginName) {
    this.setId(id);
    this.setName(name);
    this.setHashedPassword(passHash);
    this.setAdmin(admin);
    this.setLoginName(loginName);
    this.weeklyDinners = new HashMap<LocalDate, HashMap<Recipe, Integer>>();
  }

  /**
   * Constructs a new user object.
   * 
   * @param id User id
   * @param name name of user
   * @param passHash password of user
   * @param admin is admin?
   * @param weeklyDinners user's weekly dinners
   */
  public User(
      int id, String name, String passHash, int admin, 
      HashMap<LocalDate, HashMap<Recipe, Integer>> weeklyDinners) {
    this.setId(id);
    this.setName(name);
    this.setHashedPassword(passHash);
    this.setAdmin(admin);
    this.weeklyDinners = weeklyDinners;
  }

  // ID
  public int getId() {
    return this.id;
  }

  private void setId(int id) {
    this.id = id;
  }

  // Name
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLoginName() {
    return this.login_name;
  }

  public void setLoginName(String loginName) {
    this.login_name = loginName;
  }

  public String getHashedPassword() {
    return this.hashedPassword;
  }

  public void setHashedPassword(String hashedPassword) {
    this.hashedPassword = hashedPassword;
  }

  //Admin
  private void setAdmin(int admin) {
    if (admin == 1) {
      this.admin = true;
    } else {
      this.admin = false;

    }
  }

  public boolean isAdmin() {
    return this.admin;
  }

  

  /**
   * Add a recipe as a weekly dinner.
   * Should be simpler? - Marek
   */
  public void addWeeklyDinner(Recipe recipe, LocalDate date, Integer yield) throws SQLException {
    
    String query = 
        "INSERT INTO weekly_dinner (user_id, day, recipe_id, yield) VALUES (?, ?, ?, ?);";
    runUpdateQuery(query, id, date.toString(), recipe.getId(), yield);

    if (! weeklyDinners.containsKey(date)) {
      HashMap<Recipe,Integer> hm = new HashMap<Recipe,Integer>();
      hm.put(recipe, yield);
      weeklyDinners.put(date, hm);
    } else {
      weeklyDinners.get(date).put(recipe, yield);
    }
    
  }
  //statement =con.prepareStatement("SELECT * from employee WHERE  userID = ?");


  /**
   * Delete a recipe from a user's weekly dinner lists.
   */
  public void deleteWeeklyDinner(Recipe recipe, LocalDate date) throws SQLException {
    if (weeklyDinners.containsKey(date)) {
      weeklyDinners.get(date).remove(recipe);
    }
    String query = "DELETE FROM weekly_dinner WHERE user_id = ? AND day = ? AND recipe_id = ?;";
    DbConnector.runUpdateQuery(query, this.getId(), date.toString(), recipe.getId());
  }
}
