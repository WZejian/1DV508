package cookbook.mainmenu.rightsidecontent;

import cookbook.Databaseio;
import cookbook.Images;
import cookbook.Picture;
import cookbook.Recipe;
import cookbook.Tag;
import cookbook.User;
import cookbook.mainmenu.rightsidecontent.scenes.recipeview.RecipeViewScene;
import java.sql.SQLException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RecipeCard extends VBox {

  private final int width = 300;
  private final int height = 200;
  private final int topBarHeight = 10;

  /**
   * Creator method for a recipe card from a Java Recipe object.
   * 
   * @param recipe - a cookbook recipe object.
   */
  public RecipeCard(Recipe recipe, VBox rightSide, User currentUser) {

    this.setMinSize(width, height);
    this.setMaxSize(width, height);
    this.setAlignment(Pos.TOP_LEFT);
    this.setSpacing(12);
    this.setId("root");

    // Name of the recipe
    Label recipeLabel = new Label(recipe.getName());
    recipeLabel.setId("recipe-label");

    VBox nameBox = new VBox();
    nameBox.setId("name-box");
    nameBox.setMinSize(width - 15, topBarHeight);
    nameBox.setAlignment(Pos.CENTER);
    nameBox.setPadding(new Insets(7, 3, 7, 3));
    nameBox.getChildren().addAll(recipeLabel);

    // Recipe Image
    Image img = new Image(Picture.imgFetchFileInputStream(recipe.getImage()));
    ImageView imgView = new ImageView(img);
    // ImageView imgView = new ImageView(Images.testImage);
    imgView.setFitHeight(120);
    imgView.setFitWidth(120);

    // Author
    Label author;
    try {
      author = new Label(
          String.format(
              "Author: %s",
              Databaseio.useridToUsername(recipe.getAuthor())));
    } catch (SQLException e1) {
      author = new Label("");
      e1.printStackTrace();
    }
    author.setId("Author");

    // Date
    Label recDate = new Label(recipe.getDate());
    recDate.setId("Author");

    // Top Half
    VBox topHalf = new VBox();
    topHalf.setAlignment(Pos.TOP_LEFT);
    topHalf.setMaxSize(170, 60);
    topHalf.setPadding(new Insets(0, 0, 0, 5));
    topHalf.getChildren().addAll(author, recDate);

    // All tags in a VBox
    VBox bottomHalf = new VBox();
    bottomHalf.setAlignment(Pos.TOP_LEFT);
    bottomHalf.setMaxSize(170, 60);
    bottomHalf.setPadding(new Insets(0, 0, 0, 5));

    // Create tag labels
    if (recipe.getTags() != null) {
      for (Tag tag : recipe.getTags()) {
        Label dispTag = new Label(tag.getName(), this.getLabelImg());
        dispTag.setId("tag");
        bottomHalf.getChildren().add(dispTag);
      }
    }

    // Right hand side box with details
    VBox descTag = new VBox();
    descTag.setAlignment(Pos.TOP_LEFT);
    descTag.setPadding(new Insets(5, 5, 5, 5));
    descTag.setMinSize(170, 140);
    descTag.setMaxSize(170, 140);
    descTag.getChildren().addAll(topHalf, bottomHalf);

    // Contents Box
    HBox contents = new HBox();
    contents.getChildren().addAll(imgView, descTag);

    this.getChildren().addAll(nameBox, contents);
    this.getStylesheets().add("file:styles/RecipeCard.css");

    // Tooltip - text when hovering
    Tooltip hoverDesc = new Tooltip(recipe.getShortDesc());
    hoverDesc.setMaxWidth(300);
    hoverDesc.setWrapText(true);
    hoverDesc.setAutoHide(false);
    Tooltip.install(this, hoverDesc);

    // Hover properties
    this.hoverProperty().addListener(e -> {
      if (this.isHover()) {
        this.setStyle("-fx-border-color: #135131;");
        nameBox.setStyle("-fx-background-color: #135131;");
      } else {
        this.setStyle("-fx-border-color: #3e7e2f;");
        nameBox.setStyle(" -fx-background-color: #3e7e2f");
      }
    });

    // Action on click
    this.setOnMouseClicked(e -> {
      RecipeViewScene recipeScene = new RecipeViewScene(recipe, currentUser);
      rightSide.getChildren().clear();
      rightSide.getChildren().add(recipeScene.getScene());
    });
  }

  private ImageView getLabelImg() {
    ImageView lblView = new ImageView(Images.iconTagGreen);
    lblView.preserveRatioProperty().set(true);
    lblView.fitHeightProperty().set(10);

    return lblView;
  }
}
