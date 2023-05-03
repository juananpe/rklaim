package eus.ehu.rklaim;

import eus.ehu.rklaim.dataAccess.DataAccess;
import eus.ehu.rklaim.domain.Claim;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DBTest {

    private DataAccess db = new DataAccess();

    @BeforeEach
    public void setUp() {
            db.initializeDB();
    }

    @Test
    public void test() {
        Claim claim = db.getClaim(1);
        assertThat(claim.getId()).isEqualTo(1);
    }
}
