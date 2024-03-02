package cookbook.mainmenu.rightsidecontent.scenes.recipeview;

import cookbook.IngredientRec;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class IngredientField {
  
  private HBox main = new HBox();
  private HBox amountUnitBox = new HBox();

  // Yields
  private Label amountLabel;
  private double defaultYield;
  private double yieldOnePerson;
  private double currentYield;

  /**
   * Ingredient Field.
   *
   * @param ingredient Ingredient obj
   * @param recipeYield Amount used in recipe
   */
  public IngredientField(IngredientRec ingredient, int recipeYield) {

    defaultYield = recipeYield;
    yieldOnePerson = ingredient.getQuantity() / defaultYield;

    Label nameLabel = new Label(ingredient.getName());
    nameLabel.setId("name-label");

    // Amount label
    amountLabel = new Label(Double.toString(ingredient.getQuantity()));
    amountLabel.setId("amount-label");

    Label unitLabel = new Label(ingredient.getUnit());
    unitLabel.setId("unit-label");

    amountUnitBox.setPadding(new Insets(2, 5, 2, 5));
    amountUnitBox.setAlignment(Pos.CENTER);
    amountUnitBox.setSpacing(5);
    amountUnitBox.getChildren().addAll(amountLabel, unitLabel);
    amountUnitBox.setId("amount-unit-box");
    
    main.setPadding(new Insets(2, 5, 2, 5));
    main.setSpacing(10);
    main.getChildren().addAll(nameLabel, amountUnitBox);
    main.setAlignment(Pos.CENTER_LEFT);
    main.getStylesheets().addAll("file:styles/IngredientField.css");
    main.setId("root");
  }

  public HBox getIngredientField() {
    return main;
  }

  public void resetYield() {
    currentYield = defaultYield;
    amountLabel.setText(defaultYield + "");
  }

  /**
   * Updates the yield.
   *
   * @param desiredYield New value to change to
   */
  public void updateYield(int desiredYield) {
    double temp = Math.round((yieldOnePerson * desiredYield) * 100);
    currentYield = temp / 100;
    updateAmountLabel();
  }

  public void updateAmountLabel() {
    amountLabel.setText(currentYield + "");
  }
}
