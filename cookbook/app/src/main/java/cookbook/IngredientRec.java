package cookbook;

/**
 * For ingredient that used by a specific recipe.
 */

public class IngredientRec extends Ingredient {
  private int userId;
  private double quantity;

  
  public IngredientRec() {

  }

  /**
   * Creator class for an Ingredient used by recipe.
   *
   * @param id - int
   * @param name - String
   * @param quantity - double
   * @param unit - Will be string/char
   * @throws IllegalArgumentException - when args are null
   */
  public IngredientRec(
      int id, String name, double quantity, String unit
  ) throws IllegalArgumentException {
    setId(id);
    setName(name);
    setUnit(unit);
    setQuantity(quantity);
  }

  /**
   * Used in addingredientfield.
   * @param name ingredient name
   * @param quantity ingredient quantity
   * @param unit ingredient unit
   */
  public IngredientRec(String name, double quantity, String unit) {
    this.setName(name);
    this.setQuantity(quantity);
    this.setUnit(unit);
  }

  /**
   * meichen, for shopping list user add an item.
   * @param id AI database id
   * @param userId user id
   * @param name ingredient name
   * @param quantity ingredient quantity
   * @param unit ingredient unit
   */
  public IngredientRec(int id, int userId, String name, double quantity, String unit) {
    this.setId(id);
    this.setUserId(userId);
    this.setName(name);
    this.setQuantity(quantity);
    this.setUnit(unit);
  }


  // Quantity
  public double getQuantity() {
    return this.quantity;
  }

  /**
   *  Checks if quantity is a positive int.
   * 
   * @param quantity - positive int.
   */
  public void setQuantity(double quantity) {
    if (quantity >= 0) {
      this.quantity = quantity;
    } else {
      throw new IllegalArgumentException();
    }
  }

  public int getUserId() {
    return this.userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

}
