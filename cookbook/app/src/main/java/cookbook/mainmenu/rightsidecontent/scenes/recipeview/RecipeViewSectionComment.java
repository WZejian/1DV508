package cookbook.mainmenu.rightsidecontent.scenes.recipeview;

import java.util.ArrayList;
import cookbook.Comment;
import cookbook.Databaseio;
import cookbook.User;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RecipeViewSectionComment {
  private VBox main = new VBox();
  private VBox contentBox = new VBox();

  /**
   * Default Section.
   *
   * @param title Title of view
   */
  public RecipeViewSectionComment(String title, User currentUser, int recipeId) {

    Label titleLabel = new Label(title);
    titleLabel.setId("title-label");
    Button refreshCom = new Button("refresh comment");

    refreshCom.setOnAction(e -> {
      ArrayList<Comment> commentList = Databaseio.fetchComments(recipeId);
      contentBox.getChildren().clear();
      for (Comment comment : commentList) {
        CommentField commentField = new CommentField(comment, currentUser);
        this.addToSectionVbox(commentField.getCommentField());
      }
    });

    contentBox.setSpacing(10);

    main.getChildren().addAll(titleLabel, refreshCom, contentBox);
    main.getStylesheets().add("file:styles/RecipeViewSceneComment.css");
  }

  public VBox getSection() {
    return main;
  }

  public void addToSectionVbox(VBox vbox) {
    contentBox.getChildren().add(vbox);
  }

  public void addToSectionHbox(HBox hbox) {
    contentBox.getChildren().add(hbox);
  }

  public void addToSectionLabel(Label label) {
    contentBox.getChildren().add(label);
  }

}
