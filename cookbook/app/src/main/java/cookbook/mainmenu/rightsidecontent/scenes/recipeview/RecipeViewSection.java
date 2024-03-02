package cookbook.mainmenu.rightsidecontent.scenes.recipeview;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RecipeViewSection {

  private VBox main = new VBox();
  private VBox contentBox = new VBox();

  /**
   * Default Section.
   *
   * @param title Title of view
   */
  public RecipeViewSection(String title, boolean commentSection) {

    if (!commentSection) {
      contentBox.setPadding(new Insets(10));
      contentBox.setId("content-box");
    }

    Label titleLabel = new Label(title);
    titleLabel.setId("title-label");

    contentBox.setSpacing(10);

    main.getChildren().addAll(titleLabel, contentBox);
    main.getStylesheets().add("file:styles/RecipeViewSection.css");
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
