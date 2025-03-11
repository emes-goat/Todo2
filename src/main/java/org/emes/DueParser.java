package org.emes;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

public class DueParser {

  private final List<PatternWithAction> patterns = List.of(
      new PatternWithAction(
          Pattern.compile(" in ([1-99]) (day|week|month)s?"),
          (localDate, matchResult) -> {
            var amount = Integer.valueOf(matchResult.group(0));
            var unit = matchResult.group(1);
            return LocalDate.now();
          }
      )
  );

  //      //create go shopping in 2 months
  //      //create go shopping in 10 days
  //      //create go shopping in 3 weeks
  //      //create go shopping thu
  //      //create go shopping next thu
  //      //create go shopping tom
  //      //create go shopping - explicit today
  //      //create go shopping tod - implicit today
  //      //create go shopping 27th
  //      //create go shopping june 27th
  public LocalDate parse(LocalDate today, String todo) {
    var match = patterns.stream().filter(it -> it.find(todo)).findFirst().orElse(null);
    return LocalDate.now();
  }
}
