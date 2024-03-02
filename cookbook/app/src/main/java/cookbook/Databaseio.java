package cookbook;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * For read and update database.
 */
public class Databaseio {
  /**
   * This is inserting a new line in database, table recipe.
   * Meichen Ji
   * 
   * @param re A new Recipe.
   */
  public static void createNewRecipe(Recipe re) throws SQLException {
    List<String> longDes = re.getLongDesc();
    String detailedDesc = "";
    for (String step : longDes) {
      detailedDesc += step;
      detailedDesc += "; ";
    }
    detailedDesc = detailedDesc.substring(0, detailedDesc.length() - 2);

    // For now image is null.
    String insertQuery1 = String.format(
        "INSERT INTO recipes (name, short_desc, long_desc, "
            + "endline, date_created, author, stars, default_servings, image)"
            + " VALUES(?, ?, ?, ?, curdate(), ?, ?, ?, ?);");

    DbConnector.runUpdateQuery(insertQuery1,
        re.getName(),
        re.getShortDesc(),
        detailedDesc,
        re.getEndLine(),
        re.getAuthor(),
        0,
        re.getYield(),
        re.getImage());
  }

  public static int getLastId() throws SQLException {
    int temp = -1;
    String selectQuery1 = "SELECT LAST_INSERT_ID();";
    ResultSet rs = DbConnector.runQuery(selectQuery1);
    while (rs.next()) {
      temp = rs.getInt("LAST_INSERT_ID()");
    }
    return temp;
  }

  public static void addRecTags(Recipe re) throws SQLException {
    ArrayList<Tag> tags = re.getTags();
    // Check if tags exist and add.
    if (tags.size() > 0) {

      for (Tag tag : tags) { // add tags
        int tagId = tag.getId();
        // Check if tag has real id or placeholder
        if (tagId == -1) {
          // Add a custom tag to DB
          DbConnector.runUpdateQuery(
              """
                  INSERT INTO tags (name)
                  VALUES (?);
                  """,
              tag.getName());

          // Update the tag id according to DB
          ResultSet rs = DbConnector.runQuery(
              "SELECT LAST_INSERT_ID();");
          while (rs.next()) {
            tagId = rs.getInt("LAST_INSERT_ID()");
          }
        }
        DbConnector.runUpdateQuery(
            """
                INSERT INTO recipe_tags(recipe_id, tag_id)
                VALUES (?, ?);
                 """,
            re.getId(),
            tagId);
      }
      // If all tags are added successfuly, commit changes and reenable autocommit.
    }
  }

  /**
   * Add ingredients that already have assigned ID.
   * 
   * @param ingredients - IngredientRec with an ID.
   * @param recipe_id   - recipe ID.
   */
  public static void addRecIng(ArrayList<IngredientRec> ingredients, int recipe_id) throws SQLException {
    String insertQuery3 = "INSERT INTO recipe_ingredient(recipe_id, "
        + "ingredient_id, quantity) VALUES(?, ?, ?);";
    for (IngredientRec ing : ingredients) {
      ResultSet rs = DbConnector.runQuery(
          "SELECT id FROM ingredients WHERE name = ?;", ing.getName());
      while (rs.next()) {
        ing.setId(rs.getInt("id"));
      }
      DbConnector.runUpdateQuery(insertQuery3, recipe_id, ing.getId(), ing.getQuantity());
    }
  }

  public static void addUserIng(Ingredient ingredient) throws SQLException {
    DbConnector.runUpdateQuery(
        """
            INSERT INTO ingredients (name, unit)
            VALUES (?, ?);
            """,
        ingredient.getName(),
        ingredient.getUnit());
  }

  /**
   * This is adding a new comment.
   * meichen.
   * 
   * @param com the new comment.
   */
  public static void addComment(Comment com) {
    try {
      DbConnector.runUpdateQuery(
          "INSERT INTO comments(content, date_created, author, recipe_id) VALUES(?,CURDATE(),?,?);",
          com.getContent(), com.getAuthorId(), com.getRecipeId());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * This is editing a comment.
   * meichen.
   * 
   * @param com        the old comment.
   * @param newContent new content.
   */
  public static void editComment(Comment com, String newContent) {
    try {
      DbConnector.runUpdateQuery(
          "UPDATE comments SET content=? WHERE id=?;",
          newContent, com.getId());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * This is removing a comment.
   * meichen.
   * 
   * @param com the comment that is needed to be removed.
   */
  public static void removeComment(Comment com) {
    try {
      DbConnector.runUpdateQuery("DELETE FROM comments WHERE id = ?;", com.getId());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * This is to do fuzzy search.
   * as long as the recipe name contains the given name, the recipe will be
   * returned.
   * meichen
   * 
   * @param line content to search.
   * @return arraylist of recipe_ids thats fits the requirements.
   */
  public static ArrayList<Integer> fuzzySearch(String line) {
    ArrayList<Integer> recipeIds = new ArrayList<>();
    String selectQuery = "SELECT id FROM recipes WHERE name LIKE '%?%';";
    try {
      ResultSet rs = DbConnector.runQuery(selectQuery, line);
      while (rs.next()) {
        int recipeId = rs.getInt("id");
        recipeIds.add(recipeId);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return recipeIds;
  }

  /**
   * This method check database for a recipe author.
   * 
   * @param id - user id that determines who wrote the recipe.
   * @return name of the author.
   */
  public static String useridToUsername(int id) throws SQLException {
    String author = "N/A";
    ResultSet rs = DbConnector.runQuery(
        "SELECT login_name FROM users WHERE id=?;",
        id);

    while (rs.next()) {
      return rs.getString("login_name");
    }
    return author;
  }

  /**
   * This is used to build a list of all recipes.
   * 
   * @return - all recipes as an ArrayList.
   * @throws SQLException - if result set is null.
   */
  public static ArrayList<Recipe> getAllRecipes() throws SQLException {
    // All recipes stored here
    ArrayList<Recipe> allRecipes = new ArrayList<>();

    // Fetching from DB
    ResultSet rs = DbConnector.runQuery(
        "SELECT id FROM recipes");

    // id, name, short_desc, long_desc, endline, date, author, stars,
    // default_servings
    while (rs.next()) {
      Recipe recipe = Databaseio.getRecipeObj(rs.getInt("id"));
      allRecipes.add(recipe);
    }

    return allRecipes;
  }

  /**
   * This is used to build a list of my recipes.
   * 
   * @return - my recipes as an ArrayList.
   * @throws SQLException - if result set is null.
   */
  public static ArrayList<Recipe> getMyRecipes(int authorId) throws SQLException {
    // All recipes stored here
    ArrayList<Recipe> myRecipes = new ArrayList<>();
    ResultSet rs = DbConnector.runQuery(
        "SELECT id FROM recipes WHERE author = ?",
        authorId);
    while (rs.next()) {
      Recipe recipe = getRecipeObj(rs.getInt("id"));
      myRecipes.add(recipe);
    }
    return myRecipes;
  }

  /**
   * Extract the information of a user's weekly dinner lists from database.
   * Store it as a result set.
   */
  public static ResultSet extractWeeklyDinners(int userId, LocalDate localDate) {
    String query = "SELECT recipe_id, yield FROM weekly_dinner WHERE user_id = ? AND day = ?;";
    ResultSet rs = DbConnector.runQuery(query, userId, localDate.toString());
    return rs;
  }

  /**
   * Convert a result set to hash map.
   */
  public static HashMap<Integer, Integer> resultSetToHashMap(ResultSet rs) throws SQLException {

    // Create an empty hash map.
    HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();

    // Retrieve the index of the two coloumns "recipe_id" and "yield".
    // Iterate the result set.
    while (rs.next()) {
      int recipeId = rs.getInt("recipe_id");
      int yield = rs.getInt("yield");
      // recipeIdPerRow = rs.getInt(recipeId);
      // yieldPerRow = rs.getInt(yield);
      hashMap.put(recipeId, yield);
    }
    return hashMap;
  }

  /**
   * You give me the recipe ID, I give you recipe object.
   * FINISHED
   * meichen
   * 
   * @param recipeId the given recipeid
   * @return
   */
  public static Recipe getRecipeObj(int recipeId) throws SQLException {
    Recipe re = new Recipe();
    String selectRe = "SELECT * FROM recipes WHERE id = ?;";
    ResultSet rs = DbConnector.runQuery(selectRe, recipeId);
    while (rs.next()) {
      re = new Recipe(
          rs.getInt("id"),
          rs.getString("name"),
          rs.getString("short_desc"),
          rs.getString("long_desc"),
          rs.getString("endline"),
          rs.getInt("author"),
          rs.getDate("date_created"),
          rs.getInt("default_servings"),
          rs.getInt("stars"),
          rs.getInt("image"));
    }

    // tags
    ArrayList<Integer> tagIds = new ArrayList<>();
    String selectTagid = "SELECT tag_id FROM recipe_tags WHERE recipe_id = ?;";
    ResultSet rs2 = DbConnector.runQuery(selectTagid, recipeId);
    while (rs2.next()) {
      int tagId = rs2.getInt("tag_id");
      tagIds.add(tagId);
    }
    for (int tagid : tagIds) {
      String selectTagname = "SELECT name FROM tags WHERE id = ?;";
      ResultSet rs3 = DbConnector.runQuery(selectTagname, tagid);
      while (rs3.next()) {
        Tag oneTag = new Tag(tagid, rs3.getString("name"));
        re.addTag(oneTag);
      }
    }

    // ingredient id & quantity
    ArrayList<IngredientRec> ingredients = new ArrayList<>();
    String selectIngreid = "SELECT * FROM recipe_ingredient WHERE recipe_id = ?;";
    ResultSet rs4 = DbConnector.runQuery(selectIngreid, recipeId);
    while (rs4.next()) {
      IngredientRec ingre = new IngredientRec();
      ingre.setId(rs4.getInt("ingredient_id"));
      ingre.setQuantity(rs4.getDouble("quantity"));
      ingredients.add(ingre);
    }
    // ingredient name & unit
    for (IngredientRec ingre : ingredients) {
      int ingreId = ingre.getId();
      String selectIngrename = "SELECT name, unit FROM ingredients WHERE id = ?;";
      ResultSet rs5 = DbConnector.runQuery(selectIngrename, ingreId);
      while (rs5.next()) {
        ingre.setName(rs5.getString("name"));
        ingre.setUnit(rs5.getString("unit"));
      }
    }
    re.setIngredients(ingredients);

    // comments
    ArrayList<Comment> commentlst = new ArrayList<>();
    ResultSet rsComment = DbConnector.runQuery("SELECT * FROM comments WHERE recipe_id=?;", recipeId);
    while (rsComment.next()) {
      Comment oneComment = new Comment();
      oneComment.setAuthorId(rsComment.getInt("author"));
      oneComment.setContent(rsComment.getString("content"));
      Date curDate = rsComment.getDate("date_created");
      oneComment.setDate(curDate.toString());
      oneComment.setRecipeId(rsComment.getInt("recipe_id"));
      oneComment.setId(rsComment.getInt("id"));
      commentlst.add(oneComment);
    }
    re.setComments(commentlst);

    return re;
  }

  /**
   * Check if a recipe has been added to a specific date in the user's calender.
   */
  public static boolean dinnerExistCheck(User currentUser, LocalDate localDate, int recipeid) throws SQLException {

    int userid = currentUser.getId();
    ResultSet rs = extractWeeklyDinners(userid, localDate);
    HashMap<Integer, Integer> hm = resultSetToHashMap(rs);
    if (hm.containsKey(recipeid)) {
      return true;
    } else {
      return false;
    }

  }

  /**
   * You give me user id, I give you list of favorite recipe ids.
   * meichen
   * 
   * @param userid user's id
   * @return arraylist of favorite recipeid
   */
  public static ArrayList<Integer> getFavIds(int userid) {
    ArrayList<Integer> favlst = new ArrayList<>();
    String selectRe = "SELECT recipe_id FROM favourites WHERE user_id=?;";
    ResultSet rs = DbConnector.runQuery(selectRe, userid);
    try {
      while (rs.next()) {
        int reId = rs.getInt("recipe_id");
        favlst.add(reId);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return favlst;
  }

  /**
   * You give me user id and recipe id, I unstar it (if it is stared).
   * meichen
   * 
   * @param userId   user id
   * @param recipeId recipe id
   */
  public static void unStar(int userId, int recipeId) {
    String deleFav = "DELETE FROM favourites WHERE user_id=? AND recipe_id=?;";
    try {
      DbConnector.runUpdateQuery(deleFav, userId, recipeId);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * You give me user id and recipe id, I star it.
   * meichen
   * 
   * @param userId   user id
   * @param recipeId recipe id
   */
  public static void star(int userId, int recipeId) {
    String insertFav = "INSERT INTO favourites(user_id, recipe_id) VALUES (?,?);";
    try {
      DbConnector.runUpdateQuery(insertFav, userId, recipeId);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * You give me user id and recipe id, I tell you if it is stared.
   * 
   * @param userId   user id
   * @param recipeId recipe id
   * @return true means already star, false means not star yet.
   */
  public static boolean isStar(int userId, int recipeId) {
    String selectFav = "SELECT * FROM favourites WHERE user_id=? AND recipe_id=?;";
    ResultSet rs = DbConnector.runQuery(selectFav, userId, recipeId);

    try {
      while (rs.next()) {
        return true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return false;
  }

  /**
   * This method fetches all messages received by a User.
   * 
   * @param receiver - a User object (current User).
   * @return - ArrayList of all message objects
   */
  public static ArrayList<Message> getAllMessages(User receiver) {
    ArrayList<Message> allMess = new ArrayList<Message>();
    ResultSet rs = DbConnector.runQuery(
        "SELECT * FROM messages WHERE receiver_id = ?;",
        receiver.getId());
    try {
      while (rs.next()) {
        Message temp = new Message(
            rs.getInt("id"),
            rs.getInt("sender_id"),
            receiver.getId(),
            rs.getInt("recipe_id"),
            rs.getDate("date_sent").toString(),
            rs.getString("content"));
        allMess.add(temp);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return allMess;
    }
    return allMess;
  }

  /**
   * SQL delete all items in shopping list.
   * 
   * @param userId user id
   */
  public static void shopDeleteAll(int userId) {
    try {
      String clear = "DELETE FROM shoping_list WHERE user_id=?;";
      DbConnector.runUpdateQuery(clear, userId);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * SQL delete selected row.
   * 
   * @param userId userID
   */
  public static void shopDeleteRow(int id, int userId) {
    try {
      String deleteRow = "DELETE FROM shoping_list WHERE id=? AND user_id=?";
      DbConnector.runUpdateQuery(deleteRow, id, userId);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * SQL add one row.
   * 
   * @param userId   user id
   * @param ingrName ingredient name
   * @param ingrQuan ingredient quantity
   * @param ingrUnit ingredient unit
   */
  public static void shopAddRow(int userId, String ingrName, Double ingrQuan, String ingrUnit) {
    ResultSet rs = DbConnector.runQuery("SELECT * FROM shoping_list WHERE user_id=? AND ingredient_name=? AND unit=?;",
        userId, ingrName, ingrUnit);
    int exist = 0;
    Double oldQuan = 0.0;
    try {
      while (rs.next()) {
        exist += 1;
        oldQuan = rs.getDouble("quantity");
      }
    } catch (SQLException e1) {
      e1.printStackTrace();
    }
    if (exist == 0) {
      try {
        DbConnector.runUpdateQuery(
            "INSERT INTO shoping_list(user_id, ingredient_name, quantity, unit) VALUES (?,?,?,?);",
            userId, ingrName, ingrQuan, ingrUnit);
      } catch (SQLException e2) {
        e2.printStackTrace();
      }
    } else {
      try {
        Double newQuan = oldQuan + ingrQuan;
        DbConnector.runUpdateQuery(
            "UPDATE shoping_list SET quantity=? WHERE user_id=? AND ingredient_name=? AND unit=?;",
            newQuan, userId, ingrName, ingrUnit);
      } catch (SQLException e2) {
        e2.printStackTrace();
      }
    }
  }

  /**
   * SQL check if user exist. Used in share function.
   */
  public static int checkUserExist(String username) {
    ResultSet rs = DbConnector.runQuery("SELECT * FROM users WHERE name=?;",
        username);
    try {
      while (rs.next()) {
        return rs.getInt("id");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return -1;
  }

  /**
   * Used in share function.
   * 
   * @param sender   sender's id
   * @param receiver receiver's id
   * @param message  content
   */
  public static void addMessage(int sender, int receiver, int recipeId, String message) {
    try {
      DbConnector.runUpdateQuery(
          "INSERT INTO messages(sender_id, receiver_id, recipe_id, date_sent, content) VALUES (?,?,?,curdate(),?);",
          sender, receiver, recipeId, message);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Used in admin page, check if user exists.
   * 
   * @param username the username
   * @return
   */
  public static boolean usernameExist(String username) {
    Boolean userExist = false;
    ResultSet rsCheck = DbConnector.runQuery(
        "SELECT * FROM users WHERE name=?;",
        username);
    try {
      while (rsCheck.next()) {
        userExist = true;
      }
    } catch (SQLException e1) {
      e1.printStackTrace();
    }
    return userExist;
  }

  /**
   * You give me an username, I give you id.
   * 
   * @param username
   * @return -1 if name not exist
   */
  public static int usernameToid(String username) {
    ResultSet rsCheck = DbConnector.runQuery(
        "SELECT * FROM users WHERE name=?;",
        username);
    try {
      while (rsCheck.next()) {
        return rsCheck.getInt("id");
      }
    } catch (SQLException e1) {
      e1.printStackTrace();
    }
    return -1;
  }

  /**
   * Update userName, please make sure the newname is unique.
   * 
   * @param userId
   * @param newname
   */
  public static void usernameUpdate(int userId, String newname) {
    try {
      DbConnector.runUpdateQuery(
          "UPDATE users SET name=? WHERE id=?;",
          newname, userId);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Update password.
   * 
   * @param userId
   */
  public static void passwordUpdate(int userId, String newpass) {
    // Password hashing thanks to Jiaxin
    // newpass = ...
    try {
      String passHash = PasswordEncode.passwordEncode(newpass);
      DbConnector.runUpdateQuery(
          "UPDATE users SET pass_hash=? WHERE id=?;",
          passHash,
          userId);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Update nickname.
   * 
   * @param userId
   * @param newname
   */
  public static void nicknameUpdate(int userId, String newname) {
    try {
      DbConnector.runUpdateQuery(
          "UPDATE users SET login_name=? WHERE id=?;",
          newname, userId);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Used in admin, create a new user.
   * 
   * @param username
   * @param password
   */
  public static void createNewUser(String username, String password, String nickname) {
    // PasswordEncode.java, by Jiaxin
    // password = ...
    try {
      String passHash = PasswordEncode.passwordEncode(password);
      DbConnector.runUpdateQuery(
          "INSERT INTO users(name,pass_hash,admin,login_name) VALUES (?,?,?,?);",
          username,
          passHash,
          0,
          nickname
          );
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * get admin status by username, make sure the username exists before using
   * this.
   * 
   * @param username
   * @return
   */
  public static int usernameToAdminStatus(String username) {
    int adminStatus = 0;
    ResultSet rs = DbConnector.runQuery("SELECT * FROM users WHERE name=?;", username);
    try {
      while (rs.next()) {
        adminStatus = rs.getInt("admin");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return adminStatus;
  }

  /**
   * Change the admin column of user table.
   * 
   * @param username
   * @param status   make sure use 0 or 1
   */
  public static void adminUpdate(String username, int status) {
    try {
      DbConnector.runUpdateQuery(
          "UPDATE users SET admin=? WHERE name=?;",
          status, username);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void deleteRecipe(Recipe rec) throws SQLException {
    try {
      // Execute as a transaction
      DbConnector.conn.setAutoCommit(false);

      // Delete the recipe that will cascade through other tables
      DbConnector.runUpdateQuery(
          "DELETE FROM recipes WHERE id = ?",
          rec.getId());

      // Delete image first as no foreign key
      DbConnector.runUpdateQuery(
          "DELETE FROM images WHERE image_id = ?;",
          rec.getImage());

      // Commit changes
      DbConnector.conn.commit();
      DbConnector.conn.setAutoCommit(true);

    } catch (SQLException e) {
      // Revert changes if it fails
      DbConnector.conn.rollback();
      DbConnector.conn.setAutoCommit(true);
      throw e;
    }
  }

  /**
   * used in admin, delete any user by user name.
   * 
   * @param username the name col in database
   */
  public static void deleteUser(String username) {
    try {
      DbConnector.runUpdateQuery(
          "DELETE FROM users WHERE name=?;",
          username);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static ArrayList<Comment> fetchComments(int recipeId) {
    ArrayList<Comment> commentlst = new ArrayList<>();
    ResultSet rs = DbConnector.runQuery(
        "SELECT * FROM comments WHERE recipe_id=?;",
        recipeId);
    try {
      while (rs.next()) {
        Comment com = new Comment();
        com.setId(rs.getInt("id"));
        com.setContent(rs.getString("content"));
        Date dateCreated = rs.getDate("date_created");
        com.setDate(dateCreated.toString());
        com.setAuthorId(rs.getInt("author"));
        com.setRecipeId(rs.getInt("recipe_id"));
        commentlst.add(com);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return commentlst;
  }

  public static User useridToUserObj(int userid) {
    User newUser = new User();
    ResultSet rs = DbConnector.runQuery("SELECT * FROM users WHERE id=?;", userid);
    try {
      while (rs.next()) {
        newUser = new User(
            userid,
            rs.getString("name"),
            rs.getString("pass_hash"),
            rs.getInt("admin"),
            rs.getString("login_name"));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return newUser;
  }

}
