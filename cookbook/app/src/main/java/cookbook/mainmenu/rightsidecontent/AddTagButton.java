package cookbook.mainmenu.rightsidecontent;

import cookbook.Images;
import cookbook.Tag;
import cookbook.mainmenu.rightsidecontent.scenes.AddingRecipeScene;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class AddTagButton extends HBox {
  
  private ToggleButton addTag = new ToggleButton();
  
  /**
   * Creator class for a Add Tag button.
   * This button expands and has a Text Field
   * and an extra button to commit changes.
   */
  public AddTagButton(AddingRecipeScene recScene) {
    // Set style
    this.getStylesheets().addAll("file:styles/AddTagButton.css");

    this.getStyleClass().add("button-tag");
    this.setAlignment(Pos.CENTER_LEFT);
    this.setSpacing(5);

    // Add icon
    ImageView lblView = new ImageView(Images.iconPlusDarkGreen);
    lblView.preserveRatioProperty().set(true);
    lblView.fitHeightProperty().set(15);

    // Set style of the button
    addTag.getStyleClass().add("button-tag");
    addTag.setGraphic(lblView);

    // Add the adding button
    this.getChildren().add(this.addTag);

    // Adding Tag button behaviour
    this.addTag.setOnAction(e -> {
      if (this.addTag.isSelected()) {
        this.addContent(recScene);
      } else {
        this.removeContent();
      }
    });

  }

  /**
   * Adds the expansion fields to the button.
   */
  public void addContent(AddingRecipeScene recScene) {

    // Change style to expand box
    this.resize(100, 40);
    this.setStyle("-fx-background-color: #EBB643;");

    // Input field to add tag name
    TextField textField = new TextField();
    textField.setMinWidth(35);
    textField.setMaxHeight(20);
    textField.setPromptText("Enter tag name");

    Button addButton = new Button();
    addButton.getStyleClass().add("button-tag");
    addButton.setText("Add");
    
    this.getChildren().addAll(textField, addButton);
    this.setFillHeight(true);

    addButton.setOnAction(e -> {
      if (textField != null) {
        if (textField.getText().length() > 0) {
          recScene.addTagToSelected(
              new Tag(-1, textField.getText())
          );

          // Deactivate the vutton
          this.removeContent();
          this.addTag.selectedProperty().set(false);
        }
      }
    });
  }

  /**
   * Contracts the button to the original state.
   */
  public void removeContent() {
    this.setStyle("-fx-background-color: #3e7e2f;");
    this.getChildren().clear();
    this.getChildren().add(this.addTag);
  }
  
}
