package org.emes;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;

@Getter
@Entity
public class Todo {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  @NotBlank
  private String title;
  @NotNull
  private LocalDate due;

  public Todo(String title, LocalDate due) {
    this.title = title;
    this.due = due;
  }

  public Todo() {
  }

}
