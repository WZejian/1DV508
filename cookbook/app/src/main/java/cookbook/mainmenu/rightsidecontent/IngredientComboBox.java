package cookbook.mainmenu.rightsidecontent;

import java.util.ArrayList;

import cookbook.Ingredient;
import javafx.scene.control.ComboBox;

public class IngredientComboBox extends ComboBox<String> {
  
  public IngredientComboBox(ArrayList<Ingredient> ingredientList) {

    for (Ingredient ingredient : ingredientList) {
      this.getItems().add(ingredient.getName());
    }    
  }
}
