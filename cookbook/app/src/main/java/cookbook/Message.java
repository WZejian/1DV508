package cookbook;

import java.sql.SQLException;

public class Message {
  private int id;
  private int senderId;
  private int reciverId;
  private Recipe recipe;
  private String date;
  private String content;

  /**
   * This is a creator for a message class.
   * @param sender - user ID for the sender
   * @param receiver - user ID for the receiver
   * @param date - date as a string
   * @param content - message content.
   */
  public Message(int id, int sender, int receiver, int recipeId, String date, String content) {
    this.setMessageId(id);
    this.setSenderId(sender);
    this.setReciverId(receiver);
    this.setDate(date);
    this.setContent(content);
    this.setRecipe(recipeId);
  }


  public int getSenderId() {
    return this.senderId;
  }

  public void setSenderId(int senderId) {
    this.senderId = senderId;
  }

  public int getReciverId() {
    return this.reciverId;
  }

  private void setReciverId(int reciverId) {
    this.reciverId = reciverId;
  }

  public String getDate() {
    return this.date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getContent() {
    return this.content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public int getMessageId() {
    return this.id;
  }

  private void setMessageId(int id) {
    this.id = id;
  }

  private void setRecipe(int recipeId) {
    
    try {
      this.recipe = Databaseio.getRecipeObj(recipeId);
    } catch (SQLException e) {
      this.recipe = new Recipe();
      e.printStackTrace();
    }
  }

  public Recipe getRecipe() {
    return this.recipe;
  }

  
}
