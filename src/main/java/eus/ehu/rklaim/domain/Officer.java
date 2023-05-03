package eus.ehu.rklaim.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Officer {


  public Officer() {

  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public List<Claim> getAssignedTo() {
    return assignedTo;
  }

  public void setAssignedTo(List<Claim> assignedTo) {
    this.assignedTo = assignedTo;
  }


  public enum Category {
    REGULAR, HEADOFSERVICE;
  }

  @Id @GeneratedValue
  private Long id;
  private String name;
  private String surname;
  private Category category;
  private String email;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
  private List<Claim> assignedTo = new ArrayList<>();

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
  private List<Access> accessList = new ArrayList<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Access createAccess(Claim claim){
    Access access = new Access(claim, this);
    accessList.add(access);
    return access;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Officer officer = (Officer) o;

    return id != null ? id.equals(officer.id) : officer.id == null;
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }


  public Officer(String name, String surname, Category category, String email) {
    this.name = name;
    this.surname = surname;
    this.category = category;
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

}
