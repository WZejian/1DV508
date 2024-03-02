package cookbook.mainmenu.rightsidecontent;

import cookbook.Databaseio;
import cookbook.DbConnector;
import cookbook.Message;
import cookbook.User;
import cookbook.mainmenu.MainMenu;
import cookbook.mainmenu.rightsidecontent.scenes.MessagesScene;
import cookbook.mainmenu.rightsidecontent.scenes.recipeview.RecipeViewScene;
import java.sql.SQLException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MessageCard extends HBox {

  /**
   * A visual way to display messages.
   * @param message - message object to be displayed
   * @param rightSide - right side of the main menu
   * @param currentUser - logged in user.
   */
  public MessageCard(Message message, MessagesScene rightSide, User currentUser, MainMenu mainMenu) {

    //this.setMinSize(width, height);
    //this.setMaxSize(width, height);
    this.setAlignment(Pos.TOP_LEFT);
    this.setSpacing(12);
    this.setId("root");
    this.setPadding(new Insets(10, 5, 10, 5));

    // Author
    Label author;
    try {
      author = new Label(
        String.format(
          "Sender: %s",
          Databaseio.useridToUsername(message.getSenderId()))
        );
    } catch (SQLException e1) {
      author = new Label("");
      e1.printStackTrace();
    }
    author.setId("Author");

    //Date
    Label recDate = new Label(message.getDate());
    recDate.setId("Author");

    // Top Half
    VBox topHalf = new VBox();
    topHalf.setAlignment(Pos.TOP_LEFT);
    topHalf.setStyle(
      """
        -fx-background-color: rgba(62, 126, 47, .5);
        -fx-background-radius: 10px;
      """
      );
    topHalf.setMinWidth(400);
    topHalf.setMaxWidth(400);
    topHalf.setPadding(new Insets(0, 0, 0, 5));
    topHalf.getChildren().addAll(author, recDate);

    // Message content
    VBox bottomHalf = new VBox();
    bottomHalf.setAlignment(Pos.TOP_LEFT);
    bottomHalf.setMaxWidth(400);
    bottomHalf.setPadding(new Insets(5, 0, 0, 5));

    Label messCont = new Label(message.getContent());
    messCont.setMaxWidth(400);
    messCont.setMinHeight(105);
    messCont.setWrapText(true);

    // Accept or deny recipe
    HBox acceptButtons = new HBox();
    acceptButtons.setId("my-buttons-background");
    acceptButtons.setSpacing(20);
    acceptButtons.setAlignment(Pos.CENTER);
    acceptButtons.setMinWidth(400);
    acceptButtons.setPadding(new Insets(5, 0, 5, 0));

    // Buttons
    Button accept = new Button("Accept");
    accept.setId("confirm-button");
    Button deny = new Button("Deny");
    deny.setId("reject-button");
    acceptButtons.getChildren().addAll(deny, accept);

    accept.setOnAction(acceptFavRecipe -> {
      if (!Databaseio.isStar(currentUser.getId(), message.getRecipe().getId())) {
        // Star recipe and delete message
        Databaseio.star(currentUser.getId(), message.getRecipe().getId());
      }

      try {
        DbConnector.runUpdateQuery("DELETE FROM cookbook.messages WHERE id=?;", message.getMessageId());
        // Delete message card and update messages
        mainMenu.updateMessage();
        rightSide.removeCard(this);
        mainMenu.setMessageButton(new MessageButton(rightSide.getMessageSize()));
      } catch (Exception err) {
        err.printStackTrace();
      }
    });

    deny.setOnAction(denyRecipe -> {
      try {
        DbConnector.runUpdateQuery("DELETE FROM cookbook.messages WHERE id=?;", message.getMessageId());
        // Delete message card and update messages
        mainMenu.updateMessage();
        rightSide.removeCard(this);
        mainMenu.setMessageButton(new MessageButton(rightSide.getMessageSize()));
      } catch (Exception err) {
        err.printStackTrace();
      }
    });

    bottomHalf.getChildren().addAll(messCont, acceptButtons);


    // Right hand side box with details
    VBox items = new VBox();
    items.setAlignment(Pos.TOP_LEFT);
    items.setPadding(new Insets(5, 5, 5, 5));
    //items.setMinSize(170, 140);
    items.setMaxWidth(400);
    items.getChildren().addAll(topHalf, bottomHalf);

    // Recipe Image
    RecipeCard recipeCard = new RecipeCard(message.getRecipe(), rightSide, currentUser);

    // Contents Box
    this.getChildren().addAll(recipeCard, items);
    this.getStylesheets().add("file:styles/MessageCard.css");

    // Action on click
    this.setOnMouseClicked(e -> {
      RecipeViewScene recipeScene = new RecipeViewScene(message.getRecipe(), currentUser);
      rightSide.getChildren().clear();
      rightSide.getChildren().add(recipeScene.getScene());
    });
  }
}
