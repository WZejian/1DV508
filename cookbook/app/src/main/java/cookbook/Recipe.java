package cookbook;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Recipe {

  // Core attributes
  private int id;
  private String name;
  private ArrayList<IngredientRec> ingredients = new ArrayList<IngredientRec>();
  private String shortDesc;     // hovering on the image
  private List<String> longDesc;   // steps
  private String endLine;   // endline of recipe, can be empty
  private int author;    // changed to int as this is the data type for user ID on DB
  private String date;
  private int yield; // default number of servings

  // Optional attributes
  private ArrayList<Tag> tags = new ArrayList<Tag>();
  private int countOfStar;
  private ArrayList<Comment> comments = new ArrayList<Comment>();

  
  // private Picture image; - THIS IS THE PROPER ATTRIBUTE.
  private int image;


  // only one image for one recipe
  private boolean publicity;
  // True for public, False for private, thinking about removing it --meichen


  /**
   * A constructor of new empty recipe.
   * Meichen Ji.
   */
  public Recipe() {
  }

  /**
   * A new case of creator to test recipeCards. DELETE AFTER!!!
   */
  public Recipe(String name, int image) {
    this.setName(name);
    this.setImage(image);
  }

  /**
   * This is for create a new recipe.
   * Meichen Ji.
   * --
   * All values must be initiated for proper functioning!!!
   * MU
   */
  public Recipe(
      int id, String name,
      String shortDesc, String longDesc, String endLine,
      int author, Date date, int yield,  
      int countOfStar, int picId
  ) {
    // Core
    this.setId(id);
    this.setName(name);
    this.setIngredients(ingredients);
    this.setShortDesc(shortDesc);
    this.setLongDesc(longDesc);
    this.setEndLine(endLine);
    this.setAuthor(author);
    this.setDate(date);
    this.setYield(yield);

    // Optional
    this.setTags(tags);
    this.setComments(comments);
    this.setImage(picId);
    this.publicity = true;
  }


  public enum TagType {   // will be fetched from database, just examples here.
    MAINCOURSE,
    VEGAN,
    SWEET;
  }


  // ID
  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  // Name
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  // Tags
  public ArrayList<Tag> getTags() {
    return this.tags;
  }

  public void setTags(ArrayList<Tag> tags) {
    this.tags = tags;
  }

  /**
   * Add one tag to arraylist.
   * meichen
   * @param tag one tag
   */
  public void addTag(Tag tag) {
    this.tags.add(tag);
  }

  // Ingredients
  public ArrayList<IngredientRec> getIngredients() {
    return this.ingredients;
  }

  public void setIngredients(ArrayList<IngredientRec> ingredients) {
    this.ingredients = ingredients;
  }

  // Yield
  public int getYield() {
    return this.yield;
  }

  public void setYield(int yield) {
    this.yield = yield;
  }

  // Short Desc
  public String getShortDesc() {
    return this.shortDesc;
  }

  public void setShortDesc(String shortDesc) {
    this.shortDesc = shortDesc;
  }

  // Long Desc
  public List<String> getLongDesc() {
    return this.longDesc;
  }

  public void setLongDesc(String longDesc) {
    String[] lst = longDesc.split(";");
    this.longDesc = Arrays.asList(lst);
  }

  // End Line
  public String getEndLine() {
    return this.endLine;
  }

  public void setEndLine(String endLine) {
    this.endLine = endLine;
  }

  // Returns User Id for the author
  // use fileio.getRecAuthor() for author's name in print out.
  public int getAuthor() {
    return this.author;
  }

  public void setAuthor(int author) {
    this.author = author;
  }

  // Date
  public String getDate() {
    return this.date;
  }

  public void setDate(Date date) {
    this.date = date.toString();
  }

  // Stars
  public int getStars() {
    return this.countOfStar;
  }


  /**
   * Add 1 to the count of star every time a user adds the recipe to his favorites.
   */
  public void addStar() {
    this.countOfStar++;
  }


  /**
   * Reduce 1 from the count of star every time a user unstars a recipe from his favorites.
   */
  public void subStar() {
    this.countOfStar--;
  }

  // Comments
  public ArrayList<Comment> getComments() {
    return this.comments;
  }

  public void setComments(ArrayList<Comment> comments) {
    this.comments = comments;
  }

  // Picture
  public int getImage() {
    return this.image;
  }

  public void setImage(int imageid) {
    this.image = imageid;
  }

  // Publicity
  public boolean isPublic() {
    return this.publicity;
  }

  public boolean getPublicity() {
    return this.publicity;
  }

  public void setPublicity(boolean publicity) {
    this.publicity = publicity;
  }

  
}
