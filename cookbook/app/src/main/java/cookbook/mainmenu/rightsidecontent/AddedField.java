package cookbook.mainmenu.rightsidecontent;

import cookbook.IngredientRec;
import cookbook.mainmenu.rightsidecontent.scenes.AddingRecipeScene;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AddedField extends HBox {

  Button removeBtn = new Button("-");
  Label instructionIndex;
  Label nameLabel;

  /**
   * Constructor for Ingredient field
   * @param amount
   * @param name
   * @param unit
   */
  public AddedField(AddingRecipeScene addingRecipeScene, VBox contentBox, String amount, String name, String unit, IngredientRec ingredient, Boolean existingIng) {

    HBox fieldBox = new HBox();
    HBox amountUnitBox = new HBox();

    nameLabel = new Label(name);
    nameLabel.setWrapText(true);
    nameLabel.setId("name-label");

    Label amountLabel = new Label(amount + " " + unit);
    amountLabel.setId("amount-label");

    amountUnitBox.getChildren().addAll(amountLabel);
    amountUnitBox.setPadding(new Insets(2, 5, 2, 5));
    amountUnitBox.setAlignment(Pos.CENTER);
    amountUnitBox.setId("amount-unit-box");

    fieldBox.getChildren().addAll(nameLabel, amountUnitBox);
    fieldBox.setId("field-box");
    fieldBox.setSpacing(10);
    fieldBox.setAlignment(Pos.CENTER);
    fieldBox.setPadding(new Insets(5, 10, 5, 10));

    removeBtn.setMinSize(25, 10);

    this.getChildren().addAll(removeBtn, fieldBox);
    this.setSpacing(10);
    this.setPadding(new Insets(5, 20, 5, 20));
    this.setAlignment(Pos.CENTER_LEFT);
    this.getStylesheets().add("file:styles/AddedField.css");
    this.setId("root");

    // Remove Button Ingredient
    removeBtn.setOnAction(e -> {
      if (!addingRecipeScene.getIngredientAddingProgress()) {
        if (existingIng) {
          addingRecipeScene.removeNewlyCreatedIngredientToArray(ingredient);
        }
        contentBox.getChildren().remove(this);
        addingRecipeScene.decreaseIngredientCount();
        addingRecipeScene.removeIngredientToArray(ingredient);
      } else {
        System.out.println("Finish adding ingredeint first!");
      }
    });
  }

  /**
   * Constructor for Instruction field
   * @param amount
   * @param name
   * @param unit
   */
  public AddedField(AddingRecipeScene addingRecipeScene, VBox contentBox, String amount, String name) {
    
    HBox fieldBox = new HBox();

    instructionIndex = new Label(amount);
    instructionIndex.setMinWidth(20);
    instructionIndex.setWrapText(true);
    nameLabel = new Label(name);
    nameLabel.setWrapText(true);
    
    fieldBox.getChildren().addAll(instructionIndex, nameLabel);
    fieldBox.setId("field-box");
    fieldBox.setSpacing(5);
    fieldBox.setAlignment(Pos.CENTER);
    fieldBox.setPadding(new Insets(5, 10, 5, 10));

    removeBtn.setMinSize(25, 10);

    this.getChildren().addAll(removeBtn, fieldBox);
    this.setSpacing(10);
    this.setPadding(new Insets(5, 20, 5, 20));
    this.setAlignment(Pos.CENTER_LEFT);
    this.getStylesheets().add("file:styles/AddedField.css");
    this.setId("root");

    // Remove Button Instruction
    removeBtn.setOnAction(e -> {
      if (!addingRecipeScene.getInstructionsAddingProgress()) {
        contentBox.getChildren().remove(this);
        addingRecipeScene.decreaseInstructionCount();
        addingRecipeScene.removeInstructionToArray(this);
        addingRecipeScene.rearrangeInstructionsList();
      } else {
        System.out.println("Finish adding instruction first!");
      }
    });
  }

  public void setInstructionNumber(String number) {
    instructionIndex.setText(number);
  }

  public String getName() {
    return nameLabel.getText();
  }
}
