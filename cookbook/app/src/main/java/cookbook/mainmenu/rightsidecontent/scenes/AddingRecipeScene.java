package cookbook.mainmenu.rightsidecontent.scenes;

import cookbook.Databaseio;
import cookbook.DbConnector;
import cookbook.Images;
import cookbook.Ingredient;
import cookbook.IngredientRec;
import cookbook.Picture;
import cookbook.Recipe;
import cookbook.Tag;
import cookbook.User;
import cookbook.mainmenu.rightsidecontent.AddIngredientField;
import cookbook.mainmenu.rightsidecontent.AddInstructionField;
import cookbook.mainmenu.rightsidecontent.AddTagButton;
import cookbook.mainmenu.rightsidecontent.AddedField;
import cookbook.mainmenu.rightsidecontent.ButtonAddRemove;
import cookbook.mainmenu.rightsidecontent.InputField;
import cookbook.mainmenu.rightsidecontent.TagButton;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AddingRecipeScene {

  // Scene settings
  private int width = 400;
  private Label title = new Label("Add a New Recipe");
  private VBox scene = new VBox();
  private int ingredientsCount = 0;
  private int instructionsCount = 0;

  // Added items
  private ArrayList<IngredientRec> addedIngredientsList = new ArrayList<>();
  private ArrayList<IngredientRec> newlyCreatedIngredientsList = new ArrayList<>();
  private ArrayList<AddedField> addedInstructionsList = new ArrayList<>();
  private ArrayList<Ingredient> ingredientList = this.getIngDbs();
  private ArrayList<Image> recipeImgList = new ArrayList<>();
  private ArrayList<TagButton> allTags = new ArrayList<>();
  private ArrayList<TagButton> selectedTags = new ArrayList<>();

  // Status of adding ingredeints / instructions
  private boolean ingredientAddingInProgress = false;
  private boolean instructionAddingInProgress = false;

  // Label counters
  private Label counterInstruction;
  private Label counterIng;

  // Current uploaded image
  private Path imgPath;
  private VBox addedImgBox = new VBox();
  private HBox addedImg = new HBox();
  private String tmpFolderPath;
  private ImageView recipeImgView;
  private boolean imgIsUploaded = false;

  // Visual elements
  HBox tags = new HBox();

  public AddingRecipeScene(AllRecipesScene allRecipesScene, VBox rightSide, User currentUser, VBox noRecipesFoundBox) {

    // Create path to the tmp folder
    String systemPath = System.getProperty("user.dir");
    File parentFile = new File(systemPath);
    tmpFolderPath = parentFile.getAbsolutePath();

    title.setId("title");

    /********** UPLOAD RECIPE IMAGE SECTION *******************/
    Stage imageSelectStage = new Stage();
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Choose an image for the recipe");
    fileChooser.getExtensionFilters().addAll(
      new FileChooser.ExtensionFilter("JPG", "*.jpg")
    );

    // Button upload image
    VBox addImgBox = new VBox();
    Label addImgLabel = new Label("UPLOAD RECIPE IMAGE");
    addImgLabel.setId("add-img-label");

    // Plus icon
    ImageView plusIconGreen = new ImageView(Images.iconPlusGreen);
    plusIconGreen.setFitHeight(50);
    plusIconGreen.setFitWidth(50);
    ImageView plusIconDarkGreen = new ImageView(Images.iconPlusDarkGreen);
    plusIconDarkGreen.setFitHeight(50);
    plusIconDarkGreen.setFitWidth(50);

    // Hover effect add image box
    addImgBox.hoverProperty().addListener(e -> {
      if (addImgBox.isHover()) {
        addImgBox.getChildren().set(0, plusIconDarkGreen);
        addImgLabel.setStyle("-fx-text-fill: #135131");
      } else {
        addImgBox.getChildren().set(0, plusIconGreen);
        addImgLabel.setStyle("-fx-text-fill: #3e7e2f");
      }
    });

    addImgBox.setAlignment(Pos.CENTER);
    addImgBox.setMinSize(200, 200);
    addImgBox.setMaxSize(200, 200);
    addImgBox.setSpacing(10);
    addImgBox.getChildren().addAll(plusIconGreen, addImgLabel);
    addImgBox.setId("add-img-box");

    // Added image box properties
    Button removeCurrentImg = new Button("REMOVE IMAGE");
    removeCurrentImg.setId("remove-img-btn");
    addedImg.setAlignment(Pos.CENTER);
    addedImg.setPadding(new Insets(0));
    addedImg.setMaxSize(200, 200);
    addedImg.setId("wrapper-img");

    addedImgBox.getChildren().addAll(addedImg, removeCurrentImg);
    addedImgBox.setAlignment(Pos.CENTER);
    addedImgBox.setSpacing(10);
    /**************** END ************************************ */

    // Recipe Name
    InputField recipeNameField = new InputField("Recipe Name", width, false);
    // Recipe Short Description
    InputField recipeShortDesc = new InputField("Short Description", width, true);
    // Recipe Endline
    InputField recipeEndline = new InputField("Other information", width, true);

    // Servings / yields
    HBox yieldsCounterBox = new HBox();
    Label yieldsCounterLabel = new Label("1");
    yieldsCounterLabel.setId("counter-yields");
    Label yieldsLabel = new Label("Servings");
    yieldsLabel.setId("yields-label");
    ButtonAddRemove yieldsCounter = new ButtonAddRemove(this, yieldsCounterLabel);

    yieldsCounterBox.setSpacing(20);
    yieldsCounterBox.setAlignment(Pos.CENTER);
    yieldsCounterBox.setMaxWidth(width);
    yieldsCounterBox.getChildren().addAll(yieldsCounterLabel, yieldsLabel, yieldsCounter);
    yieldsCounterBox.setId("yields-box");

    /***********************************
     ****** Ingredients Section. *********
     *************************************/
    VBox ingredientsBox = new VBox();

    // Title
    HBox titleIngredientsBox = new HBox();
    Label ingredientsLabel = new Label("Ingredients");
    ingredientsLabel.setId("ingredient-label");

    // Adding and Removing ingredients box
    ButtonAddRemove ingredientBtn = new ButtonAddRemove(ingredientsBox, this, ingredientList);

    counterIng = ingredientBtn.getCounter();
    counterIng.setId("counter-ingredient");

    titleIngredientsBox.getChildren().addAll(counterIng, ingredientsLabel, ingredientBtn);
    titleIngredientsBox.setSpacing(20);
    titleIngredientsBox.setAlignment(Pos.CENTER);
    titleIngredientsBox.setId("title-ingredeint-box");

    ingredientsBox.getChildren().addAll(titleIngredientsBox);
    ingredientsBox.setMaxWidth(width);
    ingredientsBox.setId("ingredients-box");
    ingredientsBox.setSpacing(10);
    ingredientsBox.setPadding(new Insets(0, 0, 10, 0));
    /************************************/

    /***********************************
     ****** Instructions Section.s *********
     *************************************/
    VBox instructionsBox = new VBox();
    // Title
    HBox titleInstructionsBox = new HBox();

    // Adding and Removing instructions box
    ButtonAddRemove instructionBtn = new ButtonAddRemove(instructionsBox, this);

    Label instructionsLabel = new Label("Instructions");
    instructionsLabel.setId("ingredient-label");

    counterInstruction = instructionBtn.getCounter();
    counterInstruction.setId("counter-ingredient");

    titleInstructionsBox.getChildren().addAll(counterInstruction, instructionsLabel, instructionBtn);
    titleInstructionsBox.setSpacing(20);
    titleInstructionsBox.setAlignment(Pos.CENTER);
    titleInstructionsBox.setId("title-ingredeint-box");

    instructionsBox.getChildren().addAll(titleInstructionsBox);
    instructionsBox.setMaxWidth(width);
    instructionsBox.setId("ingredients-box");
    instructionsBox.setSpacing(10);
    instructionsBox.setPadding(new Insets(0, 0, 10, 0));
    /************************************/

    /***********************************
     ******** Tags Section.s ***********
     ***********************************/
    // Box Label
    Label tagLabel = new Label("Tags");
    tagLabel.setId("tags-label");
    Tooltip tagLabTooltip = new Tooltip("You can select up to 3 Tags for your recipe.");
    tagLabel.setAlignment(Pos.CENTER);
    
    // Add hover feature
    tagLabTooltip.setStyle("-fx-font-size: 12px;");
    Tooltip.install(tagLabel, tagLabTooltip);

    //Container for all tags
    tags.setSpacing(10);
    tags.setId("ingredients-box");
    tags.setPadding(new Insets(0, 5, 5, 5));
    tags.setAlignment(Pos.CENTER);

    // Custom tags adding button
    AddTagButton customTag = new AddTagButton(this);
    customTag.setMaxHeight(30);

    // All all existing tags as buttons
    for (Tag tag : this.getTagsDbs()) {
      TagButton dispTag = new TagButton(tag);
      allTags.add(dispTag);
    }
    tags.getChildren().add(customTag);
    tags.getChildren().addAll(this.allTags);
    
    ScrollPane tagsPane = new ScrollPane();
    tagsPane.setMaxWidth(400);
    tagsPane.setMinHeight(60);
    tagsPane.fitToHeightProperty().set(true);
    tagsPane.pannableProperty().set(true);
    tagsPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
    tagsPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
    tagsPane.setPrefHeight(30);
    tagsPane.setContent(tags);
    tagsPane.setId("scroll-box");

    VBox tagsLab = new VBox();
    tagsLab.setSpacing(10);
    tagsLab.setAlignment(Pos.CENTER);
    tagsLab.setMinHeight(100);
    tagsLab.setMaxWidth(width);
    tagsLab.setId("tags-label");
    tagsLab.getChildren().addAll(tagLabel, tagsPane);
    /************************************/

    // Buttons bottom HBOX
    HBox buttonxBox = new HBox();
    // Button ADD Recipe
    Button addRecipeBtn = new Button("ADD RECIPE");
    addRecipeBtn.setId("add-recipe-btn");
    // Return button
    Button returnToRecipesBtn = new Button("< BACK");
    returnToRecipesBtn.setId("return-btn");
    buttonxBox.setAlignment(Pos.CENTER);
    buttonxBox.setSpacing(20);
    buttonxBox.getChildren().addAll(returnToRecipesBtn, addRecipeBtn);

    // Scroll Box
    VBox main = new VBox();
    main.setAlignment(Pos.CENTER);
    main.setSpacing(15);
    main.getChildren().addAll(
        addImgBox,
        recipeNameField,
        recipeShortDesc,
        recipeEndline,
        yieldsCounterBox,
        ingredientsBox,
        instructionsBox,
        tagsLab);
    ScrollPane scrollBox = new ScrollPane();
    scrollBox.setFitToWidth(true);
    scrollBox.setPrefHeight(550);
    scrollBox.setContent(main);
    scrollBox.setId("scroll-box");

    // Scene settings
    scene.setPadding(new Insets(0, 30, 0, 30));
    scene.setSpacing(15);
    scene.setAlignment(Pos.CENTER);
    scene.getChildren().addAll(title, scrollBox, buttonxBox);
    scene.getStylesheets().addAll("file:styles/AddingRecipeScene.css");

    // Tag toggle button
    for (TagButton tb: allTags) {
      tb.setOnAction(e -> {
        this.addTagButtonToSelected(tb);
      });
    }

    // Add Recipe Button
    addRecipeBtn.setOnAction(e -> {

      // TO DO: Somehow utilize the boolean imgIsuploaded to not let
      // a user create a recipe without image,
      // or let create it but with no image provided
      if (imgIsUploaded) {

      }
      /******************** */

      // Build longDesc string from instructions
      StringBuilder longDescBuilder = new StringBuilder();
      for (AddedField instruction : addedInstructionsList) {
        String temp = instruction.getName().replace(';', '.');
        longDescBuilder.append(temp + ";");
      }
      String longDesc = longDescBuilder.toString();

      // Yields count
      int yieldsCount = yieldsCounter.getCount();

      Recipe rec = new Recipe(
          -1,
          recipeNameField.getText(),
          recipeShortDesc.getText(),
          longDesc,
          recipeEndline.getText(),
          currentUser.getId(),
          Date.valueOf(LocalDate.now()),
          yieldsCount,
          0,
          -1
          );
      for (TagButton tagButt : selectedTags) {
        rec.addTag(tagButt.getTag());
      }

      /*********************************** */
      /*** DB insertion as a transaction ***/

      try {
        DbConnector.conn.setAutoCommit(false);

        // Create a picture blob thing.
        if (this.imgPath != null) {
          rec.setImage(Picture.imgUpload(this.imgPath));
        }

        System.out.println(rec.getImage());
        
        // Add Recipe to DB
        Databaseio.createNewRecipe(rec);

        // Get recipe ID
        int recId = Databaseio.getLastId();
        rec.setId(recId);

        // Check if user defined ingredients already exist in DB.
        // Add if not contained in all ingredients.

        //Get all ingredient names
        ArrayList<String> allIngNames = new ArrayList<>();
        for (Ingredient ing : ingredientList) {
          allIngNames.add(ing.getName().toLowerCase());
        }

        //Check if added ingredient is already in Ingredients table.
        for (IngredientRec ingredient : addedIngredientsList) {
          int tempIngId = ingredient.getId();
          if (!allIngNames.contains(ingredient.getName().toLowerCase())) {
            DbConnector.runUpdateQuery(
              """
              INSERT INTO ingredients (name, unit)
              VALUES (?, ?);
              """,
              ingredient.getName(),
              ingredient.getUnit()
              );
            tempIngId = Databaseio.getLastId();
            ingredient.setId(tempIngId);
          }
        }
        // Add Recipe ingredients
        Databaseio.addRecIng(addedIngredientsList, recId);

        // Add Recipe tags
        Databaseio.addRecTags(rec);

        // Add all changes as a transaction.
        DbConnector.conn.commit();

        // Re-enable autocommit
        DbConnector.conn.setAutoCommit(true);

        // Alert upon successfull addition
        Alert success = new Alert(AlertType.INFORMATION, "Recipe Successfully Added");
        success.setHeaderText(null);
        success.showAndWait();

      } catch (Exception e1) {
        // Enable autocommit
        try {
          DbConnector.conn.rollback();
          DbConnector.conn.setAutoCommit(true);
        } catch (SQLException e2) {
          e2.printStackTrace();
        }
        Alert noRecipeAlert = new Alert(AlertType.ERROR, "Cannot add recipe to Data Base");
        noRecipeAlert.showAndWait();
        e1.printStackTrace();
      }

      /******* TES PRINT OUTT **********/
      System.out.println("Image: " + recipeImgList.size());
      System.out.println("Recipe name: " + recipeNameField.getText());
      System.out.println("Short description: " + recipeShortDesc.getText());
      System.out.println("Recipe Endline: " + recipeEndline.getText());
      System.out.println("Yields count: " + yieldsCount);

      System.out.println("Ingredients: ");
      for (Ingredient ingredient : addedIngredientsList) {
        System.out.println(ingredient.getName());
      }

      System.out.println("Instructions: ");
      for (AddedField addedField : addedInstructionsList) {
        System.out.println(addedField.getName());
      }

      System.out.println("Long Desc: " + longDesc);

      System.out.println("Newly added ingredients!");
      for (Ingredient ingredient : newlyCreatedIngredientsList) {
        System.out.println(ingredient.getName());
      }
      /**********************/

      rec.setIngredients(addedIngredientsList);
    });

    // Button Upload an image
    addImgBox.setOnMouseClicked(e -> {

      /***************************************************** */
      /*** SELECTS AN IMAGE AND COPIES IT TO THE TMP FOLDER */
      /***************************************************** */
      File openedFile = fileChooser.showOpenDialog(imageSelectStage);
      File outputFile = new File(tmpFolderPath + "/tmp/" + "addingRecipeImg.png");

      FileInputStream fis = null;
      FileOutputStream fos = null;

      try {
        fis = new FileInputStream(openedFile);
        fos = new FileOutputStream(outputFile);
        BufferedInputStream bin = new BufferedInputStream(fis);
        BufferedOutputStream bou = new BufferedOutputStream(fos);

        int i = 0;
        while (i != -1) {
          i = bin.read();
          bou.write(i);
        }
        bin.close();
        bou.close();
        if (fis != null) {
          fis.close();
        }
        if (fos != null) {
          fos.close();
        }
      } catch (Exception exception) {
        System.out.println(exception);
      }
      /***************************************************** */
      /******************* END ************************ */
      /***************************************************** */

      // Creates new image from specified path
      // And adds to array
      this.imgPath = outputFile.toPath();
      Image img = new Image("file:./tmp/addingRecipeImg.png");
      
      recipeImgList.add(img);
      if (recipeImgList.size() > 0) {
        imgIsUploaded = true;
      }

      // Recipe Image view after user uploads an image
      recipeImgView = new ImageView(img);
      recipeImgView.setPreserveRatio(true);
      recipeImgView.setFitHeight(186);
      recipeImgView.setFitWidth(186);

      // Display the selected image
      addedImg.getChildren().add(recipeImgView);
      main.getChildren().set(0, addedImgBox);
    });

    // Button remove current uploaded recipe image
    removeCurrentImg.setOnAction(e -> {
      imgIsUploaded = false;
      recipeImgList.clear();
      addedImg.getChildren().clear();
      main.getChildren().set(0, addImgBox);
    });

    // Button Return to recipes scene
    returnToRecipesBtn.setOnAction(e -> {
      rightSide.getChildren().clear();
      rightSide.getChildren().add(new AllRecipesScene(currentUser, rightSide));
    });
  }

  public VBox getScene() {
    return scene;
  }

  public void createAddIngredeintField(VBox ingredientsBox, ArrayList<Ingredient> ingredientList) {
    setIngredientAddingProgress(true);
    AddIngredientField addIng = new AddIngredientField(this, ingredientsBox, ingredientList, ingredientsCount);
    ingredientsBox.getChildren().add(1, addIng);
  }

  public void removeAddIngredeintField(VBox ingredientsBox, AddIngredientField addIng) {
    ingredientsBox.getChildren().remove(addIng);
  }

  public void increaseIngredientCount() {
    ingredientsCount++;
    refreshCounterIngredients();
  }

  public void decreaseIngredientCount() {
    ingredientsCount--;
    refreshCounterIngredients();
  }

  public void increaseInstructionCount() {
    instructionsCount++;
    refreshCounterInstructions();
  }

  public void decreaseInstructionCount() {
    instructionsCount--;
    refreshCounterInstructions();
  }

  public void createInstructionFiled(VBox instructionsBox) {
    setInstructionAddingProgress(true);
    increaseInstructionCount();
    AddInstructionField addInstruction = new AddInstructionField(this, instructionsBox, instructionsCount);
    instructionsBox.getChildren().add(1, addInstruction);
  }

  public void addIngredientToArray(IngredientRec ingredeint) {
    addedIngredientsList.add(ingredeint);
  }

  public void addNewlyCreatedIngredientToArray(IngredientRec ingredeint) {
    newlyCreatedIngredientsList.add(ingredeint);
  }

  public void removeIngredientToArray(IngredientRec ingredient) {
    addedIngredientsList.remove(ingredient);
  }

  public void removeNewlyCreatedIngredientToArray(Ingredient ingredient) {
    newlyCreatedIngredientsList.remove(ingredient);
  }

  public void addInstructionToArray(AddedField instructionField) {
    addedInstructionsList.add(instructionField);
  }

  public void removeInstructionToArray(AddedField instructionField) {
    addedInstructionsList.remove(instructionField);
  }

  public void refreshCounterIngredients() {
    counterIng.setText(ingredientsCount + "");
    ;
  }

  public void refreshCounterInstructions() {
    counterInstruction.setText(instructionsCount + "");
    ;
  }

  public void setIngredientAddingProgress(Boolean bool) {
    ingredientAddingInProgress = bool;
  }

  public boolean getIngredientAddingProgress() {
    return ingredientAddingInProgress;
  }

  public void setInstructionAddingProgress(Boolean bool) {
    instructionAddingInProgress = bool;
  }

  public boolean getInstructionsAddingProgress() {
    return instructionAddingInProgress;
  }

  public void rearrangeInstructionsList() {
    int count = 1;
    for (AddedField field : addedInstructionsList) {
      field.setInstructionNumber(count + "");
      count++;
    }
  }

  private void addTagButtonToSelected(TagButton tb) {
    if (this.selectedTags.contains(tb)) {
      this.selectedTags.remove(tb);
      tb.selectedProperty().set(false);
    } else {
      // De-select last tag in the list
      if (this.selectedTags.size() > 2) {
        this.selectedTags.get(2).selectedProperty().set(false);
        this.selectedTags.remove(2);
      }
      this.selectedTags.add(tb);
    }
  }

  public void addTagToSelected(Tag tag) {
    TagButton tb = new TagButton(tag);
    tb.setOnAction(e -> {
      this.addTagButtonToSelected(tb);
    });
    this.allTags.add(tb);
    this.addTagButtonToSelected(tb);
    tb.selectedProperty().set(true);
    this.tags.getChildren().add(1, tb);
  }

  /**
   * Fetch all ingredients from DB.
   * 
   * @return - ArrayList of all ingredients
   */
  private ArrayList<Ingredient> getIngDbs() {
    ArrayList<Ingredient> allIng = new ArrayList<Ingredient>();

    try {
      ResultSet rs = DbConnector.runQuery(
          "SELECT * FROM ingredients");
      while (rs.next()) {
        Ingredient ingredient = new Ingredient(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("unit"));
        allIng.add(ingredient);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return allIng;
  }

  /**
   * Fetch all tags from DB.
   * 
   * @return - ArrayList of all tags
   */
  private ArrayList<Tag> getTagsDbs() {
    ArrayList<Tag> allTag = new ArrayList<Tag>();

    try {
      ResultSet rs = DbConnector.runQuery(
          "SELECT * FROM tags");
      while (rs.next()) {
        Tag tag = new Tag(
            rs.getInt("id"),
            rs.getString("name")
            );
        allTag.add(tag);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return allTag;
  }
}
