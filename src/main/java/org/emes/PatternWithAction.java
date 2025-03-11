package org.emes;

import java.time.LocalDate;
import java.util.function.BiFunction;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public record PatternWithAction(Pattern pattern,
                                BiFunction<LocalDate, MatchResult, LocalDate> action) {

  public boolean find(String text) {
    var matcher = pattern.matcher(text);
    return matcher.find();
  }
}
