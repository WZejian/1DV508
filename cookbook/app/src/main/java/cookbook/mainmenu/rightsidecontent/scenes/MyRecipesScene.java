package cookbook.mainmenu.rightsidecontent.scenes;

import cookbook.Databaseio;
import cookbook.Recipe;
import cookbook.User;
import cookbook.mainmenu.rightsidecontent.RecipeCard;

import java.sql.SQLException;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MyRecipesScene extends VBox {
  // Java objects
  ArrayList<Recipe> myRecipes = new ArrayList<Recipe>();
  ArrayList<RecipeCard> myRecipeCards = new ArrayList<RecipeCard>();

  // Visual objects
  private FlowPane recipeBox = new FlowPane();
  private ToggleButton ownbtn = new ToggleButton("Recipes Created by Me");  // meichen
  private ToggleButton favbtn = new ToggleButton("My Favorite Recipes");  // meichen

  /**
   * construct the scene.
   * @return
   */
  public MyRecipesScene(User currentUser, VBox rightSide) {
    // Get my recipes
    try {
      myRecipes = Databaseio.getMyRecipes(currentUser.getId());
    } catch (SQLException e1) {
      Alert noLoad = new Alert(AlertType.ERROR, "Cannot load your recipes");
      noLoad.showAndWait();
      e1.printStackTrace();
    }

    // Set scene style
    this.getStylesheets().addAll("file:styles/RecipesScene.css");

    // Set button style
    this.ownbtn.getStyleClass().add("my-recipe-button");
    this.favbtn.getStyleClass().add("my-recipe-button");
    this.setSpacing(30);

    // Box with buttons
    HBox buttons = new HBox();
    buttons.getStyleClass().add("my-buttons-background");
    buttons.setSpacing(30);
    buttons.setPadding(new Insets(10, 0, 10, 0));
    buttons.setAlignment(Pos.CENTER);
    buttons.getChildren().addAll(this.ownbtn, this.favbtn);

    ScrollPane recipeScrollBox = new ScrollPane();
    recipeScrollBox.setPadding(new Insets(0, 10, 30, 25));
    recipeScrollBox.setFitToHeight(true);
    recipeScrollBox.setFitToWidth(true);
    recipeScrollBox.getStyleClass().add("recipe-scroll-pane");

    // Recipe Box settings
    recipeBox.setHgap(30);
    recipeBox.setVgap(30);
    recipeBox.setPrefWrapLength(800);
    recipeBox.setAlignment(Pos.CENTER);
    recipeBox.setPadding(new Insets(7, 0, 7, 0));
    recipeBox.getStyleClass().add("recipe-box");
    recipeScrollBox.setContent(recipeBox);

    this.getChildren().addAll(buttons, recipeScrollBox);
    

    // Display recipes created by me by default
    this.displayMe(myRecipes, currentUser, rightSide);
    this.ownbtn.setSelected(true);
   
    /** 
    layout.setPadding(new Insets(10));
    layout.setSpacing(30);
    layout.setMinSize(200, 500);
    layout.setAlignment(Pos.CENTER);
    layout.setId("root");
    */
       
    ownbtn.setOnAction(e -> {
      this.favbtn.selectedProperty().set(false);
      ownbtn.setSelected(true);
      this.displayMe(myRecipes, currentUser, rightSide);
    }
    );
    
 
    favbtn.setOnAction(e -> {
      this.ownbtn.selectedProperty().set(false);
      favbtn.setSelected(true);
      this.displayFave(currentUser, rightSide);
    }
    );
  }

  // Display recipes made by me

  private void displayMe(ArrayList<Recipe> myRecipes, User currentUser, VBox rightSide) {
    myRecipeCards.clear();
    // Make all cards
    for (Recipe r : myRecipes) {
      if (currentUser.getId() == r.getAuthor()) {
        RecipeCard rc = new RecipeCard(r, rightSide, currentUser);
        myRecipeCards.add(rc);
      }
    }

    // Add all cards to displayed screen:
    recipeBox.getChildren().clear();
    for (RecipeCard rc : myRecipeCards) {
      recipeBox.getChildren().add(rc);
    }
  }

  

  // Display favourite recipes
  private void displayFave(User currentUser, VBox rightSide) {
    myRecipeCards.clear();
    ArrayList<Integer> favIds = Databaseio.getFavIds(currentUser.getId());
    for (int favid : favIds) {
      try {
        Recipe re = Databaseio.getRecipeObj(favid);
        RecipeCard rc = new RecipeCard(re, rightSide, currentUser);
        myRecipeCards.add(rc);
      } catch (Exception sqlerror) {
        sqlerror.printStackTrace();
      }
    }

    // Add all cards to displayed screen:
    recipeBox.getChildren().clear();
    for (RecipeCard rc : myRecipeCards) {
      recipeBox.getChildren().add(rc);
    }
  }
}
