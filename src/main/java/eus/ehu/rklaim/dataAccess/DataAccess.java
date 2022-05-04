package eus.ehu.rklaim.dataAccess;

import eus.ehu.rklaim.configuration.ConfigXML;
import eus.ehu.rklaim.configuration.UtilDate;
import eus.ehu.rklaim.domain.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
//
//  public void createInvoice(Member member, float total, int month, int year) {
//    db.getTransaction().begin();
//    member.createInvoice(total, month, year);
//    db.merge(member);
//    db.getTransaction().commit();
//  }


  public static void main(String[] args) {

    DataAccess da = new DataAccess(true);


//    String name = "Oihane";
//    TypedQuery<Member> query = da.db.createQuery(
//        "SELECT m FROM Member m WHERE m.name = ?1", Member.class);
//    Member oihane = query.setParameter(1, name).getSingleResult();
//    da.close();
//
//    BlFacadeImplementation.getInstance().createInvoice(oihane, 4, 2022);
//
//    List<Court> courts = BlFacadeImplementation.getInstance().getCourts();
//    List<Booking> books = BlFacadeImplementation.getInstance().getFreeBooks(courts.get(0), UtilDate.newDate(2022, 4, 27));
//
//    Booking book = books.get(0); // select the first free booking slot
//    BlFacadeImplementation.getInstance().setBook(name, book);  // the first free slot for court:0 date:2022/04/27 will be assigned to Oihane
//
//    System.out.println(oihane);

  }

//  public List<Court> getCourts() {
//    TypedQuery<Court> query = db.createQuery(
//        "SELECT c FROM Court c", Court.class);
//    return query.getResultList();
//  }
//
//
//  public void setBook(String name, Booking book) {
//    TypedQuery<Member> query = db.createQuery(
//        "SELECT m FROM Member m WHERE m.name = ?1", Member.class);
//    query.setParameter(1, name);
//    Member member = query.getSingleResult();
//
//    db.getTransaction().begin();
//    book.setBook(member);
//    db.getTransaction().commit();
//
//  }

  // -------- Rklaim
  public Claim getClaim(int claimId) {
    Claim claim = db.find(Claim.class, claimId);
    return claim;
  }

  public void createAccess(Claim claim, int officerId) {
    Officer officer = db.find(Officer.class, officerId);
    Access access = officer.createAccess(claim);
    claim.add(access);
  }

  public void addAction(int officerId, Claim claim, String description, Date time) {

    db.getTransaction().begin();
    Claim attachedClaim = db.find(Claim.class, claim.getId());
    Officer officer = db.find(Officer.class, officerId);
    Officer assignedOfficer = claim.getOfficer();
    if (officer.equals(assignedOfficer)) {
      Action action = attachedClaim.setAction(description, Calendar.getInstance().getTime());
      db.persist(action);
    }
    db.getTransaction().commit();
  }

  public void setResolution(int officerId, Claim claim, Claim.Resolution resolution) {
    db.getTransaction().begin();
    Officer officer = db.find(Officer.class, officerId);
    Claim attachedClaim = db.find(Claim.class, claim.getId());
    Officer assignedOfficer = claim.getOfficer();
    if (officer.equals(assignedOfficer)) {
      attachedClaim.setResolution(resolution);
    }
    db.getTransaction().commit();

  }
}
