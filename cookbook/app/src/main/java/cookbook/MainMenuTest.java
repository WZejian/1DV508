package cookbook;

import cookbook.mainmenu.MainMenu;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainMenuTest extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {

    DbConnector.connect();

    User currentUser = UserAuth.authenticate("ZEJIAN", "ZEJIAN");
    MainMenu menu1 = new MainMenu(primaryStage, currentUser);
    primaryStage.setScene(menu1.getScene());
  }
}
