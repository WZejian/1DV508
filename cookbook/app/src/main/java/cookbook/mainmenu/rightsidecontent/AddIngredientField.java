package cookbook.mainmenu.rightsidecontent;

import cookbook.Ingredient;
import cookbook.mainmenu.rightsidecontent.scenes.AddingRecipeScene;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AddIngredientField extends HBox {

  private IngredientComboBox existingIngredients;

  /**
   * Add ingredient field.
   */
  public AddIngredientField(AddingRecipeScene addingRecipeScene, VBox ingredientsBox,
      ArrayList<Ingredient> ingredientList, int index) {

    existingIngredients = new IngredientComboBox(ingredientList);

    Label or = new Label("OR");
    or.setId("or-label");

    Button createIngredient = new Button("CREATE NEW");
    createIngredient.setId("create-ingredient-btn");

    // Combo Box
    existingIngredients.setPromptText("SELECT");
    existingIngredients.setVisibleRowCount(5);
    existingIngredients.setId("combo-box");

    this.setAlignment(Pos.CENTER);
    this.setSpacing(20);
    this.getChildren().addAll(createIngredient, or, existingIngredients);
    this.getStylesheets().add("file:styles/AddIngredientField.css");
    this.setId("root");
    this.setPadding(new Insets(5, 5, 5, 5));

    // Selecting ingredeint from existing
    existingIngredients.setOnAction(e -> {

      // Find the selected ingredeint
      int indexFound = -1;
      for (Ingredient ingredient : ingredientList) {
        if ((ingredient.getName()).equals(existingIngredients.getValue())) {
          indexFound = ingredientList.indexOf(ingredient);
        }
      }

      CreateNewIngredientField existIngredientFiled = new CreateNewIngredientField(
          addingRecipeScene, ingredientsBox, ingredientList, this, index, 
          ingredientList.get(indexFound));
      this.getChildren().clear();
      this.getChildren().add(existIngredientFiled);
    });

    // Create Ingredient Button
    createIngredient.setOnAction(e -> {
      CreateNewIngredientField newIngredient = new CreateNewIngredientField(
          addingRecipeScene, ingredientsBox, ingredientList, this, index);
      this.getChildren().clear();
      this.getChildren().add(newIngredient);
    });
  }

  /**
   * refresh.
   */
  public void refreshIngredientList(ArrayList<Ingredient> ingredientList) {
    existingIngredients.getItems().clear();

    for (Ingredient ingredient : ingredientList) {
      existingIngredients.getItems().add(ingredient.toString());
    }
  }
}
