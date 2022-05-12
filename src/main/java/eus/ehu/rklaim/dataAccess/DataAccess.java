package eus.ehu.rklaim.dataAccess;

import eus.ehu.rklaim.configuration.ConfigXML;
import eus.ehu.rklaim.configuration.UtilDate;
import eus.ehu.rklaim.domain.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Implements the Data Access utility to the objectDb database
 */
public class DataAccess {

  protected EntityManager db;
  protected EntityManagerFactory emf;

  ConfigXML config = ConfigXML.getInstance();

  public DataAccess(boolean initializeMode) {
    System.out.println("Creating DataAccess instance => isDatabaseLocal: " +
        config.isDataAccessLocal() + " getDatabBaseOpenMode: " + config.getDataBaseOpenMode());
    open(initializeMode);

    if (initializeMode) {
      initializeDB();
    }
  }

  public DataAccess() {
    this(false);
  }


  /**
   * This method initializes the database with some trial events and questions.
   * It is invoked by the business logic when the option "initialize" is used
   * in the tag dataBaseOpenMode of resources/config.xml file
   */
  public void initializeDB() {


    try {

      db.getTransaction().begin();

      generateTestingData();

      db.getTransaction().commit();
      System.out.println("The database has been initialized");


    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  private void generateTestingData() {
    Citizen oihane = new Citizen("123456N", "Oihane", "Albistur", "678012345", "oihane@ehu.eus");
    Citizen aitor = new Citizen("987654O", "Aitor", "Goikoetxea", "678999999", "aitor@ehu.eus");

    Officer inigo = new Officer("Iñigo", "Pérez", Officer.Category.HEADOFSERVICE, "inigo@perez.eus");
    Officer eva = new Officer("Eva", "García", Officer.Category.REGULAR, "eva@garcia.eus");


    db.persist(oihane);
    db.persist(aitor);
    db.persist(inigo);
    db.persist(eva);

    PublicService ps = new PublicService("Treasury Department", "Provincial Council of Gipuzkoa");
    db.persist(ps);

    Claim claim = new Claim(oihane,"The percentage applied to my earnings filing was too high", UtilDate.newDate(2022,05,03));
    claim.setOfficer(inigo);
    claim.setPublicService(ps);

    db.persist(claim);


  }


  public void open(boolean initializeMode) {

    System.out.println("Opening DataAccess instance => isDatabaseLocal: " +
        config.isDataAccessLocal() + " getDatabBaseOpenMode: " + config.getDataBaseOpenMode());

    String fileName = config.getDataBaseFilename();

    if (Files.exists(Paths.get(System.getProperty("user.home") + "/config/"))) {
      System.out.println("Opening db from user.home");
      fileName = System.getProperty("user.home") + "/config/" + fileName;
    }
    System.setProperty("objectdb.home", System.getProperty("user.home") + "/config"); // new $objectdb

    if (initializeMode) {
      fileName = fileName + ";drop";
      System.out.println("Deleting the DataBase");
    }

    if (config.isDataAccessLocal()) {
      emf = Persistence.createEntityManagerFactory("objectdb:" + fileName);
      db = emf.createEntityManager();
    } else {
      Map<String, String> properties = new HashMap<String, String>();
      properties.put("javax.persistence.jdbc.user", config.getDataBaseUser());
      properties.put("javax.persistence.jdbc.password", config.getDataBasePassword());

      emf = Persistence.createEntityManagerFactory("objectdb://" + config.getDataAccessNode() +
          ":" + config.getDataAccessPort() + "/" + fileName, properties);

      db = emf.createEntityManager();
    }
  }


  public void close() {
    db.close();
    System.out.println("DataBase is closed");
  }


  public static void main(String[] args) {

    DataAccess da = new DataAccess(true);

  }

  // -------- Rklaim
  public Claim getClaim(int claimId) {
    Claim claim = db.find(Claim.class, claimId);
    return claim;
  }

  public void createAccess(Claim claim, int officerId) {
    Officer officer = db.find(Officer.class, officerId);
    Access access = officer.createAccess(claim);
    db.persist(access);
    db.getTransaction().begin();
    claim.add(access);
    db.getTransaction().commit();
  }

  public Action addAction(int officerId, Claim claim, String description, Date time) {
    Action action = null;

    db.getTransaction().begin();
    Claim attachedClaim = db.find(Claim.class, claim.getId());
    Officer officer = db.find(Officer.class, officerId);
    Officer assignedOfficer = claim.getOfficer();
    if (officer.equals(assignedOfficer)) {
      action = attachedClaim.setAction(description, Calendar.getInstance().getTime());
      db.persist(action);
    }
    db.getTransaction().commit();
    return action;
  }

  public boolean setResolution(int officerId, Claim claim, Claim.Resolution resolution) {
    boolean ok = false;
    db.getTransaction().begin();
    Officer officer = db.find(Officer.class, officerId);
    Claim attachedClaim = db.find(Claim.class, claim.getId());
    Officer assignedOfficer = claim.getOfficer();
    if (officer.equals(assignedOfficer)) {
      attachedClaim.setResolution(resolution);
      ok = true;
    }
    db.getTransaction().commit();
    return ok;
  }
}
