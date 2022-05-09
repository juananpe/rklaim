package eus.ehu.rklaim;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.net.URL;

public class ManageClaim extends Application {


  @Override
  public void start(Stage stage) throws IOException {
    URL url = ManageClaim.class.getResource("manageclaim.fxml");
    System.out.println(url);
    FXMLLoader fxmlLoader = new FXMLLoader(url);
    Parent root = fxmlLoader.load();
    // ManageClaimController controller = fxmlLoader.getController();

    Scene scene = new Scene(root, 525, 525);
    scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
    stage.setTitle("Manage Claim");
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }

}
