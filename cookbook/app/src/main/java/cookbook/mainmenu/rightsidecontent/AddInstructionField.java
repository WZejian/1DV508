package cookbook.mainmenu.rightsidecontent;

import cookbook.mainmenu.rightsidecontent.scenes.AddingRecipeScene;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AddInstructionField extends HBox {
  
  public AddInstructionField(AddingRecipeScene addingRecipeScene, VBox instructionsBox, int instructionCount) {

    // Field width
    int width = 300;

    // Instruction number
    Label count = new Label(instructionCount + ".");

    // Instruction description
    TextField instruction = new TextField("");
    instruction.setPromptText("Instruction");
    instruction.setMinWidth(width);
    instruction.setMaxWidth(width);

    Button addBtn = new Button("ADD");

    this.setAlignment(Pos.CENTER);
    this.setSpacing(10);
    this.getChildren().addAll(count, instruction, addBtn);
    this.getStylesheets().add("file:styles/AddInstructionField.css");
    this.setId("root");
    this.setPadding(new Insets(5, 5, 5, 5));
    instructionsBox.getChildren().add(1, this);

    // Add instruction BUTTON
    addBtn.setOnAction(e -> {

      if (instruction.getText().length() > 2) {
        AddedField addedField = new AddedField(addingRecipeScene, instructionsBox, (instructionCount + "."), instruction.getText());
        addingRecipeScene.setInstructionAddingProgress(false);
        
        addingRecipeScene.addInstructionToArray(addedField);
        instructionsBox.getChildren().remove(1);
        instructionsBox.getChildren().add(addedField);

      } else {
        System.out.println("Empty instruction field!");
      }
      
    });
  }
}
