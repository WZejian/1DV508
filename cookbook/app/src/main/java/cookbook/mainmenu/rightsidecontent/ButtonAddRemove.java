package cookbook.mainmenu.rightsidecontent;

import java.util.ArrayList;

import cookbook.Ingredient;
import cookbook.mainmenu.rightsidecontent.scenes.AddingRecipeScene;
import cookbook.mainmenu.rightsidecontent.scenes.recipeview.IngredientField;
import cookbook.mainmenu.rightsidecontent.scenes.recipeview.RecipeViewScene;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ButtonAddRemove extends HBox {
  
  private int counter = 0;
  private Label countIngredient = new Label("0");

  /**
   * Constructor for recipe view scene counter button, yield person servings
   * @param addingRecipeScene
   */
  public ButtonAddRemove(RecipeViewScene recipeViewScene, int defaultYield, ArrayList<IngredientField> ingredientFieldList) {
    // Button width (- and +)
    int width = 30;
    int height = 30;

    counter = defaultYield;
    countIngredient.setText(defaultYield + "");

    // Counter
    countIngredient.setId("yield-counter-recipe-view");
    countIngredient.setMinSize(width, height);
    countIngredient.setPadding(new Insets(0, 5, 0, 5));
    countIngredient.setAlignment(Pos.CENTER);
    
    // Remove button*****
    Button removeBtn = new Button("-");
    removeBtn.setMinSize(width, height);
    removeBtn.setPadding(new Insets(0));

    // Add button
    Button addBtn = new Button("+");
    addBtn.setMinSize(width, height);
    addBtn.setPadding(new Insets(0));

    this.getChildren().addAll(removeBtn, countIngredient, addBtn);
    this.setPadding(new Insets(5, 5, 5, 5));
    this.setAlignment(Pos.CENTER);
    this.getStylesheets().add("file:styles/YieldCOunter.css");
    this.setId("root");

    /*******Button Actions******* */
    // Remove btn
    removeBtn.setOnAction(e -> {
      if (!(counter == 1)) {
        counter--;
        updateCounterLabel(Integer.toString(counter));
        
        for (IngredientField ingredientField : ingredientFieldList) {
          ingredientField.updateYield(counter);
        }
      }
    });

    // Add Ingredient btn
    addBtn.setOnAction(e -> {
      if (counter >= 1 && counter < 10) {
        counter++;
        updateCounterLabel(Integer.toString(counter));
        
        for (IngredientField ingredientField : ingredientFieldList) {
          ingredientField.updateYield(counter);
        }
      } 
    });
  }

  /**
   * Constructor for basic counter button, yield person servings
   * @param addingRecipeScene
   */
  public ButtonAddRemove(AddingRecipeScene addingRecipeScene, Label yieldsCounterLabel) {
    // Button width (- and +)
    int width = 30;
    int height = 30;

    counter = 1;
    countIngredient.setText("1");

    // Counter
    countIngredient.setId("yield-counter");
    countIngredient.setMinSize(width, height);
    countIngredient.setPadding(new Insets(0, 5, 0, 5));
    countIngredient.setAlignment(Pos.CENTER);
    
    // Remove button*****
    Button removeBtn = new Button("-");
    removeBtn.setMinSize(width, height);
    removeBtn.setPadding(new Insets(0));

    // Add button
    Button addBtn = new Button("+");
    addBtn.setMinSize(width, height);
    addBtn.setPadding(new Insets(0));

    this.getChildren().addAll(removeBtn, countIngredient, addBtn);
    this.setPadding(new Insets(5, 5, 5, 5));
    this.setAlignment(Pos.CENTER);
    this.getStylesheets().add("file:styles/YieldCOunter.css");
    this.setId("root");

    /*******Button Actions******* */
    // Remove btn
    removeBtn.setOnAction(e -> {
      if (!(counter == 1)) {
        counter--;
        updateCounterLabel(Integer.toString(counter));
        yieldsCounterLabel.setText(counter + "");
      }
    });

    // Add Ingredient btn
    addBtn.setOnAction(e -> {
      if (counter >= 1 && counter < 10) {
        counter++;
        updateCounterLabel(Integer.toString(counter));
        yieldsCounterLabel.setText(counter + "");
      } 
    });
  }

  /**
   * Constructor
   * @param ingredientsBox
   * @param addingRecipeScene
   * @param ingredientList
   */
  public ButtonAddRemove(VBox ingredientsBox, AddingRecipeScene addingRecipeScene,  ArrayList<Ingredient> ingredientList) {

    // Button width (- and +)
    int width = 30;
    int height = 25;

    // Counter
    countIngredient.setId("ingredient-counter");
    countIngredient.setMinSize(width, height);
    countIngredient.setPadding(new Insets(0, 5, 0, 5));
    countIngredient.setAlignment(Pos.CENTER);
    
    // Remove button, NOT USED ANYMORE*****
    Button removeBtn = new Button("-");
    removeBtn.setMinSize(width, height);

    // Add button
    Button addBtn = new Button("+");
    addBtn.setMinSize(width, height);
    addBtn.setPadding(new Insets(0));

    this.getChildren().addAll(addBtn);
    this.setPadding(new Insets(5, 5, 5, 5));
    this.setAlignment(Pos.CENTER);
    this.getStylesheets().add("file:styles/ButtonAddRemove.css");
    this.setId("root");

    // Add Ingredient btn
    addBtn.setOnAction(e -> {

      // If no other ingredeint is in progress of being added.
      if (!addingRecipeScene.getIngredientAddingProgress()) {
        if (counter >= 0) {
          counter++;
          updateCounterLabel(Integer.toString(counter));
          addingRecipeScene.createAddIngredeintField(ingredientsBox, ingredientList);
          addingRecipeScene.increaseIngredientCount();
        }
      }
    });
  }

  /**
   * Constructor without ingredient list. Will be used for adding instructions.
   * @param ingredientsBox
   * @param addingRecipeScene
   */
  public ButtonAddRemove(VBox ingredientsBox, AddingRecipeScene addingRecipeScene) {

    // Button width (- and +)
    int width = 30;
    int height = 25;

    // Counter
    countIngredient.setId("ingredient-counter");
    countIngredient.setMinSize(width, height);
    countIngredient.setPadding(new Insets(0, 5, 0, 5));
    countIngredient.setAlignment(Pos.CENTER);
    
    // Remove button, NOT USED ANYMORE*****
    Button removeBtn = new Button("-");
    removeBtn.setMinSize(width, height);

    // Add button
    Button addBtn = new Button("+");
    addBtn.setMinSize(width, height);
    addBtn.setPadding(new Insets(0));

    this.getChildren().addAll(addBtn);
    this.setPadding(new Insets(5, 5, 5, 5));
    this.setAlignment(Pos.CENTER);
    this.getStylesheets().add("file:styles/ButtonAddRemove.css");
    this.setId("root");

    /*******Button Actions******* */

    // Add instruction btn
    addBtn.setOnAction(e -> {
      
      // If no other instruction is in progress of being added.
      if (!addingRecipeScene.getInstructionsAddingProgress()) {
        if (counter >= 0) {
          counter++;
          updateCounterLabel(Integer.toString(counter));
          addingRecipeScene.createInstructionFiled(ingredientsBox);
          addingRecipeScene.increaseInstructionCount();
        }
      }
    });
  }

  public void updateCounterLabel(String num) {
    countIngredient.setText(num);
  }

  public Label getCounter() {
    return countIngredient;
  }

  public int getCount() {
    return counter;
  }
}
