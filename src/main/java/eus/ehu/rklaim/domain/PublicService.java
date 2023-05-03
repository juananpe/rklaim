package eus.ehu.rklaim.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class PublicService {
  @Id @GeneratedValue
  private Long id;

  private String department;
  private String institution;
  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
  private List<Claim> claims = new ArrayList<>();

  public PublicService() {

  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public PublicService(String department, String institution) {
    this.department = department;
    this.institution = institution;
  }
}
