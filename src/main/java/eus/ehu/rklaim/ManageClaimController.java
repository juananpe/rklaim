package eus.ehu.rklaim;

import eus.ehu.rklaim.businessLogic.BlFacadeImplementation;
import eus.ehu.rklaim.domain.Action;
import eus.ehu.rklaim.domain.Claim;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Calendar;
import java.util.Date;

public class ManageClaimController {

  private ObservableList<Action> data;
  private ObservableList<Claim.Resolution> resolutionList;

  private Claim claim;

  @FXML
  private TableView<Action> actionTable;

  @FXML
  private TableColumn<Action, String> actionCol;

  @FXML
  private TableColumn<Action, Date> dateCol;

  @FXML
  private TextArea claimDesc;

  @FXML
  private TextArea taArea;

  @FXML
  private Label lblSetResolution;


  @FXML
  private TextField officerID;

  @FXML
  private ComboBox<Claim.Resolution> comboResolution;

  @FXML
  void setResolution(ActionEvent event) {
    Claim.Resolution resolution = comboResolution.getSelectionModel().getSelectedItem();
    if (resolution!=null){
      BlFacadeImplementation.getInstance().setResolution(Integer.parseInt(officerID.getText()),claim,resolution);
      lblSetResolution.setText("Resolution changed to " + resolution);
      lblSetResolution.getStyleClass().setAll("lbl","lbl-success");
    }
  }

  @FXML
  protected void manageclaim() {
    System.out.println("Manage claim");
    claim = BlFacadeImplementation.getInstance().getClaim(1, 4);
    claimDesc.setText(claim.getDescription());
    data.addAll(claim.getActions());

  }

  @FXML
  void addAction(ActionEvent event) {
    Action action = BlFacadeImplementation.getInstance().addAction(Integer.parseInt(officerID.getText()), claim, taArea.getText());
    data.add(action);
    taArea.clear();
  }

  @FXML
  void initialize(){
    // populate comboResolution
    resolutionList = FXCollections.observableArrayList();
    resolutionList.addAll(Claim.Resolution.values());
    comboResolution.setItems(resolutionList);

    // bind columns
    actionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
    dateCol.setCellValueFactory(new PropertyValueFactory<>("dateTime"));

    data = FXCollections.observableArrayList();

    actionTable.setItems(data);
  }
}
