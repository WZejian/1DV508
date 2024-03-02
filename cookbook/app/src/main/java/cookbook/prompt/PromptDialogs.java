package cookbook.prompt;

import javafx.scene.control.*;

import javafx.scene.control.Alert.AlertType;

// https://www.geeksforgeeks.org/javafx-alert-with-examples/
public class PromptDialogs {

  /**
   * A method for prompt of confirmation alert.
   */
  public static void promptForConfirmationAlert(String dialogText) throws Exception {
    // create a alert
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setHeaderText(null);
    alert.setContentText(dialogText);
    alert.showAndWait();
  }
  

  /**
   * A method for prompt of error alert.
   */
  public static void promptForErrorAlert(String dialogText) throws Exception {
    // create a alert
    Alert alert = new Alert(AlertType.ERROR);
    alert.setHeaderText(null);
    alert.setContentText(dialogText);
    alert.showAndWait();
  }

  /**
   * A method for prompt of information alert.
   */
  public static void promptForInformationAlert(String dialogText) throws Exception {
    // create a alert
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setHeaderText(null);
    alert.setContentText(dialogText);
    alert.showAndWait();
  }

  /**
   * A method for prompt of warning alert.
   */
  public static void promptForWarningAlert(String dialogText) throws Exception {
    // create a alert
    Alert alert = new Alert(AlertType.WARNING);
    alert.setHeaderText(null);
    alert.setContentText(dialogText);
    alert.showAndWait();
  }
}
