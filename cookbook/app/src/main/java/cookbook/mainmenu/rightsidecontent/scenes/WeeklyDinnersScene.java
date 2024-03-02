package cookbook.mainmenu.rightsidecontent.scenes;

import cookbook.Databaseio;
import cookbook.IngredientRec;
import cookbook.Recipe;
import cookbook.User;
import cookbook.mainmenu.rightsidecontent.RecipeCard;
import cookbook.prompt.PromptDialogs;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.TreeMap;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Pair;

/**
 * A class for creating a weekly dinner showing scene.
 */
public class WeeklyDinnersScene extends VBox {
  public FlowPane recipeBox = new FlowPane();
  public HashMap<LocalDate, ArrayList<Recipe>> hm = new HashMap<LocalDate, ArrayList<Recipe>>();
  public TreeMap<LocalDate, ArrayList<Recipe>> treeMap = new TreeMap<LocalDate, ArrayList<Recipe>>();
  public AllRecipesScene aRs = new AllRecipesScene();
  public ArrayList<LocalDate> dateList = new ArrayList<LocalDate>();
  private HashMap<LocalDate, HashMap<Integer, Integer>> weeklyDinners = new HashMap<LocalDate, HashMap<Integer, Integer>>();

  /**
   * The method can generate a VBox showing a weekly dinner list on a given date.
   */
  public VBox getWeeklyDinnerScene(User currentUser, VBox rightSide) {
    VBox layout = new VBox();

    Button b = new Button();
    b.setText("Search for your weekly dinner list");
    b.setMinWidth(95);
    b.setStyle("-fx-background-color: #845b46; -fx-text-fill: #FFE4C4");
    b.setFont(Font.font("Fira Sans", FontWeight.BOLD, 15));
    b.setOnAction(e -> {
      weeklyDinners = promptForDateRange(currentUser);
      int recipeNum = 0;
      for (LocalDate localDate : weeklyDinners.keySet()) {
        if (weeklyDinners.get(localDate) != null) {
          recipeNum += weeklyDinners.get(localDate).size();
        }
      }
      if (recipeNum == 0) {
        try {
          PromptDialogs.promptForInformationAlert(
              "No recipe has been found for the date you are searching.");
        } catch (Exception e1) {
          e1.printStackTrace();
        }
      } else {
        for (LocalDate localDate : weeklyDinners.keySet()) {
          HashMap<Integer, Integer> recipesForOneDay = weeklyDinners.get(localDate);
          ArrayList<Recipe> recipeList = new ArrayList<Recipe>();
          for (Integer recipeId : recipesForOneDay.keySet()) {
            try {
              recipeList.add(Databaseio.getRecipeObj(recipeId));
            } catch (SQLException e1) {
              e1.printStackTrace();
            }
          }
          hm.put(localDate, recipeList);
        }
        ArrayList<VBox> wdRecipeCards = new ArrayList<VBox>();
        treeMap.putAll(hm);
        for (Entry<LocalDate, ArrayList<Recipe>> entry : treeMap.entrySet()) {
          LocalDate lDate = entry.getKey();
          ArrayList<Recipe> recipeArrayList = entry.getValue();
          for (Recipe r : recipeArrayList) {
            wdRecipeCards.add(removeFeatureBox(currentUser, r, lDate, rightSide));
          }
        }

        recipeBox.getChildren().clear();

        // layout these recipe cards
        for (VBox vb : wdRecipeCards) {
          addRemoveFeature(vb);
        }
      }
    });

    Button generateShopList = new Button();
    generateShopList.setText("Add ingredients to shopping list");
    generateShopList.setMinWidth(95);
    generateShopList.setStyle("-fx-background-color: #845b46; -fx-text-fill: #FFE4C4");
    generateShopList.setFont(Font.font("Fira Sans", FontWeight.BOLD, 15));
    generateShopList.setOnAction(e -> {
      int recipeNum = 0;
      for (LocalDate localDate : this.weeklyDinners.keySet()) {
        if (this.weeklyDinners.get(localDate) != null) {
          recipeNum += this.weeklyDinners.get(localDate).size();
        }
      }
      if (recipeNum == 0) {
        try {
          PromptDialogs.promptForWarningAlert(
              "Sorry, there is no recipe chosen. Please choose date range and search for your weekly dinner recipes first.");
        } catch (Exception e1) {
          e1.printStackTrace();
        }
      } else {
        HashMap<Recipe, Integer> chosenRecipeList = new HashMap<Recipe, Integer>();
        for (HashMap<Integer, Integer> oneDayRecipes : weeklyDinners.values()) {
          for (HashMap.Entry<Integer, Integer> oneRecipeAndYield : oneDayRecipes.entrySet()) {
            Integer recipeId = oneRecipeAndYield.getKey();
            Integer yieldForOneRecipe = oneRecipeAndYield.getValue();
            try {
              if (chosenRecipeList.containsKey(Databaseio.getRecipeObj(recipeId))) {
                chosenRecipeList.put(Databaseio.getRecipeObj(recipeId),
                    chosenRecipeList.get(Databaseio.getRecipeObj(recipeId)) + yieldForOneRecipe);
              } else {
                chosenRecipeList.put(Databaseio.getRecipeObj(recipeId), yieldForOneRecipe);
              }
            } catch (SQLException e1) {
              try {
                PromptDialogs.promptForErrorAlert(
                    "Something is wrong. Please retry or contact our helping team.");
              } catch (Exception e2) {
                e2.printStackTrace();
              }
            }
          }
        }
        for (HashMap.Entry<Recipe, Integer> oneRecipe : chosenRecipeList.entrySet()) {
          Double yield = Double.valueOf(oneRecipe.getValue());
          ArrayList<IngredientRec> ingredentLst = oneRecipe.getKey().getIngredients();
          for (IngredientRec ingr : ingredentLst) {
            String ingrName = ingr.getName();
            Double ingrQuan = (ingr.getQuantity() / (oneRecipe.getKey().getYield())) * yield;
            String ingrUnit = ingr.getUnit();
            Databaseio.shopAddRow(currentUser.getId(), ingrName, ingrQuan, ingrUnit);
          }
        }
      }
      Alert alertComplete = new Alert(AlertType.INFORMATION);
      alertComplete.setTitle("Successfully added");
      alertComplete.setHeaderText(null);
      alertComplete.setContentText("The ingredients are successfully added into shopping list!");
      alertComplete.showAndWait();
    });

    ScrollPane scrollPane = new ScrollPane();
    scrollPane.setPadding(new Insets(30, 10, 30, 20));
    scrollPane.setFitToHeight(true);
    scrollPane.setFitToWidth(true);
    scrollPane.getStyleClass().add("recipe-scroll-pane");

    recipeBox.setHgap(30);
    recipeBox.setVgap(30);
    recipeBox.setPrefWrapLength(800);
    recipeBox.setAlignment(Pos.CENTER);
    recipeBox.setPadding(new Insets(7, 0, 7, 0));
    recipeBox.getStyleClass().add("recipe-box");
    scrollPane.setContent(recipeBox);
    layout.setSpacing(5);
    layout.getChildren().addAll(b, generateShopList, scrollPane);
    layout.getStylesheets().addAll("file:styles/RecipesScene.css");
    return layout;
  }

  /**
   * date Rnage.
   */
  public HashMap<LocalDate, HashMap<Integer, Integer>> promptForDateRange(User currentUser) {
    HashMap<LocalDate, HashMap<Integer, Integer>> weeklyDinners = new HashMap<LocalDate, HashMap<Integer, Integer>>();
    // Create the custom dialog.
    Dialog<Pair<LocalDate, LocalDate>> dialog = new Dialog<>();
    dialog.setTitle("Choose the date range");
    dialog.setHeaderText("Please provide the following information");

    // Set the button types.
    ButtonType confirmButtonType = new ButtonType("Confirm", ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);

    // Create the yield and date labels and fields.
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));

    // Collect a start date
    DatePicker datePicker1 = new DatePicker();
    datePicker1.setPromptText("Select start date");
    datePicker1.setPrefWidth(170);

    // Collect an end date
    DatePicker datePicker2 = new DatePicker();
    datePicker2.setPromptText("Select end date");
    datePicker2.setPrefWidth(170);

    grid.add(new Label("Start date:"), 0, 0);
    grid.add(datePicker1, 1, 0);
    grid.add(new Label("End date:"), 0, 1);
    grid.add(datePicker2, 1, 1);

    // Enable/Disable confirm button depending on whether a date was entered.
    Node confirmButton = dialog.getDialogPane().lookupButton(confirmButtonType);
    confirmButton.setDisable(true);

    datePicker1.valueProperty().addListener((observable, oldValue, newValue) -> {
      confirmButton.setDisable(!(datePicker2.getValue() != null && newValue.isBefore(datePicker2.getValue())));
    });
    datePicker2.valueProperty().addListener((observable, oldValue, newValue) -> {
      confirmButton.setDisable(!(datePicker1.getValue() != null && newValue.isAfter(datePicker1.getValue())));
    });

    dialog.getDialogPane().setContent(grid);

    // Request focus on the date field by default.
    Platform.runLater(() -> datePicker1.requestFocus());

    // Convert the result to a date-date-pair when the confirm button is clicked.
    dialog.setResultConverter(dialogButton -> {
      if (dialogButton == confirmButtonType) {
        return new Pair<>(datePicker1.getValue(), datePicker2.getValue());
      }
      return null;
    });
    Optional<Pair<LocalDate, LocalDate>> result = dialog.showAndWait();

    result.ifPresent(startEnd -> {
      LocalDate startDate = startEnd.getKey();
      LocalDate endDate = startEnd.getValue();
      // iterate the date range and add those LocalDate to an arraylist
      for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
        dateList.add(date);
      }
      // iterate the arraylist of LocalDate.
      for (LocalDate ld : dateList) {
        HashMap<Integer, Integer> recipeYieldHashMap = new HashMap<Integer, Integer>();
        ResultSet rS = Databaseio.extractWeeklyDinners(currentUser.getId(), ld);
        try {
          HashMap<Integer, Integer> oneDayHashMap = Databaseio.resultSetToHashMap(rS);
          // Add the recipeID-yield pairs for a single day to the sum hashmap.
          for (HashMap.Entry<Integer, Integer> entry : oneDayHashMap.entrySet()) {
            int recipeId = entry.getKey();
            int yieldForRecipe = entry.getValue();
            recipeYieldHashMap.put(recipeId, yieldForRecipe);
          }
          weeklyDinners.put(ld, recipeYieldHashMap);
        } catch (SQLException e) {
          try {
            PromptDialogs.promptForErrorAlert(
                "Something is wrong. Please try again or contact our help team.");
          } catch (Exception e1) {
            e1.printStackTrace();
          }
        }
      }
    });

    return weeklyDinners;
  }

  /**
   * Return a VBox which has a recipe card and a button to remove the recipe
   * from the user's weekly dinner list.
   */
  public VBox removeFeatureBox(User currentUser, Recipe currentRecipe, LocalDate localD, VBox rightSide) {
    VBox vb = new VBox();
    Label dateString = new Label(localD.toString());
    dateString.setMinWidth(95);
    dateString.setStyle("-fx-background-color: #845b46; -fx-text-fill: #FFE4C4");
    dateString.setFont(Font.font("Fira Sans", FontWeight.BOLD, 15));
    RecipeCard rc = aRs.createCard(currentRecipe, rightSide, currentUser);

    Button b = new Button();
    b.setText("Remove");
    b.setMinWidth(95);
    b.setStyle("-fx-background-color: #845b46; -fx-text-fill: #FFE4C4");
    b.setFont(Font.font("Fira Sans", FontWeight.BOLD, 15));

    b.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        try {
          currentUser.deleteWeeklyDinner(currentRecipe, localD);

          try {
            PromptDialogs.promptForConfirmationAlert(
                "The removal of the recipe from your weekly dinner on this date has been successfully done.\nPlease wait for a while to refresh.");
          } catch (Exception e) {
            e.printStackTrace();
          }

          hm.get(localD).remove(currentRecipe);
          try {
            ArrayList<VBox> wdRecipeCards = new ArrayList<VBox>();
            treeMap.putAll(hm);
            for (Entry<LocalDate, ArrayList<Recipe>> entry : treeMap.entrySet()) {
              LocalDate lDate = entry.getKey();
              ArrayList<Recipe> recipeArrayList = entry.getValue();
              for (Recipe r : recipeArrayList) {
                wdRecipeCards.add(removeFeatureBox(currentUser, r, lDate, rightSide));
              }
            }

            recipeBox.getChildren().clear();

            // layout these recipe cards
            for (VBox vb : wdRecipeCards) {
              addRemoveFeature(vb);
            }
          } catch (Exception SQLException) {
            try {
              PromptDialogs.promptForErrorAlert(
                  "Something is wrong. Please try again or contact us for help.");
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        } catch (SQLException e) {
          try {
            PromptDialogs.promptForErrorAlert(
                "Something is wrong. Please try again or contact us for help.");
          } catch (Exception exception) {
            exception.printStackTrace();
          }
        }
      }
    });

    vb.getChildren().addAll(dateString, rc, b);
    return vb;
  }

  /**
   * Add a VBox with a recipe card and a button to remove it to a scene.
   */
  public void addRemoveFeature(VBox vb) {
    recipeBox.getChildren().add(vb);
  }
}
