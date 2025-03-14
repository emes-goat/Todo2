package org.emes;

import java.time.DayOfWeek;
import java.time.LocalDate;

public record Recurring(
    RecurringMode mode,
    DayOfWeek dayOfWeek,
    Integer dayOfMonth,
    Integer interval,
    RecurringIntervalUnit recurringIntervalUnit
) {

  public LocalDate nextOccurrence(LocalDate now) {
    return switch (mode) {
      case EVERY_DAY_OF_WEEK -> now;
      case EVERY_DAY_OF_MONTH -> now;
      case EVERY_X_DAYS -> now;
      case EVERY_X_WEEKS -> now;
      case EVERY_X_MONTHS -> now;
    };
  }
}
