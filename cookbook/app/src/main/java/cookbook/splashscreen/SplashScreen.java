package cookbook.splashscreen;

import cookbook.Images;
import cookbook.UserAuth;
import cookbook.loginmenu.LoginMenu;
import cookbook.mainmenu.ButtonMenuBar;
import cookbook.mainmenu.MainMenu;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SplashScreen extends javafx.application.Application {
  
  @Override
  public void start(Stage primaryStage) throws Exception {
  
    // Window diamentions
    final int width = 1100;
    final int height = 700;

    ButtonMenuBar enterBtn = new ButtonMenuBar("Enter", 
                                                Images.iconEnterYellow,
                                                Images.iconEnterGreen);
                                              
    enterBtn.setOnAction(e -> {
      try {
        // Open login window.
        LoginMenu login = new LoginMenu();
        login.showStage();
        
        // Checks if user logged in and launches menu.
        if (UserAuth.getStatus()) {
          MainMenu mainMen = new MainMenu(primaryStage, login.getCurrentUser());
          primaryStage.setScene(mainMen.getScene());
        }
      } catch (Exception m) {
        m.printStackTrace();
      }
      
      
    });

    Image cookbook = new Image("file:img/Splash.gif");

    ImageView splash = new ImageView(cookbook);
    splash.setPreserveRatio(true);
    splash.setId("splash");
    splash.setFitHeight(600);

    VBox root = new VBox();
    root.setAlignment(Pos.CENTER);
    root.getChildren().addAll(splash, enterBtn);
    root.setStyle("-fx-background-color: #B88453;");
    

   
    Scene temp = new Scene(root, width, height);

    //Not sure the CSS works :( Help me.
    temp.getStylesheets().addAll("file:styles/SplashScreen.css");
    Scene splashScene = temp;
    primaryStage.setScene(splashScene);
    primaryStage.setResizable(false);
    primaryStage.setTitle("CookBook");
    primaryStage.show();

    // App favicon
    Image icon = new Image("file:img/favicon.png");
    primaryStage.getIcons().add(icon);
  }
}
