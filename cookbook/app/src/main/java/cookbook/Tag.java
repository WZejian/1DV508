package cookbook;

/**
 * A class to represent a tag entity.
 */

public class Tag {
  
  // Attributes
  private int id;
  private String name;

  // Creator.
  public Tag() {
  }
  
  public Tag(int id, String name) {
    setId(id);
    setName(name);
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  /**
   * Checks if name is null and sets it. 
   * @param name - String
   */
  public void setName(String name) {
    if (name != null) {
      this.name = name;
    } else {
      this.name = "";
    } 
  }  
}
