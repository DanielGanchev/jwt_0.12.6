package net.dodo.jwtimplementation.features.authentication.model;


import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity(name = "users")
public class AuthenticationUser {

  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  @Email(message = "Invalid email")
  @NotNull
  private String email;

  @JsonIgnore
  private String password;

  public AuthenticationUser() {
  }

  public AuthenticationUser(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public AuthenticationUser setId(Long id) {
    this.id = id;
    return this;
  }

  public AuthenticationUser setEmail(String email) {
    this.email = email;
    return this;
  }

  public AuthenticationUser setPassword(String password) {
    this.password = password;
    return this;
  }
}
