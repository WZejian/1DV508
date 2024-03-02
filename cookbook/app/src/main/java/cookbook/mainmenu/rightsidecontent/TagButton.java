package cookbook.mainmenu.rightsidecontent;

import cookbook.Images;
import cookbook.Tag;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;

public class TagButton extends ToggleButton {

  private Tag tag;

  /**
   * This is a creator class to make a toggle button for a Tag.
   * @param tag - tag object
   */
  public TagButton(Tag tag) {
    this.setTag(tag);
    super.setGraphic(this.getLabelImg());
    super.setText(tag.getName());
    this.getStyleClass().add("button-tag");
  }

  public Tag getTag() {
    return this.tag;
  }

  private void setTag(Tag tag) {
    this.tag = tag;
  }

  private ImageView getLabelImg() {
    ImageView lblView = new ImageView(Images.iconTagGreen);
    lblView.preserveRatioProperty().set(true);
    lblView.fitHeightProperty().set(10);

    return lblView;
  }

  
}
