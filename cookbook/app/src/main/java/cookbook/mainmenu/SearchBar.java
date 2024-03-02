package cookbook.mainmenu;

import java.util.ArrayList;

import cookbook.Images;
import cookbook.IngredientRec;
import cookbook.Recipe;
import cookbook.Tag;
import cookbook.User;
import cookbook.mainmenu.rightsidecontent.RecipeCard;
import cookbook.mainmenu.rightsidecontent.scenes.AllRecipesScene;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SearchBar extends VBox {
  
  private ArrayList<RecipeCard> searchResultList = new ArrayList<>();

  /**
   * Place to search.
   */
  public SearchBar(AllRecipesScene allRecipesScene, ArrayList<Recipe> allRecipe, VBox rightSide, User currentUser) {

    // Dimensions
    int width = 300;
    int height = 30;

    // Boxes
    HBox topBox = new HBox();

    // Search Box
    TextField searchField = new TextField();
    searchField.setPromptText("Search...");
    searchField.setMinSize(width, height);
    searchField.setPadding(new Insets(0, 20, 0, 20));
    searchField.setId("search-bar");

    // Search Button
    ImageView searchIcon = new ImageView(Images.iconSearchGreen);
    searchIcon.setFitHeight(20);
    searchIcon.setFitWidth(20);
    Button searchBtn = new Button("Search");
    searchBtn.setGraphic(searchIcon);
    searchBtn.setId("search-button");
    searchBtn.setMinSize(50, height);


    // Filters Box
    VBox filtersBox = new VBox();
    filtersBox.setId("filters-box");
    filtersBox.setAlignment(Pos.CENTER);
    filtersBox.setPadding(new Insets(0, 5, 5, 5));

    Label filtersLabel = new Label("Search Filters");
    filtersLabel.setId("filters-label");

    HBox checkboxBox = new HBox();
    checkboxBox.setId("checkbox-box");
    checkboxBox.setPadding(new Insets(5, 10, 5, 10));

    CheckBox checkName = new CheckBox("Name");
    CheckBox checkTag = new CheckBox("Tag");
    CheckBox checkIngredient = new CheckBox("Ingredient");
    checkboxBox.setAlignment(Pos.CENTER);
    checkboxBox.setSpacing(5);
    checkboxBox.getChildren().addAll(checkName, checkTag, checkIngredient);
    filtersBox.getChildren().addAll(filtersLabel, checkboxBox);

    topBox.getChildren().addAll(searchField, searchBtn, filtersBox);
    topBox.setAlignment(Pos.CENTER);
    topBox.setSpacing(10);

    this.setSpacing(20);
    this.getChildren().addAll(topBox);
    this.setAlignment(Pos.CENTER);
    this.setMinSize(width, height);
    this.getStylesheets().addAll("file:styles/SearchBar.css");

    // Search button
    searchBtn.setOnAction(e -> {

      // Clear current search results
      searchResultList.clear();

      // Check if search field is not empty
      if ((searchField.getText().length() == 0)) {

        // Resets to default cards
        allRecipesScene.refreshCards(allRecipesScene.getAllRecipeCards());
      } else {
        // Check if any of the checkboxes is selected
        if (checkName.isSelected() 
        || checkTag.isSelected() 
        || checkIngredient.isSelected()) {
        
        boolean nameSearch = false;
        boolean tagSearch = false;
        boolean ingredientSearch = false;

        if (checkName.isSelected()) {
          nameSearch = true;
        }
        if (checkTag.isSelected()) {
          tagSearch = true;
        }
        if (checkIngredient.isSelected()) {
          ingredientSearch = true;
        }

        // Search algorithm
        for (Recipe recipe : allRecipe) {
          
          String searchPhrase = (searchField.getText().toLowerCase());
          boolean recipeFound = false;

          // Search for RECIPE NAME
          if (nameSearch && !recipeFound) {
            
            if ((recipe.getName().toLowerCase()).contains(searchPhrase)) {
              RecipeCard newCard = new RecipeCard(recipe, rightSide, currentUser);
              searchResultList.add(newCard);
              recipeFound = true;
              System.out.println("RECIPE FOUND: " + recipe.getName());
            }
          }

          // Search for TAGS
          if (tagSearch && !recipeFound) {
            for (Tag tag : recipe.getTags()) {
              
              if ((tag.getName().toLowerCase()).contains(searchPhrase)) {
                RecipeCard newCard = new RecipeCard(recipe, rightSide, currentUser);
                searchResultList.add(newCard);
                recipeFound = true;
                System.out.println("RECIPE FOUND: " + recipe.getName());
              }
            }
          }

          // Search for INGREDIENTS
          if (ingredientSearch && !recipeFound) {

            // DELETE
            // Does not find an ingredient for some reason???
            // Returns a list of size 0.
            System.out.println("SAERCHING FOR INGREDIENT!!!");
            System.out.println("INGREDIENTS COUNT: " + recipe.getIngredients().size());

            for (IngredientRec ingredient : recipe.getIngredients()) {

              // DELETE
              // Does not find an ingredient for some reason???
              // Returns a list of size 0.
              System.out.println(ingredient);
              System.out.println(ingredient.getName());

              if ((ingredient.getName().toLowerCase()).contains(searchPhrase)) {
                RecipeCard newCard = new RecipeCard(recipe, rightSide, currentUser);
                searchResultList.add(newCard);
                recipeFound = true;
                System.out.println("RECIPE FOUND: " + recipe.getName());
              }
            }
          }
        }

        System.out.println("SELECTED FILTERS: ");
        System.out.println("NAME: " + nameSearch);
        System.out.println("TAG: " + tagSearch);
        System.out.println("INGREDIENT: " + ingredientSearch);

        if (searchResultList.size() == 0) {
          searchResultList.clear();
          allRecipesScene.clearRecipeBox();
          allRecipesScene.setNoCardsFound();
        } else {
          allRecipesScene.refreshCards(searchResultList);
        }
        } else {
          System.out.println("SELECT A FILTER!");
        }
      }
    });
  }
}
