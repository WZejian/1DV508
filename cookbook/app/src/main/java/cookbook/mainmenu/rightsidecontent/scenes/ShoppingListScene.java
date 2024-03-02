package cookbook.mainmenu.rightsidecontent.scenes;

import cookbook.Databaseio;
import cookbook.DbConnector;
import cookbook.ImageBuilderUtil;
import cookbook.IngredientRec;
import cookbook.User;
import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * meichen ji.
 */
public class ShoppingListScene {
  private TableView<IngredientRec> table = new TableView<IngredientRec>();
  private final Label label = new Label("My shopping list");
  // this will be fetched from database in later stage
  private final ObservableList<IngredientRec> data = FXCollections.observableArrayList();
  TableColumn<IngredientRec, String> nameCol = new TableColumn<>("Name");
  TableColumn<IngredientRec, Double> quanCol = new TableColumn<>("Quantity");
  TableColumn<IngredientRec, String> unitCol = new TableColumn<>("Unit");
  //TableColumn<IngredientRec, String> deleCol = new TableColumn<>("Delete");
  private final HBox hb = new HBox();   // textfield and add button
  private final HBox hb2 = new HBox();  // delete, clearall, export button
  private final TextField addName = new TextField();
  private final TextField addQuantity = new TextField();
  private final TextField addUnit = new TextField();
  private final Button addButton = new Button("Add");
  private final Button deleteButton = new Button("Delete Selected Row");
  private final Button clearAllButton = new Button("Clear All");
  private final Button exportButton = new Button("Save As Picture");

  
  /**
   * To initialize the tableview and buttons.
   * @return
   */
  public VBox initialize() {
    label.setStyle("-fx-text-fill: #845b46");
    label.setFont(Font.font("TimesRomes", FontWeight.BOLD, FontPosture.ITALIC, 22));
    table.setEditable(false);
    table.getStylesheets().addAll("file:styles/Shoppinglist.css");
    table.setPlaceholder(new Label("Nothing to show, maybe add some?"));
    table.setMaxSize(300, 500);

    table.getColumns().add(nameCol);
    nameCol.setMinWidth(150);

    table.getColumns().add(quanCol);
    quanCol.setMinWidth(70);

    table.getColumns().add(unitCol);
    unitCol.setMinWidth(50);

    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    quanCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    unitCol.setCellValueFactory(new PropertyValueFactory<>("unit"));
    

    //input check, only numbers
    addQuantity.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, 
          String oldValue, String newValue) {
          if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
              addQuantity.setText(oldValue);
          }
      }
    });

    addName.setPromptText("Item Name");
    addName.setPrefWidth(150);
    addName.setFont(Font.font("Fira Sans", FontWeight.SEMI_BOLD, 15));

    deleteButton.setStyle("-fx-background-color: #845b46;-fx-text-fill: #FFE4C4");
    deleteButton.setFont(Font.font("Fira Sans", FontWeight.BOLD, 15));
    addButton.setStyle("-fx-background-color: #845b46;-fx-text-fill: #FFE4C4");
    addButton.setFont(Font.font("Fira Sans", FontWeight.BOLD, 15));
    clearAllButton.setStyle("-fx-background-color: #845b46;-fx-text-fill: #FFE4C4");
    clearAllButton.setFont(Font.font("Fira Sans", FontWeight.BOLD, 15));
    exportButton.setStyle("-fx-background-color: #845b46;-fx-text-fill: #FFE4C4");
    exportButton.setFont(Font.font("Fira Sans", FontWeight.BOLD, 15));

    addQuantity.setPromptText("Quantity");
    addQuantity.setPrefWidth(75);
    addQuantity.setFont(Font.font("Fira Sans", FontWeight.SEMI_BOLD, 15));

    addUnit.setPromptText("Unit");
    addUnit.setPrefWidth(50);
    addUnit.setFont(Font.font("Fira Sans", FontWeight.SEMI_BOLD, 15));

    hb.getChildren().addAll(addName, addQuantity, addUnit, addButton);
    hb.setSpacing(3);
    hb2.getChildren().addAll(deleteButton, clearAllButton, exportButton);
    hb2.setSpacing(3);
    final VBox vbox = new VBox();
    vbox.setSpacing(5);
    vbox.setPadding(new Insets(10, 0, 0, 10));
    vbox.getChildren().addAll(label, table, hb, hb2);
    vbox.setAlignment(Pos.CENTER);
    hb.setAlignment(Pos.CENTER);
    hb2.setAlignment(Pos.CENTER);


    return vbox;
  }
  
  /**
   * Give a inialized vbox, will refresh and support button functions.
   * @param vbox the inialized tableview and buttons.
   * @return
   */
  public VBox getSceneShop(VBox vbox, User currentUser) {
    /**
     * This is old, to edit cell, keep it for now in case we use it later.
    nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
    nameCol.setOnEditCommit(
      new EventHandler<TableColumn.CellEditEvent<IngredientRec,String>>() {
        @Override
        public void handle(CellEditEvent<IngredientRec, String> t) {
          ((IngredientRec) t.getTableView().getItems().get(
            t.getTablePosition().getRow())
            ).setName(t.getNewValue());
        }
      }
      // SQL update here
    );
    */
    
    

    refresh(currentUser.getId());
    
    addButton.disableProperty().bind(
        Bindings.isEmpty(addName.textProperty())
        .or(Bindings.isEmpty(addQuantity.textProperty()))
        .or(Bindings.isEmpty(addUnit.textProperty()))
    );

    addButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent e) {
        String newIngrName = addName.getText(); 
        Double newIngrQuan = Double.parseDouble(addQuantity.getText());
        String newIngrUnit = addUnit.getText();
        Databaseio.shopAddRow(currentUser.getId(), newIngrName, newIngrQuan, newIngrUnit);
        addName.clear();
        addQuantity.clear();
        addUnit.clear();
        refresh(currentUser.getId());
      }
    });

    clearAllButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent e) {
        Databaseio.shopDeleteAll(currentUser.getId());
        refresh(currentUser.getId());
      }
    });

    deleteButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent e) { 
        int selectedIndex = table.getSelectionModel().getSelectedIndex(); 
        if (selectedIndex >= 0) {
          IngredientRec selectedIngr = table.getItems().get(selectedIndex);
          int selectedIngrId = selectedIngr.getId();
          Databaseio.shopDeleteRow(selectedIngrId, currentUser.getId());
          refresh(currentUser.getId());
        } else { 
          // Nothing selected. 
          Alert alert = new Alert(AlertType.WARNING); 
          alert.setTitle("No Selection"); 
          alert.setHeaderText("No item Selected"); 
          alert.setContentText("Please select an item."); 
          alert.showAndWait(); 
        }
      } 
    });

    exportButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent e) { 
        Stage choose = (Stage) vbox.getScene().getWindow();
        DirectoryChooser dc = new DirectoryChooser();
        File selected = dc.showDialog(choose);
        String path;
        if (selected == null) {
          Alert alert = new Alert(AlertType.INFORMATION);  
          alert.setTitle("No Folder Selected");  
          alert.setHeaderText(null);  
          alert.setContentText("The saving is cancelled!");  
          alert.showAndWait(); 
        } else {
          path = selected.getPath();
          ArrayList<IngredientRec> inlst = new ArrayList<>();
          for (IngredientRec ingr : data) {
            inlst.add(ingr);
          }
          String savePath = ImageBuilderUtil.imageBuilder("./img/splst_head.png", inlst, path);
          Alert alert2 = new Alert(AlertType.INFORMATION); 
          alert2.setTitle("Successfully Saved"); 
          alert2.setHeaderText(null); 
          alert2.setContentText("The picture is saving to: " + savePath);
          alert2.showAndWait(); 
        }
      }
    });

    return vbox;
  }

  /**
   * This is to refresh table.
   * meichen ji
   * not finished, will be used when sql query is ready
   */
  public void refresh(int userId) {
    try {
      String selectItem = "SELECT * FROM shoping_list WHERE user_id=?;";
      ResultSet rs = DbConnector.runQuery(selectItem, userId);
      data.clear();
      while (rs.next()) {
        IngredientRec ingr = null;
        ingr = new IngredientRec(
          rs.getInt("id"),
          rs.getInt("user_id"),
          rs.getString("ingredient_name"),
          rs.getDouble("quantity"),
          rs.getString("unit")
        );
        data.add(ingr);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    table.setItems(data);
  }

  

  

  

}