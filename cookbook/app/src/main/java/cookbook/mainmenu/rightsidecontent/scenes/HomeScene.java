package cookbook.mainmenu.rightsidecontent.scenes;


import java.security.NoSuchAlgorithmException;

import cookbook.Databaseio;
import cookbook.DbConnector;
import cookbook.PasswordEncode;
import cookbook.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HomeScene {
  
  /**
   * @return - returns layout VBox.
   */
  public VBox getScene(User oldcurrentUser) {
    // refresh
    User currentUser = Databaseio.useridToUserObj(oldcurrentUser.getId());
    // Main layout.
    VBox layout = new VBox();
    layout.setPadding(new Insets(6));
    layout.setSpacing(45);
    layout.setAlignment(Pos.BOTTOM_LEFT);

    layout.getStylesheets().add("file:styles/HomeScene.css");

    //Block for PROFILE.
    Label prof = new Label("PROFILE"); 
   

    ImageView iv = new ImageView(new Image("file:img/Icons/user_icon.png"));
    iv.setFitWidth(20);
    iv.setFitHeight(20);

    HBox h = new HBox();
    h.setAlignment(Pos.CENTER_LEFT);
    h.setSpacing(8);
    h.getChildren().addAll(iv, prof);

    // User label
    Label username = new Label("Username: " + currentUser.getName());
    username.setId("my-buttons-background");
    username.setPadding(new Insets(10, 5, 10, 5));

    Label nickname = new Label("Nickname: " + currentUser.getLoginName());
    nickname.setId("my-buttons-background");
    nickname.setPadding(new Insets(10, 5, 10, 5));
     

    VBox profile = new VBox();
    profile.setPadding(new Insets(0));
    profile.setSpacing(10);
    profile.setAlignment(Pos.BOTTOM_LEFT);
    profile.getChildren().addAll(h, username, nickname);

    //Block for EDIT PROFILE
    Label editProf = new Label("EDIT PROFILE"); 
   

    ImageView ep = new ImageView(new Image("file:img/Icons/edit_icon.png"));
    ep.setFitWidth(20);
    ep.setFitHeight(20);

    HBox hbo = new HBox();
    hbo.setAlignment(Pos.CENTER_LEFT);
    hbo.setSpacing(8);
    hbo.getChildren().addAll(ep, editProf);
    Button changeUsernameBtn = new Button("Change nickname");
    changeUsernameBtn.setId("confirm-button");
    changeUsernameBtn.setMinWidth(230);
    

    // Change username button behaviour
    changeUsernameBtn.setOnAction(e -> {
      Stage stage = new Stage();
      stage.initModality(Modality.APPLICATION_MODAL);
      Label changeUsername = new Label("Change Nickname");
    
      Label changePrompt = new Label("Please enter your new nickname");
      TextField addNewName = new TextField();
      addNewName.setPromptText("new nickname");
      addNewName.setMaxWidth(150);
      Button update = new Button("UPDATE");
      update.setOnAction(v -> {
        try {
          DbConnector.runUpdateQuery(
          """
          UPDATE users 
          SET login_name = ?
          WHERE id = ?;
          """,
          addNewName.getText(),
          currentUser.getId()
          );
          stage.close();
        } catch (Exception f) {
        f.printStackTrace();
        Alert addUserError = new Alert(AlertType.ERROR, "Can not change nickname");
        addUserError.showAndWait();
      }      
      });

      Button cancel = new Button("CANCEL");
      cancel.setOnAction(event -> {
        stage.close();
      });

      VBox changeUsernameWindow = new VBox();
      changeUsernameWindow.setPadding(new Insets(6));
      changeUsernameWindow.setSpacing(15);
      changeUsernameWindow.setAlignment(Pos.CENTER);
      changeUsernameWindow.getChildren().addAll(changeUsername, changePrompt, addNewName, update, cancel);
      changeUsernameWindow.setStyle("-fx-background-color: #ffffe0;");
      Scene scene = new Scene(changeUsernameWindow, 400, 300);    
      stage.setScene(scene);
      Image icon = new Image("file:img/favicon.png");
      stage.getIcons().add(icon);
      stage.show();
    }     
    );


    Button changePasswordBtn = new Button("Change password");
    changePasswordBtn.setId("confirm-button");
    changePasswordBtn.setMinWidth(230);
    
    // Change password behaviour button
    changePasswordBtn.setOnAction(e -> {
      Stage stage = new Stage();
      stage.initModality(Modality.APPLICATION_MODAL);
      Label changePassword = new Label("Change Password");
      Label changePrompt = new Label("Please enter your current password and new password");
      PasswordField currentPassword = new PasswordField();
      currentPassword.setPromptText("current password");
      currentPassword.setMaxWidth(150);
      PasswordField newPassword = new PasswordField();
      newPassword.setPromptText("new password");
      newPassword.setMaxWidth(150);
      PasswordField comfirmPassword = new PasswordField();
      comfirmPassword.setPromptText("confirm password");
      comfirmPassword.setMaxWidth(150);
      Button update = new Button("UPDATE");
      Button cancel = new Button("CANCEL");
      // change password
      update.setOnAction(ev -> {
        String passHashCur = "";
        try {
          passHashCur = PasswordEncode.passwordEncode(currentPassword.getText());
        } catch (NoSuchAlgorithmException e1) {
          Alert noHash = new Alert(AlertType.ERROR, "Something went wrong. Aborting.");
          noHash.showAndWait();
          e1.printStackTrace();
        }
        if (passHashCur.equals(currentUser.getHashedPassword())) {
          if (newPassword.getText().equals(comfirmPassword.getText())) {
            try {
              String passHashNew = PasswordEncode.passwordEncode(newPassword.getText());
              DbConnector.runUpdateQuery(
                """
                UPDATE users 
                SET pass_hash = ?
                WHERE id = ?;
                """,
                passHashNew,
                currentUser.getId()
              );
              Alert changePassSuc = new Alert(AlertType.INFORMATION, "Password changed successfully.");
              changePassSuc.showAndWait();
              stage.close();
            } catch (Exception p) {
              p.printStackTrace();
              Alert dbProbAlert = new Alert(AlertType.ERROR, "Cannot change password. Aborting.");
              dbProbAlert.showAndWait();
            }
            
          } else {
            Alert newPasswordNotMatch = new Alert(AlertType.WARNING, "New password does not match");
            newPasswordNotMatch.showAndWait();
          };
        } else {
          Alert passwordNotCorrect = new Alert(AlertType.WARNING, "Current Password is not correct.");
          passwordNotCorrect.showAndWait();
        }
      });
      cancel.setOnAction(event -> {
        stage.close();
      });

      VBox changePasswordWindow = new VBox();
      changePasswordWindow.setPadding(new Insets(6));
      changePasswordWindow.setSpacing(15);
      changePasswordWindow.setAlignment(Pos.CENTER);
      changePasswordWindow.getChildren().addAll(changePassword, changePrompt, currentPassword, newPassword, comfirmPassword, update, cancel);
      changePasswordWindow.setStyle("-fx-background-color: #ffffe0;");
      Scene scene = new Scene(changePasswordWindow, 400, 300);
      
      stage.setScene(scene);
      Image icon = new Image("file:img/favicon.png");
      stage.getIcons().add(icon);
      stage.show();
    }     
    );

    VBox editProfile = new VBox();
    editProfile.setPadding(new Insets(0));
    editProfile.setSpacing(10);
    editProfile.setAlignment(Pos.BOTTOM_LEFT);
    editProfile.getChildren().addAll(hbo, changeUsernameBtn, changePasswordBtn);


    //Block for PROFILE SETTING
    Label profileSet = new Label("PROFILE SETTING"); 
    ImageView ps = new ImageView(new Image("file:img/Icons/setting_icon.png"));
    ps.setFitWidth(20);
    ps.setFitHeight(20);

    HBox hb = new HBox();
    hb.setAlignment(Pos.CENTER_LEFT);
    hb.setSpacing(8);
    hb.getChildren().addAll(ps, profileSet);

    // Delete account button
    Button deleteAccountBtn = new Button("Delete account");
    deleteAccountBtn.setId("confirm-button");
    deleteAccountBtn.setMinWidth(230);  
    
    // Delete button action
    deleteAccountBtn.setOnAction(e -> {
      Stage stage = new Stage();
      stage.initModality(Modality.APPLICATION_MODAL);
      Label deleteAccount = new Label("Delete Account");
      
      // Get password and validate.
      Label prompt = new Label("Enter your password to delete your account.");
      PasswordField currentPassword = new PasswordField();
      currentPassword.setPromptText("current password");
      currentPassword.setMaxWidth(150);
      Button deleteBtn = new Button("DELETE");
      
      // Ask for confirmation again.
      deleteBtn.setOnAction(eve -> {
        String hashPass = "";
        try {
          hashPass = PasswordEncode.passwordEncode(currentPassword.getText());
        } catch (NoSuchAlgorithmException e1) {
          Alert noHash = new Alert(AlertType.ERROR, "Something went wrong. Aborting...");
          noHash.showAndWait();
          e1.printStackTrace();
        }
        if (hashPass.equals(currentUser.getHashedPassword())) {
          // Make sure user confirms delete.
          Alert confirmDelAlert = new Alert(
            AlertType.CONFIRMATION,
            "Are you sure you want to delete your account?"
            );
          confirmDelAlert.showAndWait().ifPresent(del -> {
            if (del == ButtonType.OK) {
              try {
                DbConnector.runUpdateQuery(
                  """
                  DELETE FROM users WHERE id = ?;
                  """,
                  currentUser.getId()
                );
                Alert deleteSuc = new Alert(AlertType.INFORMATION, "Account deleted successfully");
                deleteSuc.showAndWait();
                stage.close();
              } catch (Exception p) {
                p.printStackTrace();
                Alert noDelete = new Alert(AlertType.ERROR, "Cannot delete account.");
                noDelete.showAndWait();
              }
            }
        });
        } else {
          Alert wrongPass = new Alert(AlertType.ERROR, "Wrong password! Try again.");
          wrongPass.showAndWait();
        }
      });

      Button cancelBtn = new Button("CANCEL");
      cancelBtn.setOnAction(event -> {
        stage.close();
      });
      VBox deleteAccountWindow = new VBox();
      deleteAccountWindow.setPadding(new Insets(6));
      deleteAccountWindow.setSpacing(15);
      deleteAccountWindow.setAlignment(Pos.CENTER);
      deleteAccountWindow.getChildren().addAll(deleteAccount, prompt, currentPassword, deleteBtn, cancelBtn);
      deleteAccountWindow.setStyle("-fx-background-color: #ffffe0;");
      Scene scene = new Scene(deleteAccountWindow, 400, 300);    
      stage.setScene(scene);
      Image icon = new Image("file:img/favicon.png");
      stage.getIcons().add(icon);
      stage.show();
    }     
    );

    VBox profileSetting = new VBox();
    profileSetting.setPadding(new Insets(0));
    profileSetting.setSpacing(10);
    profileSetting.setAlignment(Pos.BOTTOM_LEFT);
    profileSetting.getChildren().addAll(hb, deleteAccountBtn);

    if (currentUser.isAdmin()) {
      VBox adminPart = AdminVbox.getAdminVbox();
      layout.getChildren().addAll(profile, editProfile, profileSetting, adminPart);
    } else {
      layout.getChildren().addAll(profile, editProfile, profileSetting);
    }
        

    return layout;
  }
}
