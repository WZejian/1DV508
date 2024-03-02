package cookbook.mainmenu.rightsidecontent.scenes;

import cookbook.Databaseio;
import cookbook.Images;
import cookbook.Recipe;
import cookbook.User;
import cookbook.mainmenu.SearchBar;
import cookbook.mainmenu.rightsidecontent.RecipeCard;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class AllRecipesScene extends VBox {
  // Java objects
  private ArrayList<RecipeCard> allRecipeCards = new ArrayList<RecipeCard>();
  private ArrayList<Recipe> allRecipe = new ArrayList<Recipe>();
  
  // Visual objects
  private FlowPane recipeBox = new FlowPane();
  private VBox noRecipesFoundBox = new VBox();

  /**
   * Create a default constructor.
   */
  public AllRecipesScene() {

  }

  /**
   * This scene displays all recipes as Recipe Cards.
   */
  public AllRecipesScene(User currentUser, VBox rightSide) {
    
    // Fetch all recipes from DB
    try {
      allRecipe = Databaseio.getAllRecipes();
    } catch (SQLException e1) {
      Alert noRecipes = new Alert(AlertType.ERROR, "Cannot load the recipes from DB");
      noRecipes.showAndWait();
      e1.printStackTrace();
    }

    // Make all cards
    for (Recipe r : allRecipe) {
      allRecipeCards.add(this.createCard(r, rightSide, currentUser));
    }

    // No recipes found box
    Label noRecipesLabel = new Label("NO RECIPES FOUND");
    noRecipesLabel.setId("no-recipes-found");
    ImageView sadSmiley = new ImageView(Images.sadSmileyGreen);
    sadSmiley.setFitHeight(150);
    sadSmiley.setFitWidth(150);
    noRecipesFoundBox.setAlignment(Pos.CENTER);
    noRecipesFoundBox.setSpacing(20);
    noRecipesFoundBox.setPadding(new Insets(100, 0, 0, 0));
    noRecipesFoundBox.getChildren().addAll(noRecipesLabel, sadSmiley);

    // Load recipes in the scene
    refreshCards(allRecipeCards);

    this.setPadding(new Insets(10));
    this.setSpacing(10);
    this.setMinSize(200, 500);
    this.setAlignment(Pos.CENTER);
    this.setId("root");

    // Search Bar
    VBox searchSection = new VBox();
    searchSection.setId("search-section");
    searchSection.setPadding(new Insets(10));
    searchSection.setMaxWidth(640);
    SearchBar searchBar = new SearchBar(this, allRecipe, rightSide, currentUser);
    
    Button addRecipeToBoxBtn = new Button("Add Recipe");
    addRecipeToBoxBtn.setId("add-recipe-button");
    
    searchSection.getChildren().addAll(searchBar);
    searchSection.setSpacing(10);

    // Scroll pane
    // To store all the Recipe Cards
    // And make them scrollable.
    ScrollPane recipeScrollBox = new ScrollPane();
    recipeScrollBox.setPadding(new Insets(15, 10, 30, 20));
    recipeScrollBox.setFitToWidth(true);
    recipeScrollBox.setPrefHeight(475);
    recipeScrollBox.getStyleClass().add("recipe-scroll-pane");

    /**
    // Add all cards to displayed screen:
    for (RecipeCard rc : allRecipeCards) {
      this.addRecipe(rc);
    }
    */

    // Recipe Box settings
    recipeBox.setHgap(30);
    recipeBox.setVgap(30);
    recipeBox.setPrefWrapLength(800);
    recipeBox.setAlignment(Pos.CENTER);
    recipeBox.getStyleClass().add("recipe-box");
    recipeScrollBox.setContent(recipeBox);

    this.getChildren().addAll(searchSection, recipeScrollBox, addRecipeToBoxBtn);
    this.getStylesheets().addAll("file:styles/RecipesScene.css");

    // Add recipe button
    addRecipeToBoxBtn.setOnAction(e -> {
      AddingRecipeScene addRecipeScene = new AddingRecipeScene(
          this,
          rightSide,
          currentUser,
          noRecipesFoundBox
          );
      rightSide.getChildren().clear();
      rightSide.getChildren().add(addRecipeScene.getScene());
    });
  }

  public RecipeCard createCard(Recipe r, VBox rightSide, User currentUser) {
    RecipeCard rc = new RecipeCard(r, rightSide, currentUser);
    return rc;
  }

  public void addRecipeToBox(RecipeCard recipe) {
    recipeBox.getChildren().add(recipe);
  }

  public void setNoCardsFound() {
    recipeBox.getChildren().addAll(noRecipesFoundBox);
  }

  public void clearRecipeBox() {
    recipeBox.getChildren().clear();;
  }

  public void refreshCards(ArrayList<RecipeCard> recipeCardsList) {
    
    if (recipeCardsList.size() > 0) {

      // Clear current cards
      recipeBox.getChildren().clear();

      // Add new cards
      for (RecipeCard recipeCard : recipeCardsList) {
        addRecipeToBox(recipeCard);
      }
    } else {
      setNoCardsFound();
    }
  }

  public ArrayList<RecipeCard> getAllRecipeCards() {
    return allRecipeCards;
  }
}
