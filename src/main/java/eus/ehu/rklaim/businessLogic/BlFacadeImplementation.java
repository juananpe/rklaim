package eus.ehu.rklaim.businessLogic;

import eus.ehu.rklaim.configuration.ConfigXML;
import eus.ehu.rklaim.dataAccess.DataAccess;
import eus.ehu.rklaim.domain.Action;
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
        boolean initialize = config.getDataBaseOpenMode().equals("initialize");
        dbManager = new DataAccess();
        if (initialize)
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
    public Action addAction(int officerId, Claim claim, String description) {
        dbManager.open(false);
        Action action = dbManager.addAction(officerId, claim, description, Calendar.getInstance().getTime());
        dbManager.close();

        return action;
    }

    @Override
    public boolean setResolution(int officerId, Claim claim, Claim.Resolution resolution) {
        dbManager.open(false);
        boolean ok = dbManager.setResolution(officerId, claim, resolution);
        dbManager.close();
        return ok;
    }

    public static void main(String[] args) {
        int officerId = 1;
        int claimId = 4;

        Claim claim = BlFacadeImplementation.getInstance().getClaim(officerId, claimId);
        BlFacadeImplementation.getInstance().addAction(officerId, claim, "an email has been sent to the treasury registry");
        BlFacadeImplementation.getInstance().setResolution(officerId, claim, Claim.Resolution.UPHELD);

    }
}
