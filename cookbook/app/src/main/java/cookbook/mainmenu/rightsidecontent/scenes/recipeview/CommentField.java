package cookbook.mainmenu.rightsidecontent.scenes.recipeview;

import cookbook.Comment;
import cookbook.Databaseio;
import cookbook.User;
import java.sql.SQLException;
import java.util.Optional;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class CommentField {

  private VBox main = new VBox();
  private BorderPane topBox = new BorderPane();
  private HBox authorbox = new HBox();
  private HBox buttonsBox = new HBox();

  /**
   * Comment field.
   *
   * @param comment     Comment obj
   * @param currentUser Logged in user
   */
  public CommentField(Comment comment, User currentUser) {
    String authorName = "Unknown";
    try {
      authorName = Databaseio.useridToUsername(comment.getAuthorId());
    } catch (SQLException error) {
      error.printStackTrace();
    }
    Label name = new Label(authorName);
    name.setId("name");
    Label date = new Label(comment.getDate());
    date.setId("date");
    date.setAlignment(Pos.CENTER);
    date.setPadding(new Insets(2, 5, 2, 5));
    Text commentText = new Text(comment.getContent());
    commentText.setId("comment-text");
    commentText.setFill(Color.GREEN);

    TextFlow commentArea = new TextFlow(commentText);
    commentArea.setId("comment-area");
    commentArea.setPadding(new Insets(5, 10, 5, 10));

    authorbox.setPadding(new Insets(5));
    authorbox.setSpacing(10);
    authorbox.setAlignment(Pos.CENTER);
    authorbox.getChildren().addAll(name, date);
    authorbox.setId("author-box");
    topBox.setLeft(authorbox);

    // Buttons for current user
    Button editBtn = new Button("Edit");
    Button deleteBtn = new Button("Delete");
    Label yourLabel = new Label("YOUR COMMENT");
    yourLabel.setId("your-label");

    // If the current user is the author of the comment
    if ((currentUser.getId()) == (comment.getAuthorId()) || currentUser.isAdmin()) {
      yourLabel.setId("your-label");
      buttonsBox.setAlignment(Pos.CENTER);
      buttonsBox.setSpacing(5);
      buttonsBox.getChildren().addAll(yourLabel, editBtn, deleteBtn);
      buttonsBox.setId("buttons-box");
      buttonsBox.setPadding(new Insets(2, 5, 2, 5));
      topBox.setRight(buttonsBox);
    }

    main.setPadding(new Insets(5));
    main.setSpacing(5);
    main.setAlignment(Pos.CENTER_LEFT);
    main.getChildren().addAll(topBox, commentArea);
    main.getStylesheets().addAll("file:styles/CommentField.css");
    main.setId("root");

    // Edit comment button
    editBtn.setOnAction(e -> {
      // Create the custom dialog.
      Dialog<String> dialog = new Dialog<>();
      dialog.setTitle("Edit the comment");
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
        Databaseio.editComment(comment, commentContent);
        ;
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Comment Edited");
        alert.setHeaderText(null);
        alert.setContentText("The comment is successfully edited.");
        alert.showAndWait();
      });
    });

    // Delete comment button
    deleteBtn.setOnAction(e -> {
      Alert alert = new Alert(AlertType.CONFIRMATION);
      alert.setTitle("Deleting Comment");
      alert.setHeaderText(null);
      alert.setContentText("Are you sure?");

      Optional<ButtonType> result = alert.showAndWait();
      if (result.get() == ButtonType.OK) {
        Databaseio.removeComment(comment);
      }
    });

  }

  public VBox getCommentField() {
    return main;
  }
}
