package cookbook.mainmenu.rightsidecontent.scenes;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.util.Pair;
import javafx.geometry.Insets;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import cookbook.Databaseio;
import cookbook.Recipe;
import cookbook.User;
import cookbook.prompt.PromptDialogs;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;

public class AddWeekDinnerScene {

  /**
   * Asks yield.
   *
   * @param currentUser Current user
   * @param recipeId id of recipe
   * @param currentRecipe recipe working with
   */
  public void promptForYieldAndDate(User currentUser, int recipeId, Recipe currentRecipe) {

    int yieldDefault = currentRecipe.getYield();

    // Create the custom dialog.
    Dialog<Pair<Integer, LocalDate>> dialog = new Dialog<>();
    dialog.setTitle("Add this recipe to your dinner list");
    dialog.setHeaderText("Please provide the following information");

    // Set the button types.
    ButtonType addButtonType = new ButtonType("Add", ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

    // Create the yield and date labels and fields.
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));

    TextField yieldField = new TextField();
    yieldField.setPromptText("Enter the yield of this recipe");

    // input check, only numbers
    yieldField.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        if (!newValue.matches("\\d*")) {
          yieldField.setText(newValue.replaceAll("[^\\d]", ""));
        }
      }
    });

    DatePicker datePicker = new DatePicker();
    datePicker.setPromptText("Choose a date");
    datePicker.setPrefWidth(170);

    grid.add(new Label("Yield:"), 0, 0);
    grid.add(yieldField, 1, 0);
    grid.add(new Label("Date:"), 0, 1);
    grid.add(datePicker, 1, 1);

    // Enable/Disable add button depending on whether a date was entered.
    Node addButton = dialog.getDialogPane().lookupButton(addButtonType);
    addButton.setDisable(true);

    // Do some validation (using the Java 8 lambda syntax).
    datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
      addButton.setDisable(newValue.toString().trim().isEmpty());
    });

    dialog.getDialogPane().setContent(grid);

    // Request focus on the date field by default.
    Platform.runLater(() -> datePicker.requestFocus());
    // Convert the result to a yield-date-pair when the add button is clicked.
    dialog.setResultConverter(dialogButton -> {
      if (dialogButton == addButtonType) {
        if (yieldField.getText().equals("")) {
          return new Pair<>(yieldDefault, datePicker.getValue());
        } else {
          return new Pair<>(Integer.parseInt(yieldField.getText()), datePicker.getValue());
        }
      }
      return null;
    });
    Optional<Pair<Integer, LocalDate>> result = dialog.showAndWait();

    result.ifPresent(yieldDate -> {
      int inputYield = yieldDate.getKey();
      LocalDate inputDate = yieldDate.getValue();

      try {
        if (!Databaseio.dinnerExistCheck(currentUser, inputDate, currentRecipe.getId())) {
          currentUser.addWeeklyDinner(currentRecipe, inputDate, inputYield);
          try {
            PromptDialogs
                .promptForConfirmationAlert("The recipe has been added to the date you choose in your calender.");
          } catch (Exception e) {
            e.printStackTrace();
          }
        } else {
          try {
            PromptDialogs.promptForInformationAlert(
                "The recipe is already in your weekly dinner list.");
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      } catch (SQLException e) {
        try {
          PromptDialogs.promptForErrorAlert("Something is wrong. Please try to add again!");
          e.printStackTrace();
        } catch (Exception e1) {
          e1.printStackTrace();
        }
      }
    });
  }
}
