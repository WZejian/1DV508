package cookbook.mainmenu;

import cookbook.Databaseio;
import cookbook.Images;
import cookbook.Message;
import cookbook.Picture;
import cookbook.Recipe;
import cookbook.User;
import cookbook.UserAuth;
import cookbook.mainmenu.rightsidecontent.MessageButton;
import cookbook.mainmenu.rightsidecontent.scenes.AllRecipesScene;
import cookbook.mainmenu.rightsidecontent.scenes.HelpScene;
import cookbook.mainmenu.rightsidecontent.scenes.HomeScene;
import cookbook.mainmenu.rightsidecontent.scenes.MessagesScene;
import cookbook.mainmenu.rightsidecontent.scenes.MyRecipesScene;
import cookbook.mainmenu.rightsidecontent.scenes.ShoppingListScene;
import cookbook.mainmenu.rightsidecontent.scenes.WeeklyDinnersScene;
import cookbook.splashscreen.SplashObject;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenu {

  // Window dimensions
  final int width = 1100;
  final int height = 700;
  final int leftBarWidth = 300;

  // Main Menu Objects
  private Scene scene;
  private HBox root = new HBox();
  private VBox rightSide = new VBox();
  private BorderPane leftSide = new BorderPane();
  private HBox iconBox = new HBox();
  private ImageView logoHorizontal = new ImageView(Images.logoHorizontal);
  private HBox bottomBox = new HBox();
  private VBox userInfo = new VBox();
  private Label loginStatus = new Label();
  private Label loggedInUser = new Label();
  private Button logoutBtn = new Button("Logout");
  private MessageButton messageBtn = new MessageButton();
  private Image icon = new Image("file:img/favicon.png");

  // Scenes
  WeeklyDinnersScene weekDinScene = new WeeklyDinnersScene();
  HelpScene helpscene = new HelpScene();
  HomeScene homeScene = new HomeScene();

  // Buttons toggle group
  final ToggleGroup buttonsGroup = new ToggleGroup();

  // Java Objects
  User currentUser;
  ArrayList<Recipe> allRecipes = Databaseio.getAllRecipes();
  ArrayList<Message> allMessages;

  // Buttons section
  VBox buttonBox = new VBox();
  ButtonMenuBar homeBtn = new ButtonMenuBar("Home",
      Images.iconHomeYellow,
      Images.iconHomeGreen, buttonsGroup);
  ButtonMenuBar allRecipesBtn = new ButtonMenuBar("All Recipes",
      Images.iconRecipeYellow,
      Images.iconRecipeGreen, buttonsGroup);
  ButtonMenuBar myRecipesBtn = new ButtonMenuBar("My Recipes",
      Images.iconRecipeYellow,
      Images.iconRecipeGreen, buttonsGroup);
  ButtonMenuBar shoppingBtn = new ButtonMenuBar("Shopping List",
      Images.iconShoppingYellow,
      Images.iconShoppingGreen, buttonsGroup);
  ButtonMenuBar dinnerBtn = new ButtonMenuBar("Weekly Dinner",
      Images.iconDinnerYellow,
      Images.iconDinnerGreen, buttonsGroup);
  ButtonMenuBar helpBtn = new ButtonMenuBar("Help",
      Images.iconHelpYellow,
      Images.iconHelpGreen, buttonsGroup);

  /**
   * Class to invoke the main menu.
   *
   * @throws Exception - JavaFX exception.
   */
  public MainMenu(Stage stage, User currentUser) throws Exception {

    // Sets the current user to the user created at login.
    this.currentUser = currentUser;
    // Fetch all messages if any exist
    allMessages = Databaseio.getAllMessages(currentUser);

    // Right Side of Main menu
    rightSide = new VBox();
    rightSide.setMinSize((width - leftBarWidth), height);
    rightSide.setId("right-side");
    rightSide.setPadding(new Insets(5, 10, 5, 10));

    // Left side main menu
    leftSide = new BorderPane();
    leftSide.setMinSize(leftBarWidth, height);
    leftSide.setId("left-side");

    // Top - left bar
    iconBox = new HBox();
    logoHorizontal = new ImageView(Images.logoHorizontal);
    logoHorizontal.setFitHeight(55);
    logoHorizontal.setFitWidth(225);
    iconBox.getChildren().addAll(logoHorizontal);
    iconBox.setAlignment(Pos.CENTER);
    iconBox.setId("top-bar");
    iconBox.setPadding(new Insets(20, 10, 20, 10));

    // Button group settings
    buttonBox.getChildren().addAll(homeBtn, allRecipesBtn, myRecipesBtn,
        shoppingBtn, dinnerBtn, helpBtn);
    buttonBox.setAlignment(Pos.CENTER);
    buttonBox.setSpacing(10);

    // Bottom section
    bottomBox = new HBox();
    bottomBox.setAlignment(Pos.CENTER);
    bottomBox.setId("bottom-bar");
    bottomBox.setPadding(new Insets(20, 10, 20, 10));
    bottomBox.setSpacing(10);

    // Login status box
    userInfo = new VBox();
    loginStatus = new Label();
    loginStatus.setText("Logged in as:");
    loginStatus.setId("login-status");
    loginStatus.setPadding(new Insets(3, 10, 3, 10));
    loginStatus.setMinSize(120, 10);
    loginStatus.setAlignment(Pos.CENTER);
    loggedInUser = new Label();
    loggedInUser.setText(currentUser.getName());
    loggedInUser.setId("user-logged-in");
    loggedInUser.setPadding(new Insets(3, 10, 3, 10));
    loggedInUser.setMinSize(120, 10);
    loggedInUser.setAlignment(Pos.CENTER);
    userInfo.getChildren().addAll(loginStatus, loggedInUser);

    // Set message button
    messageBtn = new MessageButton(allMessages.size());

    // Logout Button
    logoutBtn.setId("logout-button");
    logoutBtn.setMinSize(50, 46);


    bottomBox.getChildren().addAll(userInfo, messageBtn, logoutBtn);

    leftSide.setTop(iconBox);
    leftSide.setCenter(buttonBox);
    leftSide.setBottom(bottomBox);

    /*
     * **********************************************************
     * Dynamically changeable content inside right side of the menu
     **************************************************************/
    rightSide.setMinSize((width - leftBarWidth), height);
    rightSide.setId("right-side");
    rightSide.setPadding(new Insets(30));
    rightSide.getChildren().add(this.homeScene.getScene(currentUser));

    // Root
    root = new HBox();
    root.getChildren().addAll(leftSide, rightSide);

    // Scene
    scene = new Scene(root, width, height);
    scene.getStylesheets().addAll("file:styles/MainMenu.css");

    stage.setScene(scene);
    stage.setTitle("CookBook");
    stage.show();
    stage.setResizable(false);

    // App favicon
    icon = new Image("file:img/favicon.png");
    stage.getIcons().add(icon);

    /*
     * *****************************
     * Button actions / Functionality
     ********************************/
    // Menu button
    homeBtn.setOnAction(e -> {
      setAllInactive(buttonsGroup);
      disableToggles(buttonsGroup, homeBtn);
      homeBtn.setActiveButton();
      rightSide.getChildren().clear();
      rightSide.getChildren().add(homeScene.getScene(currentUser));
    });

    // All recieps button
    //AllRecipesScene allRecipesScene = new AllRecipesScene(currentUser, rightSide);
    allRecipesBtn.setOnAction(e -> {
      setAllInactive(buttonsGroup);
      disableToggles(buttonsGroup, allRecipesBtn);
      allRecipesBtn.setActiveButton();
      rightSide.getChildren().clear();
      rightSide.getChildren().add(new AllRecipesScene(currentUser, rightSide));
    });

    // My recieps button
    //MyRecipesScene myRecipesScene = new MyRecipesScene(currentUser, rightSide);
    myRecipesBtn.setOnAction(e -> {
      setAllInactive(buttonsGroup);
      disableToggles(buttonsGroup, myRecipesBtn);
      myRecipesBtn.setActiveButton();
      rightSide.getChildren().clear();
      rightSide.getChildren().add(new MyRecipesScene(currentUser, rightSide));
    });

    // Help button
    helpBtn.setOnAction(e -> {
      setAllInactive(buttonsGroup);
      disableToggles(buttonsGroup, helpBtn);
      helpBtn.setActiveButton();
      rightSide.getChildren().clear();
      rightSide.getChildren().add(helpscene.getSceneHelp());
    });

    // Shoppinglist button
    ShoppingListScene shopScene = new ShoppingListScene();
    VBox shoppingList = shopScene.initialize(); // meichen
    shoppingBtn.setOnAction(e -> {
      setAllInactive(buttonsGroup);
      disableToggles(buttonsGroup, shoppingBtn);
      shoppingBtn.setActiveButton();
      rightSide.getChildren().clear();
      rightSide.getChildren().add(shopScene.getSceneShop(shoppingList, currentUser));
    });

    // weekly dinner list button
    dinnerBtn.setOnAction(e -> {
      setAllInactive(buttonsGroup);
      disableToggles(buttonsGroup, dinnerBtn);
      dinnerBtn.setActiveButton();
      rightSide.getChildren().clear();
      //rightSide.getChildren().add(weekDinScene.getWeeklyDinnerScene(currentUser, rightSide));
      rightSide.getChildren().add(weekDinScene.getWeeklyDinnerScene(currentUser, rightSide));
    });

    // Messages button
    messageBtn.setOnAction(e -> {
      setAllInactive(buttonsGroup);
      disableAllToggles(buttonsGroup);
      rightSide.getChildren().clear();
      rightSide.getChildren().add(new MessagesScene(this, currentUser, rightSide));
    });

    // Logout button
    logoutBtn.setOnAction(e -> {
      setAllInactive(buttonsGroup);
      disableAllToggles(buttonsGroup);
      UserAuth.authenticate("", "");
      SplashObject welcome = new SplashObject(stage);
      stage.setScene(welcome.getScene());
      Picture.clearTmp();
    });
    /****************
     * END
     *****************/
  }

  public Scene getScene() {
    return this.scene;
  }

  private void setAllInactive(ToggleGroup tg) {
    this.homeBtn.setInactiveButton();
    this.allRecipesBtn.setInactiveButton();
    this.myRecipesBtn.setInactiveButton();
    this.shoppingBtn.setInactiveButton();
    this.dinnerBtn.setInactiveButton();
    this.helpBtn.setInactiveButton();
  }

  /**
   * Disable all toggles but one that's currently active.
   * @param tg - ToggleGroup with ButtonMenuBar objects 
   * @param btn - Selected button of type ButtonMenuBar
   */
  private void disableToggles(ToggleGroup tg, ButtonMenuBar btn) {
    tg.getToggles().forEach(toggle -> {
      ButtonMenuBar node = (ButtonMenuBar) toggle;
      if (!btn.equals(node)) {
        node.setSelected(false);
      }
    });
  }

  /**
   * Disable all toggles in the toggle group.
   * @param tg - ToggleGroup with ButtonMenuBar objects
   */
  private void disableAllToggles(ToggleGroup tg) {
    tg.getToggles().forEach(toggle -> {
      ButtonMenuBar node = (ButtonMenuBar) toggle;
      node.setSelected(false);
    });
  }

  public ArrayList<Recipe> getAllRecipes() {
    return this.allRecipes;
  }

  /**
   * Add a recipe to all Recipes.
   * @param rec - a recipe object
   */
  public void updateRecipes(Recipe rec) {
    if (rec != null) {
      this.allRecipes.add(rec);
    }
  }

  public ArrayList<Message> getAllMessages() {
    return this.allMessages;
  }

  /**
   * Remove a message
   * @param mes - a message
   */
  public void updateMessage() {
    this.allMessages = Databaseio.getAllMessages(currentUser);
  }

  public void setMessageButton(MessageButton mb) {
    // Delete old button
    bottomBox.getChildren().remove(this.messageBtn);
    this.messageBtn = mb;
    // Set new button action
    this.messageBtn.setOnAction(e -> {
      setAllInactive(buttonsGroup);
      disableAllToggles(buttonsGroup);
      this.rightSide.getChildren().clear();
      this.rightSide.getChildren().add(new MessagesScene(this, this.currentUser, this.rightSide));
    });
    // Add new button to bottom box
    bottomBox.getChildren().add(1, messageBtn);
  }
}
