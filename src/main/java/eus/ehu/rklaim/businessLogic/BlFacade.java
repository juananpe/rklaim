package eus.ehu.rklaim.businessLogic;

import eus.ehu.rklaim.domain.Claim;

/**
 * Interface that specifies the business logic.
 */
public interface BlFacade  {

    // ===== Rklaim
    Claim getClaim(int officerId, int claimId);
    void addAction(int officerId, Claim claim, String description);
    void setResolution(int officerId, Claim claim, Claim.Resolution resolution);
}
