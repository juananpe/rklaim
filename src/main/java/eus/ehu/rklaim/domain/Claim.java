package eus.ehu.rklaim.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
public class Claim {

  public Claim() {

  }

  enum Status {
    NOT_ASSIGNED, PROCESSING;
  }

  public enum Resolution {
    UPHELD, DENIED;
  }

  @Id @GeneratedValue
  private Long  number;
  private String description;
  private Date dateTimeClaimedEvent;
  private Date dateTimeFiling;
  private Status status;


  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
  private PublicService publicService;

  @Enumerated(EnumType.ORDINAL)
  private Resolution resolution;

  public Claim(Citizen citizen, String description, Date dateTimeClaimedEvent) {
    this.dateTimeFiling = Calendar.getInstance().getTime();
    this.citizen = citizen;
    this.description = description;
    this.dateTimeClaimedEvent = dateTimeClaimedEvent;
    this.status = Status.NOT_ASSIGNED;

  }

  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
  private Citizen citizen;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
  private List<Action> actions = new ArrayList<>();

  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
  private Officer officer;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
  private List<Access> accesses = new ArrayList<>();

  public PublicService getPublicService() {
    return publicService;
  }

  public Long getId() {
    return number;
  }

  public void setId(Long id) {
    this.number = id;
  }

  public void add(Access access) {
    accesses.add(access);
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Date getDateTimeClaimedEvent() {
    return dateTimeClaimedEvent;
  }

  public void setDateTimeClaimedEvent(Date dateTimeClaimedEvent) {
    this.dateTimeClaimedEvent = dateTimeClaimedEvent;
  }

  public Date getDateTimeFiling() {
    return dateTimeFiling;
  }

  public void setDateTimeFiling(Date dateTimeFiling) {
    this.dateTimeFiling = dateTimeFiling;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status state) {
    this.status = state;
  }

  public Citizen getCitizen() {
    return citizen;
  }

  public void setCitizen(Citizen citizen) {
    this.citizen = citizen;
  }

  public List<Action> getActions() {
    return actions;
  }

  public void setActions(List<Action> actions) {
    this.actions = actions;
  }

  public Officer getOfficer() {
    return officer;
  }

  public void setOfficer(Officer officer) {
    this.officer = officer;
    this.status = Status.PROCESSING;
  }

  public Action setAction(String description, Date time) {
    Action action = new Action(description, time, this);
    actions.add(action);
    return action;
  }

  public void setResolution(Resolution resolution) {
    this.resolution = resolution;
  }

  public void setPublicService(PublicService publicService) {
    this.publicService = publicService;
  }

  public Long getNumber() {
    return number;
  }

  public void setNumber(Long number) {
    this.number = number;
  }

  public Resolution getResolution() {
    return resolution;
  }

  public List<Access> getAccesses() {
    return accesses;
  }

  public void setAccesses(List<Access> accesses) {
    this.accesses = accesses;
  }

  @Override
  public String toString() {
    return "Claim{" +
        "number=" + number +
        ", description='" + description + '\'' +
        ", dateTimeClaimedEvent=" + dateTimeClaimedEvent +
        ", dateTimeFiling=" + dateTimeFiling +
        ", status=" + status +
        ", publicService=" + publicService +
        ", resolution=" + resolution +
        ", citizen=" + citizen +
        ", actions=" + actions +
        ", officer=" + officer +
        ", accesses=" + accesses +
        '}';
  }
}
