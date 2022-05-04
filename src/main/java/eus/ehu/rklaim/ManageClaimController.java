package eus.ehu.rklaim;

import eus.ehu.rklaim.businessLogic.BlFacadeImplementation;
import eus.ehu.rklaim.domain.Claim;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class ManageClaimController {

  @FXML
  private Label welcomeText;

  @FXML
  private TextArea claimDesc;

  @FXML
  protected void manageclaim() {
    System.out.println("Manage claim");
    Claim claim = BlFacadeImplementation.getInstance().getClaim(1, 4);
    claimDesc.setText(claim.getDescription());
    System.out.println(claim.getActions());
  }

}
