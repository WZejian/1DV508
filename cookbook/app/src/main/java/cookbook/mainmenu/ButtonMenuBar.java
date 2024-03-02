package cookbook.mainmenu;

import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ButtonMenuBar extends ToggleButton {
  
  private ImageView imgView;
  private ImageView imgViewHover;
  private Boolean isActive = false;

  /**
   * Class that defines menu buttons.
   * @param text - ?
   * @param img - ?
   * @param imgHover - ?
   */
  public ButtonMenuBar(String text, Image img, Image imgHover, ToggleGroup toggleGroup) {
    // Default icon
    imgView = new ImageView(img);
    imgView.setFitHeight(35);
    imgView.setFitWidth(35);
    
    // Hover icon
    imgViewHover = new ImageView(imgHover);
    imgViewHover.setFitHeight(35);
    imgViewHover.setFitWidth(35);
        
    // Button properties
    this.setText(text);
    this.setGraphic(imgView);
    this.setGraphicTextGap(10);
    this.setMinSize(270, 60);
    this.getStyleClass().add("button-menu-bar");
    this.setToggleGroup(toggleGroup);
    
    // Hover effect of the button
    // Changes colors
    this.hoverProperty().addListener(e -> {
      if (this.isHover()) {
        this.setGraphic(imgViewHover);
      } else if (!this.isActive) {
        this.setGraphic(imgView);
      }
    });
  }

  /**
   * This class defines the Buttons used in the Main Menu.
   * @param text - button text.
   * @param img - button image at rest.
   * @param imgHover - button image when mouse hovers over.
   */
  // Without togglegroup
  public ButtonMenuBar(String text, Image img, Image imgHover) {
    // Default icon
    ImageView imgView = new ImageView(img);
    imgView.setFitHeight(35);
    imgView.setFitWidth(35);
    
    // Hover icon
    ImageView imgViewHover = new ImageView(imgHover);
    imgViewHover.setFitHeight(35);
    imgViewHover.setFitWidth(35);
        
    // Button properties
    this.setText(text);
    this.setGraphic(imgView);
    this.setGraphicTextGap(10);
    this.setMinSize(270, 60);
    this.getStyleClass().add("button-menu-bar");
    
    // Hover effect of the button
    // Changes colors
    if (!isActive) {
      this.hoverProperty().addListener(e -> {
        if (this.isHover()) {
          // this.setGraphic(imgViewHover);
          this.setIcon(imgViewHover);
        } else {
          // this.setGraphic(imgView);
          this.setIcon(imgView);
        }
      });
    }
  }

  public void setIcon(ImageView imgView) {
    this.setGraphic(imgView);
  }

  /**
   * Sets active button.
   */
  public void setActiveButton() {
    this.selectedProperty().set(true);
    this.setGraphic(this.imgViewHover);
    this.isActive = true;
  }

  public void setInactiveButton() {
    this.setGraphic(this.imgView);
    this.isActive = false;
  }
}
