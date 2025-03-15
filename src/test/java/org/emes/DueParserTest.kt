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
      Arguments.of(NOW, "shopping in 1 day", PRUtil.of("2025-03-02", 1, 1)),
      Arguments.of(NOW, "shopping in 2 days", PRUtil.of("2025-03-03", 1, 1)),
      Arguments.of(NOW, "shopping in 1 week", PRUtil.of("2025-03-08", 1, 1)),
      Arguments.of(NOW, "shopping in 2 weeks", PRUtil.of("2025-03-15", 1, 1)),
      Arguments.of(NOW, "shopping in 1 month", PRUtil.of("2025-04-01", 1, 1)),
      Arguments.of(NOW, "shopping in 2 months", PRUtil.of("2025-05-01", 1, 1)),

      Arguments.of(LocalDate.parse("2025-03-31"), "shopping in 1 month",
          PRUtil.of("2025-04-30", 1, 1)),
      Arguments.of(LocalDate.parse("2025-04-30"), "shopping in 1 month",
          PRUtil.of("2025-05-30", 1, 1)),
      Arguments.of(LocalDate.parse("2025-01-31"), "shopping in 1 month",
          PRUtil.of("2025-02-28", 1, 1)),

      Arguments.of(NOW, "shoppingin 2 months", null),
      Arguments.of(NOW, "shopping in2 months", null),
      Arguments.of(NOW, "shopping in 2 monthss", null)
  );

  @ParameterizedTest(name = "Todo: {1}")
  @FieldSource("argumentsInXUnit")
  void shouldReturnCorrectDueDateForInXUnit(LocalDate now, String todo,
      ParseResult<LocalDate> expected) {
    var dueParser = new DueParser();
    var actual = dueParser.parse(now, todo);
    assertEquals(expected, actual);
  }

  private static final List<Arguments> argumentsDayOfWeek = List.of(
      Arguments.of(NOW, "shopping sun", PRUtil.of("2025-03-02", 1, 1)),
      Arguments.of(NOW, "shopping mon", PRUtil.of("2025-03-03", 1, 1)),
      Arguments.of(NOW, "shopping tue", PRUtil.of("2025-03-04", 1, 1)),
      Arguments.of(NOW, "shopping wed", PRUtil.of("2025-03-05", 1, 1)),
      Arguments.of(NOW, "shopping thu", PRUtil.of("2025-03-06", 1, 1)),
      Arguments.of(NOW, "shopping fri", PRUtil.of("2025-03-07", 1, 1)),
      Arguments.of(NOW, "shopping sat", PRUtil.of("2025-03-08", 1, 1)),

      Arguments.of(NOW, "shopping next sun", PRUtil.of("2025-03-09", 1, 1)),
      Arguments.of(NOW, "shopping next mon", PRUtil.of("2025-03-10", 1, 1)),
      Arguments.of(NOW, "shopping next tue", PRUtil.of("2025-03-11", 1, 1)),
      Arguments.of(NOW, "shopping next wed", PRUtil.of("2025-03-12", 1, 1)),
      Arguments.of(NOW, "shopping next thu", PRUtil.of("2025-03-13", 1, 1)),
      Arguments.of(NOW, "shopping next fri", PRUtil.of("2025-03-14", 1, 1)),
      Arguments.of(NOW, "shopping next sat", PRUtil.of("2025-03-15", 1, 1)),

      Arguments.of(NOW, "shoppingsun", null),
      Arguments.of(NOW, "shopping nextsun", null),
      Arguments.of(NOW, "shopping next sunn", null),
      Arguments.of(NOW, "shopping sunn", null)
  );

  @ParameterizedTest(name = "Todo: {1}")
  @FieldSource("argumentsDayOfWeek")
  void shouldReturnCorrectDueDateForDayOfWeek(LocalDate now, String todo,
      ParseResult<LocalDate> expected) {
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