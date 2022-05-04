package eus.ehu.rklaim;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ManageClaim extends Application {


  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(ManageClaim.class.getResource("manageclaim.fxml"));
    Parent root = fxmlLoader.load();
    Scene scene = new Scene(root, 600, 550);
    stage.setTitle("Manage Claim");
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }

}
