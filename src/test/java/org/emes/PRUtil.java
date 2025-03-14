package org.emes;

import java.time.LocalDate;

public class PRUtil {

  public static ParseResult<LocalDate> of(String date, int start, int end) {
    return new ParseResult<>(LocalDate.parse(date), start, end);
  }
}
