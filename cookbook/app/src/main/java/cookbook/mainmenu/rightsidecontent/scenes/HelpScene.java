package cookbook.mainmenu.rightsidecontent.scenes;

import cookbook.mainmenu.rightsidecontent.Helptext;
import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * HelpScene class.
 */
public class HelpScene {

  /**
   * this is used in the mainmenu.
   */
  public VBox getSceneHelp() {

    Button video = new Button();
    video.setText("Tutorial videos");
    video.setMinWidth(95);
    video.setStyle("-fx-background-color: #845b46; -fx-text-fill: #FFE4C4");
    video.setFont(Font.font("Fira Sans", FontWeight.BOLD, 15));

    Button instruction = new Button();
    instruction.setText("Instructions");
    instruction.setMinWidth(95);
    instruction.setStyle("-fx-background-color: #845b46; -fx-text-fill: #FFE4C4");
    instruction.setFont(Font.font("Fira Sans", FontWeight.BOLD, 15));

    Button us = new Button();
    us.setText("Contact us");
    us.setMinWidth(95);
    us.setStyle("-fx-background-color: #845b46; -fx-text-fill: #FFE4C4");
    us.setFont(Font.font("Fira Sans", FontWeight.BOLD, 15));

    Button searchFeature = new Button();
    searchFeature.setText("Search features");
    searchFeature.setMinWidth(95);
    searchFeature.setStyle("-fx-background-color: #845b46; -fx-text-fill: #FFE4C4");
    searchFeature.setFont(Font.font("Fira Sans", FontWeight.BOLD, 15));

    HBox tileButtons = new HBox();
    tileButtons.setSpacing(20);

    tileButtons.getChildren().addAll(video, instruction, us, searchFeature);
    VBox layout = new VBox();
    // button action for the video
    video.setOnAction(showVideo -> {

      layout.getChildren().clear();
      layout.getChildren().add(tileButtons);
      Label test = new Label("Tutorial Videos:");
      // a video later
      test.setFont(new Font("Fira Sans", 40));

      Button login = new Button();
      login.setText("Login");
      login.setMinWidth(200);
      login.setStyle("-fx-background-color: #845b46; -fx-text-fill: #FFE4C4");
      login.setFont(Font.font("Fira Sans", FontWeight.BOLD, 20));

      login.setOnAction(o -> {
        Image c = new Image("file:img/video/newLogin.gif");
        // C:\Users\User\Documents\Captura\2022-05-26-15-19-06.gif

        ImageView s = new ImageView(c);
        Group g = new Group();
        g.getChildren().add(s);
        Scene t = new Scene(g, 854, 480);

        Stage newWindow = new Stage();
        newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.setTitle("Tutorial for login");
        newWindow.setScene(t);
        newWindow.setResizable(false);
        newWindow.show();

      });

      Button home = new Button();
      home.setText("Home");
      home.setMinWidth(200);
      home.setStyle("-fx-background-color: #845b46; -fx-text-fill: #FFE4C4");
      home.setFont(Font.font("Fira Sans", FontWeight.BOLD, 20));

      home.setOnAction(o -> {

        Image c = new Image("file:img/video/home (2).gif");

        ImageView s = new ImageView(c);
        Group g = new Group();
        g.getChildren().add(s);
        Scene t = new Scene(g, 854, 480);

        Stage newWindow = new Stage();
        newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.setTitle("Tutorial for home");
        newWindow.setScene(t);
        newWindow.setResizable(false);
        newWindow.show();

      });

      Button allrecipe = new Button();
      allrecipe.setText("All Recipes");
      allrecipe.setMinWidth(200);
      allrecipe.setStyle("-fx-background-color: #845b46; -fx-text-fill: #FFE4C4");
      allrecipe.setFont(Font.font("Fira Sans", FontWeight.BOLD, 20));

      allrecipe.setOnAction(o -> {
        Image c = new Image("file:img/video/allrecipes.gif");

        ImageView s = new ImageView(c);
        Group g = new Group();
        g.getChildren().add(s);
        Scene t = new Scene(g, 854, 480);

        Stage newWindow = new Stage();
        newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.setTitle("Tutorial for Allrecipes");
        newWindow.setScene(t);
        newWindow.setResizable(false);
        newWindow.show();

      });

      Button myrecipe = new Button();
      myrecipe.setText("My Recipes");
      myrecipe.setMinWidth(200);
      myrecipe.setStyle("-fx-background-color: #845b46; -fx-text-fill: #FFE4C4");
      myrecipe.setFont(Font.font("Fira Sans", FontWeight.BOLD, 20));

      myrecipe.setOnAction(o -> {
        Image c = new Image("file:img/video/myrecipes.gif");

        ImageView s = new ImageView(c);
        Group g = new Group();
        g.getChildren().add(s);
        Scene t = new Scene(g, 854, 480);

        Stage newWindow = new Stage();
        newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.setTitle("Tutorial for Myrecipes");
        newWindow.setScene(t);
        newWindow.setResizable(false);
        newWindow.show();

      });

      Button shop = new Button();
      shop.setText("Shopping list");
      shop.setMinWidth(200);
      shop.setStyle("-fx-background-color: #845b46; -fx-text-fill: #FFE4C4");
      shop.setFont(Font.font("Fira Sans", FontWeight.BOLD, 20));

      shop.setOnAction(o -> {
        Image c = new Image("file:img/video/shoplist.gif");

        ImageView s = new ImageView(c);
        Group g = new Group();
        g.getChildren().add(s);

        Scene t = new Scene(g, 854, 480);

        Stage newWindow = new Stage();
        newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.setTitle("Tutorial for Shoppinglist");
        newWindow.setScene(t);
        newWindow.setResizable(false);
        newWindow.show();

      });

      Button dinner = new Button();
      dinner.setText("Weekly Dinner");
      dinner.setMinWidth(200);
      dinner.setStyle("-fx-background-color: #845b46; -fx-text-fill: #FFE4C4");
      dinner.setFont(Font.font("Fira Sans", FontWeight.BOLD, 20));

      dinner.setOnAction(o -> {
        Image c = new Image("file:img/video/weekdinner (2).gif");

        ImageView s = new ImageView(c);
        Group g = new Group();
        g.getChildren().add(s);
        Scene t = new Scene(g, 854, 480);

        Stage newWindow = new Stage();
        newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.setTitle("Tutorial for weeklydinner");
        newWindow.setScene(t);
        newWindow.setResizable(false);
        newWindow.show();

      });

      Button helpsystem = new Button();
      helpsystem.setText("Help system");
      helpsystem.setMinWidth(200);
      helpsystem.setStyle("-fx-background-color: #845b46; -fx-text-fill: #FFE4C4");
      helpsystem.setFont(Font.font("Fira Sans", FontWeight.BOLD, 20));

      helpsystem.setOnAction(o -> {
        Image c = new Image("file:img/video/help (3).gif");

        ImageView s = new ImageView(c);
        Group g = new Group();
        g.getChildren().add(s);
        Scene t = new Scene(g, 854, 480);

        Stage newWindow = new Stage();
        newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.setTitle("Tutorial for Help system");
        newWindow.setScene(t);
        newWindow.setResizable(false);
        newWindow.show();

      });

      Button admin = new Button();
      admin.setText("Admin");
      admin.setMinWidth(200);
      admin.setStyle("-fx-background-color: #845b46; -fx-text-fill: #FFE4C4");
      admin.setFont(Font.font("Fira Sans", FontWeight.BOLD, 20));

      admin.setOnAction(o -> {
        Image c = new Image("file:img/video/admin.gif");

        ImageView s = new ImageView(c);
        Group g = new Group();
        g.getChildren().add(s);
        Scene t = new Scene(g, 854, 480);

        Stage newWindow = new Stage();
        newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.setTitle("Tutorial for Admin");
        newWindow.setScene(t);
        newWindow.setResizable(false);
        newWindow.show();

      });

      Button message = new Button();
      message.setText("Messages");
      message.setMinWidth(200);
      message.setStyle("-fx-background-color: #845b46; -fx-text-fill: #FFE4C4");
      message.setFont(Font.font("Fira Sans", FontWeight.BOLD, 20));

      message.setOnAction(o -> {
        Image c = new Image("file:img/video/messages.gif");

        ImageView s = new ImageView(c);
        Group g = new Group();
        g.getChildren().add(s);
        Scene t = new Scene(g, 854, 480);

        Stage newWindow = new Stage();
        newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.setTitle("Tutorial for Admin");
        newWindow.setScene(t);
        newWindow.setResizable(false);
        newWindow.show();

      });

      HBox row1 = new HBox();
      HBox row2 = new HBox();

      row1.getChildren().addAll(login, home, allrecipe);
      row1.setSpacing(20);
      row2.getChildren().addAll(myrecipe, shop, dinner);
      row2.setSpacing(20);

      HBox row3 = new HBox();
      row3.getChildren().addAll(message, helpsystem, admin);
      row3.setSpacing(20);

      layout.setSpacing(20);
      layout.getChildren().addAll(test, row1, row2, row3);
    });

    // button action for the instructions
    instruction.setOnAction(showInstructions -> {

      layout.getChildren().clear();
      layout.getChildren().add(tileButtons);
      ComboBox<String> cb = new ComboBox<>();

      cb.getItems().addAll("Login", "Home", "All Recipes", "My Recipes", "Shopping list",
          "Weekly Dinner", "Help", "Admin", "Messages");

      cb.setPromptText("Choose here");
      cb.setMinWidth(100);
      cb.setMinHeight(30);

      ScrollPane scrollinstruction = new ScrollPane();
      scrollinstruction.setStyle("-fx-background: #f8d485;\n -fx-background-color: #f8d485");

      // content that might be important
      Label steps = new Label("""
          Instructions for a specific functionality will display here:
          """);
      cb.setOnAction(e -> { // set on action is avilable for combobox
        if (cb.getValue().equals("Login")) {
          steps.setText("""
              In the login page:\n
              To choose which role:
              1, Go to login page
              2, Log in with whether user or admin account
              """);

        } else if (cb.getValue().equals("Home")) {
          steps.setText("""
              In Home:\n
              To change user name:
              1, Enter new user name
              2, Click update button\n
              To change your password:
              1, Enter old password
              2, Enter new password
              3, Comfirm new password
              4, Click update button\n
              To delete account:
              1, Enter user name
              2, Click delete button

                """);

        } else if (cb.getValue().equals("All Recipes")) {
          steps.setText("""
              In all recipes:\n
              To search a recipe of your wish:
              1, Click the search bar at the top
              2, Type in recipe name, ingredients and tags
              3, Click search\n
              To create a new recipe:
              1, Click Add recipe
              2, Type in the recipe name
              3, Write a short desciption for your recipe
              4, Write a long desciption for your recipe
              5, Select the ingredients
              6, Click Add recipe\n
              To check a recipe:
              1, Click on one the recipe
              2, All the inoformation will be shown

              """);
        } else if (cb.getValue().equals("My Recipes")) {
          steps.setText("""
              In My Recipes\n
              To display recipes that were created by me
              1, Click the button on top left
              \n
              To display my favorite recipes
              1, Click the button on top right

              """);

        } else if (cb.getValue().equals("Shopping list")) {
          steps.setText("""
              In shopping list:\n
              To add item into your shopping list:
              1, Click on the name, quantity and unit of the ingredient
              2, Type in name, quantity and unit
              3, Click add\n
              To delete an item:
              1, Click on corresponding row
              2, Then click Delete selected row\n
              To delete all items in the shopping list:
              1, Click on Clear All\n
              To have screenshot of your shopping list
              1, Click Save as a picture
                """);
        } else if (cb.getValue().equals("Weekly Dinner")) {
          steps.setText("""
              In weekly dinner:\n
              To display your weekly dinner list
              1, Select start day and finish day
              2, Then the recipes will be displayed\n
              To add the ingredients in recipe into shopping list
              1, Click Add ingredients to shooping list
              2, Then Click shopping list to check
                """);
        } else if (cb.getValue().equals("Help")) {
          steps.setText("""
              In help:\n
              To watch a tutorial video:
              1,Click on tutorial videos
              2,Click on the corresponding buttons to see how the program works\n
              To find more information about the group:
              1, Click on contact us
              2, There you can try to fix a bug, visite our website and more\n
              To search features:
              1, Type in the feature that you like to know about
              2, Options are: Login, home, shoppinglist, recipes, messages, helpsystem, weeklydinner,admin
              3, Click search button

                """);
        } else if (cb.getValue().equals("Admin")) {
          steps.setText("""
              As an admin:\n
              To create a new user:
              1, Click Create a new user button
              2, Type username,password and nickname
              3, Click create button\n
              To edit user's information:
              1, Click edit user's information
              2, Enter username\n
              To Change user's privilege
              1, Click Change user's privilege
              2, Enter username\n
              To Delete an user
              1, Click delete user button
              2, Enter username

                """);
        } else if (cb.getValue().equals("Messages")) {
          steps.setText("""
              In Messages:\n
              To view the recipes that the others has shared
              1, Click on the picture of the recipe\n
              To delete a message:
              1, Click on deny\n
              To accept a message:
              1, Click on accept

                """);
        }

      });

      steps.setFont(new Font("Fira Sans", 25));
      scrollinstruction.setContent(steps);
      scrollinstruction.setPrefSize(120, 550);

      layout.setSpacing(5);
      layout.getChildren().addAll(cb, scrollinstruction);
    });

    // button action for the search feature
    searchFeature.setOnAction(searchF -> {

      layout.getChildren().clear();
      layout.getChildren().add(tileButtons);

      TextField searchBox = new TextField(); // place to enter
      searchBox.setMinWidth(300);
      searchBox.setMinHeight(33);
      searchBox.setAlignment(Pos.TOP_LEFT);
      searchBox.setPromptText("Search the features you like to know about here");

      Button searchBtn = new Button("Search"); // button to search
      // searchBtn.setText("search");
      searchBtn.setMinWidth(95);
      Image searchicon = new Image("file:img/Icons/search_icon_yellow.png", 20, 20, false, false);
      searchBtn.setGraphic(new ImageView(searchicon));
      searchBtn.setStyle("-fx-background-color: #845b46; -fx-text-fill: #FFE4C4");
      searchBtn.setFont(Font.font("Fira Sans", FontWeight.BOLD, 15));

      ScrollPane sp = new ScrollPane();
      sp.setStyle("-fx-background: #f8d485;\n -fx-background-color: #f8d485");

      Label searchresult = new Label();
      searchresult.setFont(new Font("Fira Sans", 40));

      searchBtn.setOnAction(e -> { // pop up after pressing the search button

        if (searchBox.getText().equals("") || searchBox.getText().equals(" ")) {
          searchresult.setText("please enter the correct feature");
          sp.setContent(searchresult);
          layout.getChildren().add(sp);
        }

        Helptext empty = new Helptext("", "please enter the correct feature");

        Helptext login = new Helptext("login", """
            General Information about login:
            In the login page:
            - You will be welcomed by a animated splash screen
            - You can choose to either login as a normal user or an admin
            """);
        Helptext shoplist = new Helptext("shoppinglist", """
            General Information about Shopping list:
            In the Shopping list:
            - You can see the items in your shopping list
            - You can add or delete items
            - You can save your shopping list as a picture""");
        Helptext home = new Helptext("home", """
            General Information about Home:
            In the Home page
            - You can modify the user accounts if you're an admin
            - You can see a personlized welcome message
            - You can change your nickname as a normal user
            - You can change your password as a normal user
            - You can delete your account as normal user
              """);
        Helptext admin = new Helptext("admin",
            """
                General Information about Admin:
                If you have logged in as an admin:
                - You can see all the functionalities that a users would see
                plus the privileges that only the admin is authorized to
                - You can add a new user with user name
                - You can modify an existing user
                - You can delete an existing user
                - You can create a new user
                - You can edit user's information
                - You can change user's privilege """);
        Helptext helpsystem = new Helptext("helpsystem", """
            General Information about Help system:
            In the Help page:\n
            - You can see a tutorial video about how to use the application
            - You can see some step-by-step insructions for a specific funtionality
            - There's a search box where you search a keyword to better understand a feature
            - You can check our website in contact us\n
              """);
        Helptext weeklydinner = new Helptext("weeklydinnerer", """
            General Information about Weekly Dinner:
            In the Weekly dinner page:\n
            - You can see weeks of recipe list that you have added
            - You can select and display the dinner list for a week
            - You can create weekly dinner lists for several weeks
            - You can add the ingredients in recipes into the shooping list
              """);
        Helptext recipes = new Helptext("recipes", """
            General Information about Recipes:\n
            In All Recipes and My Recipes
            - You can see the recipes created by you
            and your favorite ones in My recipes
            - You can search based on recipe name, ingredients, and tags
            - You can find all the recipes
            - You can create a new recipe\n
            - You can add a recipe as your favorite
            - You can give comments to a recipe
            - You add recipes to your weekly dinner list
            - You share recipes to other users

            """);
        Helptext messages = new Helptext("messages", """
            General Information about messages
            In messgaes
            - You can see the messages that the others sent you
            - You can view their recipe
            - You can accept or deny their messages
            - You can see the recipes that the other has shared with you
            - Whenever there's a message to you, your message button will be red
            """);

        ArrayList<Helptext> helptext = new ArrayList<>();
        helptext.add(login);
        helptext.add(shoplist);
        helptext.add(home);
        helptext.add(admin);
        helptext.add(helpsystem);
        helptext.add(weeklydinner);
        helptext.add(recipes);
        helptext.add(messages);
        helptext.add(empty);
        String userinput = searchBox.getText();

        // ArrayList<Helptext> searcharray = new ArrayList<>();

        for (Helptext row : helptext) {
          userinput.toLowerCase();
          if (row.getMatch().contains(userinput)) {

            searchresult.setText(row.getText());
            searchresult.setFont(Font.font(20));

            sp.setContent(searchresult);
            sp.setMinHeight(400);
            layout.setSpacing(30);
            layout.getChildren().add(sp);

          }
        }

      });

      Button clearBtn = new Button();
      clearBtn.setText("Clear");
      clearBtn.setMinWidth(95);
      clearBtn.setStyle("-fx-background-color: #845b46; -fx-text-fill: #FFE4C4");
      clearBtn.setFont(Font.font("Fira Sans", FontWeight.BOLD, 15));

      clearBtn.setOnAction(e -> {
        searchBox.setText("");
        searchresult.setText("");
        sp.setContent(searchresult);

      });

      HBox hb = new HBox();

      hb.getChildren().addAll(searchBox, searchBtn, clearBtn);
      hb.setSpacing(20);
      layout.getChildren().addAll(hb, searchresult);
      layout.setSpacing(30);
    });

    // button action for the contact us
    us.setOnAction(showcontactinfo -> {

      layout.getChildren().clear();
      layout.getChildren().add(tileButtons);

      Label contactInfo = new Label("""
          Tel: +46 123456789
          Email: support@aluminium.com
          Service Hours: Tuesday 13:00-13:03
          Office location: UB 4th floor, 35195 Vaxjo, Sweden
          """);
      contactInfo.setFont(new Font("Fira Sans", 30));

      HBox copyright = new HBox();
      Label copylabel = new Label("Copyright \u00a9 2022 Aluminium");
      copylabel.setFont(Font.font("Fira Sans", 30));
      copyright.getChildren().add(copylabel);
      Hyperlink link = new Hyperlink("meichen.tech/cookbook");
      // String mylink = link.getText();
      link.setFont(new Font("Fira Sans", 30));
      layout.getChildren().add(link);

      link.setOnAction(showlink -> {
        layout.getChildren().clear();
        layout.getChildren().add(tileButtons);
        layout.getChildren().addAll(contactInfo, copyright);
        Text notice = new Text("Copy this link to your browser to visit our website");
        notice.setFont(new Font("Fira Sans", 25));
        TextField givelink = new TextField("meichen.tech/cookbook");

        layout.getChildren().addAll(notice, givelink);
      });
      HBox r = new HBox();
      r.setSpacing(20);
      r.getChildren().addAll(copyright, link);

      layout.setSpacing(30);
      layout.getChildren().addAll(contactInfo, copyright, r);
    });

    layout.getChildren().addAll(tileButtons);
    return layout;
  }
}
