package cookbook.mainmenu.rightsidecontent.scenes.recipeview;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class TextAreaField extends HBox {
  
  /**
   * Constructor for Steps text fields
   * @param text
   */
  public TextAreaField(int stepCount, String text) {

    Label count = new Label(stepCount + "");
    count.setId("count");
    count.setMinSize(20, 20);
    count.setAlignment(Pos.CENTER);

    Text txt = new Text(text);
    txt.setId("text");
    txt.setFill(Color.GREEN);

    TextFlow textFlow = new TextFlow(txt);
    textFlow.setId("text-area");

    this.setSpacing(10);
    this.setAlignment(Pos.TOP_LEFT);
    this.getChildren().addAll(count, textFlow);
    this.setId("box");
    this.getStylesheets().addAll("file:styles/TextAreaField.css");
  }

  /**
   * Constructor for regular wrapped text fields
   * @param text
   */
  public TextAreaField(String text) {

    Text txt = new Text(text);
    txt.setId("text");
    txt.setFill(Color.GREEN);

    TextFlow textFlow = new TextFlow(txt);
    textFlow.setId("text-area");

    this.setSpacing(10);
    this.setAlignment(Pos.CENTER_LEFT);
    this.getChildren().addAll(textFlow);
    this.setId("box");
    this.getStylesheets().addAll("file:styles/TextAreaField.css");
  }
}
