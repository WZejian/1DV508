package cookbook.mainmenu.rightsidecontent;

import cookbook.Images;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class MessageButton extends Button {

  public MessageButton() {
    
  }

  /**
   * A message button class that changes if there are new messages.
   * @param messLstSize - size of the messages array.
   */
  public MessageButton(int messLstSize) {
    this.setMinSize(50, 46);
    this.getStylesheets().addAll("file:styles/MainMenu.css");

    ImageView messageIcon = new ImageView();
    messageIcon.preserveRatioProperty().set(true);
    messageIcon. setFitHeight(25);

    // Set the graphic accordingly
    if (messLstSize == 0) {
      messageIcon.setImage(Images.iconMessageGreen);
      this.setId("message-button");
      this.setGraphic(messageIcon);
      this.hoverProperty().addListener(e -> {
        if (this.isHover()) {
          messageIcon.setImage(Images.iconMessageYellow);
          this.setGraphic(messageIcon);
        } else {
          messageIcon.setImage(Images.iconMessageGreen);
          this.setGraphic(messageIcon);
        }
        
      });
    } else {
      messageIcon.setImage(Images.iconMessageRed);
      this.setId("message-button-alert");
      this.setGraphic(messageIcon);
    }
  }
  
}
