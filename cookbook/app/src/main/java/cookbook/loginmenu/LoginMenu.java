package cookbook.loginmenu;

import cookbook.DbConnector;
import cookbook.User;
import cookbook.UserAuth;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginMenu {
  
  private Scene scene;
  private Stage stage;
  private User currentUser = null;

  /**
   * Login window object.
   *
   * @throws Exception - JavaFX exceptions
   */
  public LoginMenu() throws Exception {

    // promptDialog p = new promptDialog();
    // Authentication message
    Label auth = new Label();
    auth.setPadding(new Insets(-5, 0, 0, 0));
    auth.setFont(Font.font("Fira Sans", FontWeight.BOLD, 20));
    
    // App logo
    Image logo = new Image("file:img/LoginMenu/login_logo.png");
    ImageView logoView = new ImageView(logo);
    logoView.setFitHeight(180);
    logoView.setFitWidth(180);
    
    // Username label + username field
    Label username = new Label("Username");
    username.setStyle("-fx-text-fill: #845b46");
    username.setFont(Font.font("Fira Sans", FontWeight.BOLD, 13));
    TextField inputName = new TextField();
    inputName.setStyle("-fx-text-fill: #845b46");
    inputName.setFont(Font.font("Fira Sans", FontWeight.BOLD, 13));
    VBox name = new VBox();
    name.getChildren().addAll(username, inputName);
    name.setMaxSize(200, 50);
    
    // Password label + password field
    Label password = new Label("Password");
    password.setStyle("-fx-text-fill: #845b46");
    password.setFont(Font.font("Fira Sans", FontWeight.BOLD, 13));
    PasswordField inputPassword = new PasswordField();
    inputPassword.setStyle("-fx-text-fill: #845b46");
    inputPassword.setFont(Font.font("Fira Sans", FontWeight.BOLD, 13));
    VBox pass = new VBox();
    pass.getChildren().addAll(password, inputPassword);
    pass.setMaxSize(200, 50);
    
    // THIS WILL BE DELETED LATER
    // Prints out the result at the very bottom of the login screen
    // displaying the input username and password
    Label result1 = new Label();
    Label result2 = new Label();
    VBox print = new VBox();
    print.getChildren().addAll(result1, result2);
    print.setAlignment(Pos.CENTER);
    print.setPadding(new Insets(-10, 0, 0, 0));
    
    // LOGIN button
    Button btnLogin = new Button();
    btnLogin.setText("Login");
    btnLogin.setMinWidth(95);
    btnLogin.setStyle("-fx-background-color: #845b46; -fx-text-fill: #f7ca6b");
    btnLogin.setFont(Font.font("Fira Sans", FontWeight.BOLD, 13));
    
    // Login button action on press
    btnLogin.setOnAction(e -> {
      
      // Checks that both username and password fields are not empty
      if (inputName.getLength() > 0 && inputPassword.getLength() > 0) {
        
        
        // THIS WILL BE DELETED, JUST FOR TEST PURPOSES
        result1.setText("Your name is: " + inputName.getText());
        
        // Check if user exists in DB using given credentials
        // Output depending on whether connection was successful.
        this.currentUser = UserAuth.authenticate(inputName.getText(), inputPassword.getText());
        if (UserAuth.getStatus()) {

          auth.setText("AUTHENTICATED");
          auth.setTextFill(Color.GREEN);
          result2.setText("Password approved");
          this.closeStage();
        } else {
          auth.setText("ACCESS DENIED");
          auth.setTextFill(Color.RED);
          result2.setText(DbConnector.getError());
        }
      } else {
        // THIS WILL BE DELETED, JUST FOR TEST PURPOSES
        result1.setText("");
        result2.setText("");
        
        // Displays the auth message: AUTHENTICATED / ACCESS DENIED
        auth.setText("");
      }
    });
    
    // Buttons box
    HBox buttons = new HBox();
    buttons.getChildren().addAll(btnLogin);
    buttons.setSpacing(10);
    buttons.setAlignment(Pos.CENTER);
    
    // Main box, stores all login elements
    VBox main = new VBox();
    main.setAlignment(Pos.CENTER);
    // CHANGE TOP PADDING once PRINT is removed from the bottom
    main.setPadding(new Insets(10, 0, 0, 0));
    main.setSpacing(10);
    main.getChildren().addAll(logoView, name, pass, buttons, auth, print);
    
    // Background image
    Image background = new Image("file:img/LoginMenu/login_background.png");
    ImageView backgroundView = new ImageView(background);
    
    // Stackpane used to add the background image below the main layout box
    StackPane window = new StackPane();
    window.getChildren().addAll(backgroundView, main);
    
    // Root box
    VBox root = new VBox();
    root.getChildren().addAll(window);
    root.setAlignment(Pos.CENTER);
    
    // Scene
    scene = new Scene(root, 320, 420);

    //Stage
    this.stage = new Stage();
    this.stage.setTitle("Login");
    this.stage.setScene(scene);
    this.stage.setResizable(false);
    this.stage.initModality(Modality.APPLICATION_MODAL);
  }

  public void showStage() {
    this.stage.showAndWait();
  }

  public void closeStage() {
    this.stage.close();
  }

  public User getCurrentUser() {
    return this.currentUser;
  }

}
