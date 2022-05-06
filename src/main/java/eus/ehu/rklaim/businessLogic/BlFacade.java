package eus.ehu.rklaim.businessLogic;

import eus.ehu.rklaim.domain.Action;
import eus.ehu.rklaim.domain.Claim;

/**
 * Interface that specifies the business logic.
 */
public interface BlFacade  {

    // ===== Rklaim
    Claim getClaim(int officerId, int claimId);
    Action addAction(int officerId, Claim claim, String description);
    boolean setResolution(int officerId, Claim claim, Claim.Resolution resolution);
}
