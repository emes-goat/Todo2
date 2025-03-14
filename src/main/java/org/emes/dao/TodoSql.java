package org.emes.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@NoArgsConstructor
class TodoSql {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  @NotBlank
  private String title;
  @NotNull
  private LocalDate due;

  private String recurringMode;
  private Integer recurringDayOfWeek;
  private Integer recurringDayOfMonth;
  private Integer recurringInterval;
  private String recurringIntervalUnit;
}
