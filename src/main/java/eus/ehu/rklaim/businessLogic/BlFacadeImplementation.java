package eus.ehu.rklaim.businessLogic;

import eus.ehu.rklaim.configuration.ConfigXML;
import eus.ehu.rklaim.dataAccess.DataAccess;
import eus.ehu.rklaim.domain.Claim;


import java.util.Calendar;


/**
 * Implements the business logic as a web service.
 */
public class BlFacadeImplementation implements BlFacade {

  DataAccess dbManager;
  ConfigXML config = ConfigXML.getInstance();

  private static BlFacadeImplementation bl = new BlFacadeImplementation();

  public static BlFacadeImplementation getInstance() {
    return bl;
  }

  private BlFacadeImplementation() {
    System.out.println("Creating BlFacadeImplementation instance");
    dbManager = new DataAccess();
    dbManager.close();
  }


  public void close() {
    dbManager.close();
  }

  /**
   * This method invokes the data access to initialize the database with some events and questions.
   * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
   */
  public void initializeBD() {
    dbManager.open(false);
    dbManager.initializeDB();
    dbManager.close();
  }

  // rklaim -------------
  @Override
  public Claim getClaim(int officerId, int claimId) {
    dbManager.open(false);
    Claim claim = dbManager.getClaim(claimId);
    dbManager.createAccess(claim, officerId);
    dbManager.close();
    return claim;
  }

  @Override
  public void addAction(int officerId, Claim claim, String description) {
    dbManager.open(false);
    dbManager.addAction(officerId, claim, description, Calendar.getInstance().getTime());
    dbManager.close();
  }

  @Override
  public void setResolution(int officerId, Claim claim, Claim.Resolution resolution) {
    dbManager.open(false);
    dbManager.setResolution(officerId, claim, resolution);
    dbManager.close();
  }

  public static void main(String[] args) {
    int officerId = 1;
    int claimId = 4;

    Claim claim = BlFacadeImplementation.getInstance().getClaim(officerId, claimId);
    BlFacadeImplementation.getInstance().addAction(officerId, claim, "an email has been sent to the treasury registry");
    BlFacadeImplementation.getInstance().setResolution(officerId,claim, Claim.Resolution.UPHELD);

  }
}
