package cookbook.mainmenu.rightsidecontent.scenes;

import cookbook.Databaseio;
import java.util.ArrayList;
import java.util.Optional;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

public class AdminVbox extends VBox {
  /**
   * Return a admin function vbox.
   * @return
   */
  public static VBox getAdminVbox() {
    final VBox layout = new VBox();
    layout.getStylesheets().add("file:styles/HomeScene.css");
    final Label profileSet = new Label("ADMIN FUNCTION");
    ImageView ps = new ImageView(new Image("file:img/Icons/setting_icon.png"));
    ps.setFitWidth(20);
    ps.setFitHeight(20);

    HBox hb = new HBox();
    hb.setAlignment(Pos.CENTER_LEFT);
    hb.setSpacing(8);
    hb.getChildren().addAll(ps, profileSet);
    Button newUserBtn = new Button("Create a new user");
    Button changeInfoBtn = new Button("Edit user's information");
    Button changeToAdminBtn = new Button("Change user's privilege");
    final Button delUserBtn = new Button("Delete an user");
    newUserBtn.setId("confirm-button");
    changeInfoBtn.setId("confirm-button");
    changeToAdminBtn.setId("confirm-button");
    delUserBtn.setId("confirm-button");
    newUserBtn.setMinWidth(230);
    changeInfoBtn.setMinWidth(230);
    changeToAdminBtn.setMinWidth(230);
    delUserBtn.setMinWidth(230);

    // https://code.makery.ch/blog/javafx-dialogs-official/
    newUserBtn.setOnAction(e -> {
      Dialog<ArrayList<String>> dialog = new Dialog<>();
      dialog.setTitle("Create a new user");
      dialog.setHeaderText("Please enter the following information");
      dialog.setGraphic(null);

      // Set the button types.
      ButtonType createButtonType = new ButtonType("Create", ButtonData.OK_DONE);
      dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

      // Create the username and password labels and fields.
      GridPane grid = new GridPane();
      grid.setHgap(10);
      grid.setVgap(10);
      grid.setPadding(new Insets(20, 20, 10, 10));

      TextField username = new TextField();
      username.setPromptText("Username");
      TextField nickname = new TextField();
      nickname.setPromptText("Nickname");
      PasswordField password = new PasswordField();
      password.setPromptText("Password");

      grid.add(new Label("Username:"), 0, 0);
      grid.add(username, 1, 0);
      grid.add(new Label("Password:"), 0, 1);
      grid.add(password, 1, 1);
      grid.add(new Label("Nickname:"), 0, 2);
      grid.add(nickname, 1, 2);

      // Enable/Disable login button depending on whether a username was entered.
      Node createButton = dialog.getDialogPane().lookupButton(createButtonType);
      createButton.setDisable(true);

      // Do some validation (using the Java 8 lambda syntax).
      username.textProperty().addListener((observable, oldValue, newValue) -> {
        createButton.setDisable(newValue.trim().isEmpty());
      });

      password.textProperty().addListener((observable, oldValue, newValue) -> {
        createButton.setDisable(newValue.trim().isEmpty());
      });

      nickname.textProperty().addListener((observable, oldValue, newValue) -> {
        createButton.setDisable(newValue.trim().isEmpty());
      });

      dialog.getDialogPane().setContent(grid);

      // Request focus on the username field by default.
      Platform.runLater(() -> username.requestFocus());

      // Convert the result to a username-password-pair when the login button is
      // clicked.
      dialog.setResultConverter(dialogButton -> {
        if (dialogButton == createButtonType) {
          ArrayList<String> info = new ArrayList<>();
          info.add(username.getText());
          info.add(password.getText());
          info.add(nickname.getText());
          return info;
        }
        return null;
      });

      Optional<ArrayList<String>> result = dialog.showAndWait();

      result.ifPresent(usernamePassword -> {
        String userName = usernamePassword.get(0);
        String userPass = usernamePassword.get(1);
        String nickName = usernamePassword.get(2);
        boolean userNameExist = Databaseio.usernameExist(userName);
        if (userNameExist) {
          Alert existAlert = new Alert(AlertType.ERROR);
          existAlert.setTitle("Error");
          existAlert.setHeaderText(null);
          existAlert.setContentText("The username already exists!\nUsername must be unique!");
          existAlert.showAndWait();
        } else {
          Databaseio.createNewUser(userName, userPass, nickName);
          Alert createSucc = new Alert(AlertType.INFORMATION);
          createSucc.setTitle("User created");
          createSucc.setHeaderText(null);
          createSucc.setContentText("The user is successfully created.");
          createSucc.showAndWait();
        }
      });
    });

    changeInfoBtn.setOnAction(e -> {
      TextInputDialog dialogUsername = new TextInputDialog();
      dialogUsername.setTitle("Edit User's information");
      dialogUsername.setHeaderText(null);
      dialogUsername.setContentText("Please enter the username:");
      Optional<String> resultUsername = dialogUsername.showAndWait();
      resultUsername.ifPresent(username -> {
        Boolean userExist = Databaseio.usernameExist(username);
        if (userExist) {
          // Create the custom dialog.
          Dialog<Pair<String, String>> dialogEdit = new Dialog<>();
          dialogEdit.setTitle("Edit " + username + "\'s Information");
          dialogEdit.setHeaderText(null);

          // Set the icon (must be included in the project).
          dialogEdit.setGraphic(null);

          // Set the button types.
          ButtonType confirmButtonType = new ButtonType("Confirm", ButtonData.OK_DONE);
          dialogEdit.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);

          GridPane grid = new GridPane();
          grid.setHgap(10);
          grid.setVgap(10);
          grid.setPadding(new Insets(20, 20, 10, 10));

          ChoiceBox<String> choices = new ChoiceBox<>();
          choices.getItems().addAll("Username", "Password", "Nickname");
          choices.getSelectionModel().selectFirst();
          choices.setPrefWidth(200);
          TextField newContent = new TextField();
          newContent.setPrefWidth(200);
          newContent.setPrefHeight(50);

          grid.add(new Label("You want to edit:"), 0, 0);
          grid.add(choices, 1, 0);
          grid.add(new Label("Change it to:"), 0, 1);
          grid.add(newContent, 1, 1);

          // Enable/Disable login button depending on whether a username was entered.
          Node confirButton = dialogEdit.getDialogPane().lookupButton(confirmButtonType);
          confirButton.setDisable(true);

          // Do some validation (using the Java 8 lambda syntax).
          newContent.textProperty().addListener((observable, oldValue, newValue) -> {
            confirButton.setDisable(newValue.trim().isEmpty());
          });

          dialogEdit.getDialogPane().setContent(grid);

          Platform.runLater(() -> newContent.requestFocus());

          dialogEdit.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButtonType) {
              return new Pair<>(choices.getValue(), newContent.getText());
            }
            return null;
          });

          Optional<Pair<String, String>> resultEdit = dialogEdit.showAndWait();
          resultEdit.ifPresent(editInfo -> {
            int userId = Databaseio.usernameToid(username);
            String editType = editInfo.getKey();
            String editTo = editInfo.getValue();
            switch (editType) {
              case ("Username"):
                Boolean alreadyExist = Databaseio.usernameExist(editTo);
                if (alreadyExist) {
                  Alert existAlert = new Alert(AlertType.ERROR);
                  existAlert.setTitle("Error");
                  existAlert.setHeaderText(null);
                  existAlert.setContentText(
                      "The username already exists!\nUsername must be unique!");
                  existAlert.showAndWait();
                } else {
                  Databaseio.usernameUpdate(userId, editTo);
                  Alert usernameSucc = new Alert(AlertType.INFORMATION);
                  usernameSucc.setTitle("Information Changed");
                  usernameSucc.setHeaderText(null);
                  usernameSucc.setContentText(
                      "The username is successfully changed to \"" + editTo + "\".");
                  usernameSucc.showAndWait();
                }
                break;
              case ("Password"):
                Databaseio.passwordUpdate(userId, editTo);
                Alert passSucc = new Alert(AlertType.INFORMATION);
                passSucc.setTitle("Information Changed");
                passSucc.setHeaderText(null);
                passSucc.setContentText(
                    "The password is successfully changed to \"" + editTo + "\".");
                passSucc.showAndWait();
                break;
              case ("Nickname"):
                Databaseio.nicknameUpdate(userId, editTo);
                Alert nickSucc = new Alert(AlertType.INFORMATION);
                nickSucc.setTitle("Information Changed");
                nickSucc.setHeaderText(null);
                nickSucc.setContentText(
                    "The nickname is successfully changed to \"" + editTo + "\".");
                nickSucc.showAndWait();
                break;
              default:
                Alert whatchoice = new Alert(AlertType.ERROR);
                whatchoice.setTitle("Error");
                whatchoice.setHeaderText(null);
                whatchoice.setContentText("Something got wrong, try again.");
                whatchoice.showAndWait();
                break;
            }
          });
        } else {
          Alert noUser = new Alert(AlertType.ERROR);
          noUser.setTitle("Error");
          noUser.setHeaderText(null);
          noUser.setContentText("The user does not exist!");
          noUser.showAndWait();
        }
      });
    });

    changeToAdminBtn.setOnAction(e -> {
      TextInputDialog dialogUsername = new TextInputDialog();
      dialogUsername.setTitle("Change User's Privilege");
      dialogUsername.setHeaderText(null);
      dialogUsername.setContentText("Please enter the username:");
      Optional<String> result = dialogUsername.showAndWait();
      result.ifPresent(userName -> {
        Boolean userExist = Databaseio.usernameExist(userName);
        if (userExist) {
          int adminStatus = Databaseio.usernameToAdminStatus(userName);
          if (adminStatus == 0) {   // if now normal user
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText(
                "The user is normal user now, are you sure to change it to admin?");
            Optional<ButtonType> resultConfirm = alert.showAndWait();
            if (resultConfirm.get() == ButtonType.OK) {
              Databaseio.adminUpdate(userName, 1);
            }
          } else if (adminStatus == 1) {  // if now admin
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText(
                "The user is admin now, are you sure to change it to normal user?");
            Optional<ButtonType> resultConfirm = alert.showAndWait();
            if (resultConfirm.get() == ButtonType.OK) {
              Databaseio.adminUpdate(userName, 0);
            }
          }
        } else {    // user not exist
          Alert noUser = new Alert(AlertType.ERROR);
          noUser.setTitle("Error");
          noUser.setHeaderText(null);
          noUser.setContentText("The user does not exist!");
          noUser.showAndWait();
        }
      });
    });

    delUserBtn.setOnAction(e -> {
      TextInputDialog dialogUsername = new TextInputDialog();
      dialogUsername.setTitle("Delete an user");
      dialogUsername.setHeaderText(null);
      dialogUsername.setContentText("Please enter the username:");
      Optional<String> result = dialogUsername.showAndWait();
      result.ifPresent(userName -> {
        Boolean userExist = Databaseio.usernameExist(userName);
        if (userExist) {
          Databaseio.deleteUser(userName);
        } else {    // user not exist
          Alert noUser = new Alert(AlertType.ERROR);
          noUser.setTitle("Error");
          noUser.setHeaderText(null);
          noUser.setContentText("The user does not exist!");
          noUser.showAndWait();
        }
      });
    });

    layout.setPadding(new Insets(0));
    layout.setSpacing(10);
    layout.setAlignment(Pos.BOTTOM_LEFT);
    layout.getChildren().addAll(hb, newUserBtn, changeInfoBtn, changeToAdminBtn, delUserBtn);
    return layout;
  }
}
