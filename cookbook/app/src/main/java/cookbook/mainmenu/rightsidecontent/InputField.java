package cookbook.mainmenu.rightsidecontent;


import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class InputField extends VBox {
  // text from user input that can be used in other classes
  private String inText = "";
  private TextField textField;
  private TextArea textArea;

  public InputField(String labelName, int width, Boolean multiLineText) {

    // Name of input field
    Label nameLabel = new Label(labelName);
    nameLabel.setId("name-label");
    this.getChildren().addAll(nameLabel);

    // Single line text field
    if (!multiLineText) {
      textField = new TextField();
      textField.setId("input-field");
      this.getChildren().add(textField);
    
    // Multi line text area
    } else {
      textArea = new TextArea();
      textArea.setId("text-area-field");
      textArea.setWrapText(true);
      textArea.setPrefHeight(100);
      this.getChildren().add(textArea);
    }

    this.setMaxWidth(width);
    this.getStylesheets().add("file:styles/InputField.css");
  }

  public String getText() {
    //Ony one can be true depending if multiline is true
    if (this.textArea == null) {
      inText = textField.getText();
    } else if (this.textField == null) {
      inText = textArea.getText();
    }
    
    return this.inText;
  }
}
