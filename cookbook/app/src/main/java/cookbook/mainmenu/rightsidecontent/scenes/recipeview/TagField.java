package cookbook.mainmenu.rightsidecontent.scenes.recipeview;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TagField extends Label {
  
  /**
   * Tag field.
   *
   * @param name Name of tag
   * @param img image
   */
  public TagField(String name, Image img) {

    ImageView imgView = new ImageView(img);
    imgView.setFitHeight(20);
    imgView.setFitWidth(20);

    this.setAlignment(Pos.CENTER);
    this.setText(name);
    this.setGraphic(imgView);
    this.getStylesheets().add("file:styles/TagField.css");
  }
}
