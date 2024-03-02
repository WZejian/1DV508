package cookbook.mainmenu.rightsidecontent.scenes.recipeview;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SideButton extends Button {
  
  /**
   * A button.
   * @param btnLabel Label of the button
   * @param icon Icon
   */
  public SideButton(String btnLabel, Image icon) {

    // Image
    ImageView imgView = new ImageView(icon);
    imgView.setFitHeight(20);
    imgView.setFitWidth(20);

    this.setAlignment(Pos.CENTER);
    this.setGraphic(imgView);
    this.setText(btnLabel);
    this.setId("button");
    this.getStylesheets().add("file:styles/SideButton.css");
  }

  /**
   * meichen, used in star button, reset button image.
   * @param icon new image
   */
  public void setImage(Image icon) {
    ImageView imgView = new ImageView(icon);
    imgView.setFitHeight(20);
    imgView.setFitWidth(20);
    this.setGraphic(imgView);
  }

}
