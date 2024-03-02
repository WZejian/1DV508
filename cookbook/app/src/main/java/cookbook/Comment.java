package cookbook;

/**
 * For each comment.
 * meichen
 */
public class Comment {
  private int id;
  private int authorId;
  private String date;
  private String content;
  private int recipeId;

  public Comment() {    
  }

  public Comment(int authorId, String date, String comment, int recId) {
    this.authorId = authorId;
    this.content = comment;
    this.recipeId = recId;
    this.date = date;
  }

  public int getId() {
    return this.id;
  }

  public int getAuthorId() {
    return this.authorId;
  }

  public void setAuthorId(int authorId) {
    this.authorId = authorId;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getDate() {
    return this.date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public int getRecipeId() {
    return this.recipeId;
  }

  public void setRecipeId(int recipeId) {
    this.recipeId = recipeId;
  }

  public String getContent() {
    return this.content;
  }

  public void setContent(String content) {
    this.content = content;
  }

}
