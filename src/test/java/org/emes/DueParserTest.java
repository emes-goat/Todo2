package org.emes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.FieldSource;

class DueParserTest {

  private static final LocalDate NOW = LocalDate.parse("2025-03-01");

  private static final List<Arguments> argumentsInXUnit = List.of(
      Arguments.of(NOW, "shopping in 1 day", LocalDate.parse("2025-03-02")),
      Arguments.of(NOW, "shopping in 2 days", LocalDate.parse("2025-03-03")),
      Arguments.of(NOW, "shopping in 1 week", LocalDate.parse("2025-03-08")),
      Arguments.of(NOW, "shopping in 2 weeks", LocalDate.parse("2025-03-15")),
      Arguments.of(NOW, "shopping in 1 month", LocalDate.parse("2025-04-01")),
      Arguments.of(NOW, "shopping in 2 months", LocalDate.parse("2025-05-01")),

      Arguments.of(LocalDate.parse("2025-03-31"), "shopping in 1 month",
          LocalDate.parse("2025-04-30")),
      Arguments.of(LocalDate.parse("2025-04-30"), "shopping in 1 month",
          LocalDate.parse("2025-05-30")),
      Arguments.of(LocalDate.parse("2025-01-31"), "shopping in 1 month",
          LocalDate.parse("2025-02-28")),

      Arguments.of(NOW, "shoppingin 2 months", null),
      Arguments.of(NOW, "shopping in2 months", null),
      Arguments.of(NOW, "shopping in 2 monthss", null)
  );

  @ParameterizedTest(name = "Todo: {1}")
  @FieldSource("argumentsInXUnit")
  void shouldReturnCorrectDueDateForInXUnit(LocalDate now, String todo, LocalDate expected) {
    var dueParser = new DueParser();
    var actual = dueParser.parse(now, todo);
    assertEquals(expected, actual);
  }

  private static final List<Arguments> argumentsDayOfWeek = List.of(
      Arguments.of(NOW, "shopping sun", LocalDate.parse("2025-03-02")),
      Arguments.of(NOW, "shopping mon", LocalDate.parse("2025-03-03")),
      Arguments.of(NOW, "shopping tue", LocalDate.parse("2025-03-04")),
      Arguments.of(NOW, "shopping wed", LocalDate.parse("2025-03-05")),
      Arguments.of(NOW, "shopping thu", LocalDate.parse("2025-03-06")),
      Arguments.of(NOW, "shopping fri", LocalDate.parse("2025-03-07")),
      Arguments.of(NOW, "shopping sat", LocalDate.parse("2025-03-08")),

      Arguments.of(NOW, "shopping next sun", LocalDate.parse("2025-03-09")),
      Arguments.of(NOW, "shopping next mon", LocalDate.parse("2025-03-10")),
      Arguments.of(NOW, "shopping next tue", LocalDate.parse("2025-03-11")),
      Arguments.of(NOW, "shopping next wed", LocalDate.parse("2025-03-12")),
      Arguments.of(NOW, "shopping next thu", LocalDate.parse("2025-03-13")),
      Arguments.of(NOW, "shopping next fri", LocalDate.parse("2025-03-14")),
      Arguments.of(NOW, "shopping next sat", LocalDate.parse("2025-03-15")),

      Arguments.of(NOW, "shoppingsun", null),
      Arguments.of(NOW, "shopping nextsun", null),
      Arguments.of(NOW, "shopping next sunn", null),
      Arguments.of(NOW, "shopping sunn", null)
  );

  @ParameterizedTest(name = "Todo: {1}")
  @FieldSource("argumentsDayOfWeek")
  void shouldReturnCorrectDueDateForDayOfWeek(LocalDate now, String todo, LocalDate expected) {
    var dueParser = new DueParser();
    var actual = dueParser.parse(now, todo);
    assertEquals(expected, actual);
  }

  //create go shopping tom
  //create go shopping - explicit today
  //create go shopping tod - implicit today

  //create go shopping 27th
  //create go shopping june 27th

  //TODO implement
}