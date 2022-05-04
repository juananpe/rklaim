package eus.ehu.rklaim.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Action {
  @Id @GeneratedValue
  private Long id;

  private String description;
  private Date dateTime;

  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
  private Claim claim;

  public Action(String description, Date time, Claim claim) {
    this.description = description;
    this.dateTime = time;
    this.claim = claim;
  }

  public Action() {

  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Date getDateTime() {
    return dateTime;
  }

  public void setDateTime(Date dateTime) {
    this.dateTime = dateTime;
  }
}
