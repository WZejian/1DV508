package cookbook.mainmenu.rightsidecontent.scenes.recipeview;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import cookbook.Comment;
import cookbook.Databaseio;
import cookbook.Images;
import cookbook.IngredientRec;
import cookbook.Picture;
import cookbook.Recipe;
import cookbook.Tag;
import cookbook.User;
import cookbook.mainmenu.rightsidecontent.ButtonAddRemove;
import cookbook.mainmenu.rightsidecontent.scenes.AddWeekDinnerScene;
import cookbook.mainmenu.rightsidecontent.scenes.MyRecipesScene;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

public class RecipeViewScene {

  private VBox main = new VBox();
  private ScrollPane scrollBox = new ScrollPane();
  private VBox scrollBoxContent = new VBox();
  private AddWeekDinnerScene addWeekDin = new AddWeekDinnerScene();

  // private HBox topBox = new HBox();
  private HBox topBox = new HBox();
  private HBox topBoxRightSide = new HBox();
  private VBox dateTagYieldBox = new VBox();
  private HBox dateBox = new HBox();
  private VBox tagBox = new VBox();
  private HBox yieldBox = new HBox();
  private VBox buttonsBox = new VBox();

  // Ingredients and yields
  private ArrayList<IngredientField> ingredientFieldList = new ArrayList<>();

  /**
   * Recipe view.
   *
   * @param recipe      Recipe to display
   * @param currentUser Current user
   */
  public RecipeViewScene(Recipe recipe, User currentUser) {

    // Recipe name / title
    Label nameLabel = new Label(recipe.getName());
    nameLabel.setId("recipe-name-label");

    // Recipe Image
    Image recipeImg = new Image(Picture.imgFetchFileInputStream(recipe.getImage()));
    ImageView imgView = new ImageView(recipeImg);
    imgView.setFitHeight(200);
    imgView.setFitWidth(200);

    // Recipe date label
    Label dateLabel = new Label("Date");
    dateLabel.setId("date-label");
    Label date = new Label(recipe.getDate());
    date.setPadding(new Insets(2, 5, 2, 5));
    date.setId("date");
    dateBox.setAlignment(Pos.CENTER);
    dateBox.setSpacing(5);
    dateBox.setPadding(new Insets(0, 0, 0, 0));
    dateBox.setId("date-box");
    dateBox.getChildren().addAll(dateLabel, date);

    ArrayList<Tag> recipeTagsList = recipe.getTags();
    for (Tag tag : recipeTagsList) {
      TagField tagField = new TagField(tag.getName(), Images.iconTagGreen);
      tagBox.getChildren().add(tagField);
    }

    // Side Buttons
    final SideButton starBtn = new SideButton("Star", Images.iconStarHollow);
    if (Databaseio.isStar(currentUser.getId(), recipe.getId())) {
      starBtn.setText("Unstar");
      starBtn.setImage(Images.iconStarYellow);
    }

    SideButton addWeeklyDinnerBtn = new SideButton("Weekly Dinner", Images.iconDinnerYellow);
    SideButton shareBtn = new SideButton("Share", Images.iconShareYellow);
    SideButton addCommentBtn = new SideButton("Add Comment", Images.iconCommentYellow);
    buttonsBox.setAlignment(Pos.CENTER_RIGHT);
    buttonsBox.setSpacing(10);
    buttonsBox.getChildren().addAll(starBtn, addWeeklyDinnerBtn, shareBtn, addCommentBtn);

    // Yields
    ButtonAddRemove yieldBtn = new ButtonAddRemove(this, recipe.getYield(), ingredientFieldList);
    Label yieldLabel = new Label("Yield");
    yieldLabel.setId("yield-label");
    yieldBox.setAlignment(Pos.CENTER);
    yieldBox.getChildren().addAll(yieldLabel, yieldBtn);
    yieldBox.setId("yield-box");

    dateTagYieldBox.setSpacing(10);
    dateTagYieldBox.setAlignment(Pos.CENTER);
    dateTagYieldBox.getChildren().addAll(dateBox, tagBox, yieldBox);

    topBoxRightSide.setSpacing(110);
    topBoxRightSide.getChildren().addAll(dateTagYieldBox, buttonsBox);

    topBox.setPadding(new Insets(5, 10, 5, 10));
    topBox.setAlignment(Pos.CENTER_LEFT);
    topBox.setSpacing(40);
    topBox.getChildren().addAll(imgView, topBoxRightSide);
    topBox.setId("top-box");

    // Short description section
    RecipeViewSection descSection = new RecipeViewSection("Short Description", false);
    TextAreaField shortDesc = new TextAreaField(recipe.getShortDesc());
    descSection.addToSectionHbox(shortDesc);

    // Ingredients section
    RecipeViewSection ingredientsSection = new RecipeViewSection("Ingredients", false);
    ArrayList<IngredientRec> ingredeintsList = recipe.getIngredients();
    // Change list to recipe.getIngredients() and recipe.getYield()
    for (IngredientRec ingredient : ingredeintsList) {
      IngredientField ingField = new IngredientField(ingredient, recipe.getYield());
      ingredientFieldList.add(ingField);
      ingredientsSection.addToSectionHbox(ingField.getIngredientField());
    }

    // Instruction/Steps section
    RecipeViewSection instructionsSection = new RecipeViewSection("Steps", false);
    int taskCount = 1;
    for (String step : recipe.getLongDesc()) {
      TextAreaField stepLabel = new TextAreaField(taskCount, step.trim());
      // stepLabel.setId("step-label");
      instructionsSection.addToSectionHbox(stepLabel);
      taskCount++;
    }

    // Endnote section
    RecipeViewSection endnoteSection = new RecipeViewSection("Other Information", false);
    TextAreaField endline = new TextAreaField(recipe.getEndLine());
    endnoteSection.addToSectionHbox(endline);

    // Comments section
    RecipeViewSectionComment commentsSection = new RecipeViewSectionComment("Comments", currentUser, recipe.getId());
    ArrayList<Comment> commentList = recipe.getComments();
    for (Comment comment : commentList) {
      CommentField commentField = new CommentField(comment, currentUser);
      commentsSection.addToSectionVbox(commentField.getCommentField());
    }

    // Delete recipe button
    Button delRecButton = new Button("Delete recipe");
    delRecButton.setId("delete-button");

    // HBox for delete button or maybe edit in the future
    HBox delRecHBox = new HBox(delRecButton);
    delRecHBox.setAlignment(Pos.TOP_LEFT);

    // scrollBoxContent settings
    scrollBoxContent = new VBox();
    scrollBoxContent.setId("scroll-box-content");
    scrollBoxContent.setAlignment(Pos.CENTER);
    scrollBoxContent.setPadding(new Insets(30, 30, 30, 30));
    scrollBoxContent.setSpacing(20);
    scrollBoxContent.getChildren().addAll(
        topBox,
        descSection.getSection(),
        ingredientsSection.getSection(),
        instructionsSection.getSection(),
        endnoteSection.getSection(),
        commentsSection.getSection());

    // Add delete button if is user is the owner or an admin.
    if (currentUser.isAdmin() || currentUser.getId() == recipe.getAuthor()) {
      scrollBoxContent.getChildren().add(1, delRecHBox);
    }

    // ScrollPane settings
    scrollBox.setContent(scrollBoxContent);
    scrollBox.setPadding(new Insets(0, 0, 0, 0));
    scrollBox.setFitToHeight(true);
    scrollBox.setFitToWidth(true);
    scrollBox.setId("scroll-box");

    // Main window
    main.setSpacing(20);
    main.setAlignment(Pos.CENTER);
    main.getChildren().addAll(nameLabel, scrollBox);
    main.getStylesheets().add("file:styles/RecipeViewScene.css");

    /*******************************/
    /***** BUTTON ACTIONS ************/
    /*******************************/

    // Star button meichen
    starBtn.setOnAction(e -> {
      // if already star, unstar it
      if (Databaseio.isStar(currentUser.getId(), recipe.getId())) {
        Databaseio.unStar(currentUser.getId(), recipe.getId());
        starBtn.setText("Star");
        starBtn.setImage(Images.iconStarHollow);
      } else { // if not star, star it
        Databaseio.star(currentUser.getId(), recipe.getId());
        starBtn.setText("Unstar");
        starBtn.setImage(Images.iconStarYellow);
      }
    });

    // Weekly dinner button
    addWeeklyDinnerBtn.setOnAction(e -> {
      addWeekDin.promptForYieldAndDate(currentUser, recipe.getId(), recipe);
    });

    // Share button
    shareBtn.setOnAction(e -> {
      // Create the custom dialog.
      Dialog<Pair<String, String>> dialog = new Dialog<>();
      dialog.setTitle("Share the recipe to your friend");
      dialog.setHeaderText(null);

      // Set the icon (must be included in the project).
      dialog.setGraphic(null);

      // Set the button types.
      ButtonType confirmButtonType = new ButtonType("Confirm", ButtonData.OK_DONE);
      dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);

      // Create the username and password labels and fields.
      GridPane grid = new GridPane();
      grid.setHgap(10);
      grid.setVgap(10);
      grid.setPadding(new Insets(20, 20, 10, 10));

      TextField username = new TextField();
      username.setPrefWidth(200);
      TextArea message = new TextArea();
      message.setWrapText(true);
      message.setPrefWidth(200);
      message.setPrefHeight(50);

      grid.add(new Label("Receiver's username:"), 0, 0);
      grid.add(username, 1, 0);
      grid.add(new Label("Message:"), 0, 1);
      grid.add(message, 1, 1);

      // Enable/Disable login button depending on whether a username was entered.
      Node confirButton = dialog.getDialogPane().lookupButton(confirmButtonType);
      confirButton.setDisable(true);

      // Do some validation (using the Java 8 lambda syntax).
      username.textProperty().addListener((observable, oldValue, newValue) -> {
        confirButton.setDisable(newValue.trim().isEmpty());
      });

      dialog.getDialogPane().setContent(grid);

      // Request focus on the username field by default.
      Platform.runLater(() -> username.requestFocus());

      dialog.setResultConverter(dialogButton -> {
        if (dialogButton == confirmButtonType) {
          return new Pair<>(username.getText(), message.getText());
        }
        return null;
      });

      Optional<Pair<String, String>> result = dialog.showAndWait();

      result.ifPresent(usernameMessage -> {
        String receiverName = usernameMessage.getKey();
        String messageContent = usernameMessage.getValue();
        int receiverId = Databaseio.checkUserExist(receiverName);
        if (receiverId == -1) {
          Alert alert = new Alert(AlertType.ERROR);
          alert.setTitle("Warning");
          alert.setHeaderText(null);
          alert.setContentText("The receiver is not existed.");
          alert.showAndWait();
        } else {
          Databaseio.addMessage(currentUser.getId(), receiverId, recipe.getId(), messageContent);
          Alert alert = new Alert(AlertType.INFORMATION);
          alert.setTitle("Message Sent");
          alert.setHeaderText(null);
          alert.setContentText("The message is successfully sent.");
          alert.showAndWait();
        }
      });
    });

    // Add Comment button
    addCommentBtn.setOnAction(e -> {
      // Create the custom dialog.
      Dialog<String> dialog = new Dialog<>();
      dialog.setTitle("Add a comment to the recipe");
      dialog.setHeaderText(null);
      dialog.setGraphic(null);

      // Set the button types.
      ButtonType confirm = new ButtonType("Confirm", ButtonData.OK_DONE);
      dialog.getDialogPane().getButtonTypes().addAll(confirm, ButtonType.CANCEL);

      // Create the username and password labels and fields.
      GridPane grid = new GridPane();
      grid.setHgap(10);
      grid.setVgap(10);
      grid.setPadding(new Insets(20, 20, 10, 10));

      TextArea com = new TextArea();
      com.setPromptText("Be nice...");
      com.setWrapText(true);
      com.setPrefWidth(200);
      com.setPrefHeight(100);
      grid.add(new Label("Write Something:"), 0, 0);
      grid.add(com, 1, 0);

      // Enable/Disable login button depending on whether a username was entered.
      Node confirmButton = dialog.getDialogPane().lookupButton(confirm);
      confirmButton.setDisable(true);

      // Do some validation (using the Java 8 lambda syntax).
      com.textProperty().addListener((observable, oldValue, newValue) -> {
        confirmButton.setDisable(newValue.trim().isEmpty());
      });

      dialog.getDialogPane().setContent(grid);

      // Request focus on the username field by default.
      Platform.runLater(() -> com.requestFocus());

      // Convert the result to a username-password-pair when the login button is
      // clicked.
      dialog.setResultConverter(dialogButton -> {
        if (dialogButton == confirm) {
          return new String(com.getText());
        }
        return null;
      });
      Optional<String> result = dialog.showAndWait();

      result.ifPresent(commentContent -> {
        Comment comObj = new Comment();
        comObj.setAuthorId(currentUser.getId());
        comObj.setContent(commentContent);
        comObj.setRecipeId(recipe.getId());
        Databaseio.addComment(comObj);
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Comment Added");
        alert.setHeaderText(null);
        alert.setContentText("The comment is successfully added.");
        alert.showAndWait();
      });
    });

    // Delete Recipe button
    delRecButton.setOnAction(e -> {
      Alert deleteRecAlert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete: " + recipe.getName());
      deleteRecAlert.showAndWait().ifPresent(del -> {
        if (del == ButtonType.OK) {
          try {
            Databaseio.deleteRecipe(recipe);
            Alert yesDelete = new Alert(AlertType.INFORMATION, "Recipe successfully deleted");
            yesDelete.showAndWait();
            main.getChildren().clear();
            main.getChildren().add(new MyRecipesScene(currentUser, this.main));
          } catch (SQLException e1) {
            Alert noDelete = new Alert(AlertType.ERROR, "Something went wrong. Cannot delete the recipe");
            noDelete.showAndWait();
            e1.printStackTrace();
          }
        }
      });
    });

  }

  public VBox getScene() {
    return main;
  }
}
