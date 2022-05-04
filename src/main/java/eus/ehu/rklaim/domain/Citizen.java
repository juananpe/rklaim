package eus.ehu.rklaim.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Citizen {
  @Id
  private String dni;
  private String name;
  private String surname;
  private String phone;
  private String email;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
  private List<Claim> files = new ArrayList<>();

  // new Citizen("123456N", "Oihane", "Albistur", "678012345");
  public Citizen(String dni, String name, String surname, String phone, String email) {
    this.dni = dni;
    this.name = name;
    this.surname = surname;
    this.phone = phone;
    this.email = email;
  }

  public String getDni() {
    return dni;
  }

  public void setDni(String dni) {
    this.dni = dni;
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

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public List<Claim> getFiles() {
    return files;
  }

  public void setFiles(List<Claim> files) {
    this.files = files;
  }
}
