package cookbook.mainmenu.rightsidecontent;

import cookbook.Ingredient;
import cookbook.IngredientRec;
import cookbook.mainmenu.rightsidecontent.scenes.AddingRecipeScene;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CreateNewIngredientField extends HBox {

  enum UnitType {
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

  private int placementInIngList;
  // All ingredients for a given recipe.
  private ArrayList<IngredientRec> allRecIngs = new ArrayList<IngredientRec>();

  /**
   * Constructor for adding an existing ingredient
   * @param addingRecipeScene
   * @param ingredientsBox
   * @param ingredientList
   * @param addIngredientField
   * @param index
   */
  public CreateNewIngredientField(AddingRecipeScene addingRecipeScene, VBox ingredientsBox, ArrayList<Ingredient> ingredientList, AddIngredientField addIngredientField, int index, Ingredient ingredient) {

    placementInIngList = index;

    // Unit Types
    ComboBox<UnitType> unitSelection = new ComboBox<>();
    for (UnitType unitType : UnitType.values()) {
      unitSelection.getItems().add(unitType);
    }
    unitSelection.setPromptText("UNIT");
    unitSelection.setVisibleRowCount(5);

    // Amount of ingredeint
    TextField amountField = new TextField("");
    amountField.setPromptText("Amount");
    amountField.setMaxWidth(60);

    //input check, only numbers
    amountField.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(
          ObservableValue<? extends String> observable, String oldValue, String newValue
        ) {
          if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
              amountField.setText(oldValue);
          }
      }
    });

    // Ingredient name label
    Label nameLabel = new Label(ingredient.getName());
    nameLabel.setId("name-label");
    
    Button addBtn = new Button("ADD");
  
    this.setAlignment(Pos.CENTER);
    this.setSpacing(10);
    this.getChildren().addAll(nameLabel, amountField, unitSelection, addBtn);
    this.getStylesheets().add("file:styles/CreateNewIngredientField.css");

    // Add Button Action
    addBtn.setOnAction(e -> {

      UnitType unit = unitSelection.getValue();
      String name = ingredient.getName();
      String amount = amountField.getText();

      if (unit != null && name.length() > 2 && amount.length() > 0) {
        // Create the new Ingredient
        try {
          IngredientRec newIngredient = new IngredientRec(
              name,
              Double.parseDouble(amount),
              unit.toString());

          addingRecipeScene.addIngredientToArray(newIngredient);
          addingRecipeScene.setIngredientAddingProgress(false);

          System.out.println("Name " + name);
          System.out.println("Unit " + unit);

          AddedField added = new AddedField(
              addingRecipeScene,
              ingredientsBox,
              amount+"",
              name,
              unit.toString(),
              newIngredient, false);

          // Remove current box and add new added ingredient in the lsit box.
          ingredientsBox.getChildren().remove(1);
          ingredientsBox.getChildren().add(added);

        } catch (Exception exception) {
          System.out.println(exception);
        }
      } else {
        System.out.println("Please provide all the details");
      }
    });
  }

  /**
   * Constructor for to create a new ingredeint from scratch
   * @param addingRecipeScene
   * @param ingredientsBox
   * @param ingredientList
   * @param addIngredientField
   * @param index
   */
  public CreateNewIngredientField(AddingRecipeScene addingRecipeScene, VBox ingredientsBox, ArrayList<Ingredient> ingredientList, AddIngredientField addIngredientField, int index) {
    
    placementInIngList = index;

    // THIS MAY NEED CHANGING, HOW TO SET A NEW INGREDIENT ID
    // DEFAULT VALUES
    // int newId = ingredientList.size() + 1;

    // ID not needed - Marek

    // Unit Types
    ComboBox<UnitType> unitSelection = new ComboBox<>();
    for (UnitType unitType : UnitType.values()) {
      unitSelection.getItems().add(unitType);
    }
    unitSelection.setPromptText("UNIT");
    unitSelection.setVisibleRowCount(5);

    // Amount of ingredeint
    TextField amountField = new TextField("");
    amountField.setPromptText("Amount");
    amountField.setMaxWidth(60);
    //input check, only numbers
    amountField.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(
          ObservableValue<? extends String> observable, String oldValue, String newValue
        ) {
          if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
              amountField.setText(oldValue);
          }
      }
    });

    // Ingredient Name
    TextField nameField = new TextField("");
    nameField.setPromptText("Ingredient name");
    
    Button addBtn = new Button("ADD");
  
    this.setAlignment(Pos.CENTER);
    this.setSpacing(10);
    this.getChildren().addAll(nameField, amountField, unitSelection, addBtn);
    this.getStylesheets().add("file:styles/CreateNewIngredientField.css");

    // Add Button Action
    addBtn.setOnAction(e -> {

      UnitType unit = unitSelection.getValue();
      String name = nameField.getText();
      String amount = amountField.getText();

      if (unit != null && name.length() > 2 && amount.length() > 0) {
        // Create the new Ingredient
        try {
          IngredientRec newIngredient = new IngredientRec(
              name,
              Double.parseDouble(amount),
              unit.toString());

          addingRecipeScene.addIngredientToArray(newIngredient);
          addingRecipeScene.addNewlyCreatedIngredientToArray(newIngredient);
          addingRecipeScene.setIngredientAddingProgress(false);

          System.out.println("Name " + name);
          System.out.println("Unit " + unit);

          AddedField added = new AddedField(
              addingRecipeScene,
              ingredientsBox,
              amount+"",
              name,
              unit.toString(),
              newIngredient, true);

          // Remove current box and add new added ingredient in the lsit box.
          ingredientsBox.getChildren().remove(1);
          ingredientsBox.getChildren().add(added);

        } catch (Exception exception) {
          System.out.println(exception);
        }
      } else {
        System.out.println("Please provide all the details");
      }
    });
  }

  // private void setPlacement(int index) {
  //   placementInIngList = index;
  // }
}
