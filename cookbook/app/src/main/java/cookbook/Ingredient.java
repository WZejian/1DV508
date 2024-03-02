package cookbook;

/**
 * For each ingredient.
 */
public class Ingredient {
  private int id;
  private String name;
  private String unit;
  
  /**
  public enum UnitType {  // will be fetched from database, just examples here.
    G,
    KG,
    TBSP,
    TSP,
    ML,
    L,
    CUP,
    PIECE,
    OUNCE;
  }
  */

  public Ingredient() {
  }

  /**
   *  Creator class for ingredient object.
   *
   * @param id - int
   * @param name - String
   * @param unit - Will be string/char
   */
  public Ingredient(
      int id, String name, String unit
  ) throws IllegalArgumentException {
    setId(id);
    setName(name);
    setUnit(unit);
  }


  // ID
  public int getId() {
    return this.id;
  }

  /**
   * Sets ingredient id.
   * @param id - int.
   */
  public void setId(int id) {
    if (id >= 0) {
      this.id = id;
    } else {
      throw new IllegalArgumentException();
    }
    
  }

  // Name
  public String getName() {
    return this.name;
  }

  /**
   * Checks to make sure name is not null.
   * Sets name
   *
   * @param name - String.
   */
  public void setName(String name) {
    if (name != null) {
      this.name = name;
    } else {
      throw new IllegalArgumentException();
    }
  }

  // Unit
  public String getUnit() {
    return this.unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }
}
