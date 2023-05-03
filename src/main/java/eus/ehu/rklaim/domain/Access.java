package eus.ehu.rklaim.domain;

import jakarta.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
public class Access {
  @Id  @GeneratedValue
  private Long id;

  private Date dateTime;

  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
  private Claim toWhat;

  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
  private Officer who;

  public Access() {

  }

  public Date getDateTime() {
    return dateTime;
  }

  public void setDateTime(Date dateTime) {
    this.dateTime = dateTime;
  }

  public Claim getToWhat() {
    return toWhat;
  }

  public void setToWhat(Claim toWhat) {
    this.toWhat = toWhat;
  }

  public Officer getWho() {
    return who;
  }

  public void setWho(Officer who) {
    this.who = who;
  }

  public Access(Claim claim, Officer officer) {
    this.who = officer;
    this.toWhat = claim;
    this.dateTime = Calendar.getInstance().getTime();

  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
