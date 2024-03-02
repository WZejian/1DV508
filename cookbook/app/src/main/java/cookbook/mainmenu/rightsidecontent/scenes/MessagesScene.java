package cookbook.mainmenu.rightsidecontent.scenes;

import java.util.ArrayList;

import cookbook.Databaseio;
import cookbook.Images;
import cookbook.Message;
import cookbook.User;
import cookbook.mainmenu.MainMenu;
import cookbook.mainmenu.rightsidecontent.MessageButton;
import cookbook.mainmenu.rightsidecontent.MessageCard;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class MessagesScene extends VBox {

  private ArrayList<MessageCard> allMessageCards = new ArrayList<>();
  private ArrayList<Message> allMessages = new ArrayList<>();
  
  /**
   * This is a screen to display all received messages.
   * @param allMessages - all messages received by a user
   * @return
   */
  public MessagesScene(MainMenu mainMenu, User currentUser, VBox rightSide) {
    //Fetch messages from DB
    this.allMessages = Databaseio.getAllMessages(currentUser);

    this.setAlignment(Pos.CENTER);

    if (allMessages.size() > 0) {
      for (Message m : this.allMessages) {
        MessageCard mc = new MessageCard(m, this, currentUser, mainMenu);
        allMessageCards.add(mc);
        this.getChildren().add(mc);
      }
    mainMenu.setMessageButton(new MessageButton(allMessages.size()));

    } else {
      // Image for the label
      ImageView sad = new ImageView(Images.sadSmileyGreen);
      sad.setFitHeight(15);
      sad.preserveRatioProperty().set(true);

      // Label with text
      Label noMess = new Label("No new messages", sad);
      noMess.setAlignment(Pos.CENTER);
      noMess.setMaxWidth(200);
      noMess.setPadding(new Insets(10, 5, 10, 5));
      noMess.setStyle("""
        -fx-background-color: rgba(62, 126, 47, .5);
        -fx-background-radius: 10px;
        -fx-text-fill: #3e7e2f;
        -fx-font-size: 16px; 
        -fx-font-weight: bolder;
        """
      );
      this.getChildren().add(noMess);
    }
  }

  public void removeCard(MessageCard mc) {
    this.allMessageCards.remove(mc);
    this.getChildren().clear();
    this.getChildren().addAll(allMessageCards);
  }

  public int getMessageSize() {
    return this.allMessageCards.size();
  }
}
