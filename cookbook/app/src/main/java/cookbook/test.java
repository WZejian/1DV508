package cookbook;

import javafx.application.Application;

public class test {
  public static void main(String[] args) {
    try {
      DbConnector.connect();
      Application.launch(Cookbook.class, args);
    } catch (Exception e) {
      System.out.print(e.toString());
    }
    
  }
}
