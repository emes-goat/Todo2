package org.emes;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalUnit;
import java.util.AbstractMap;
import java.util.List;
import java.util.function.BiFunction;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DueParser {

  private static final Logger LOG = LogManager.getLogger();

  private final static Pattern IN_X_UNIT = Pattern.compile("\\bin (\\d)+ (day|week|month)s?\\b");
  private final static Pattern DAY_OF_WEEK = Pattern.compile(
      "\\b(next )?(mon|tue|wed|thu|fri|sat|sun)\\b");
  private final static Pattern TODAY_TOMORROW = Pattern.compile("\\b(tod|tom)\\b");
  private final static Pattern DATE = Pattern.compile(
      "\\b(?i)(?:jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec)\\s+([1-9]|[12][0-9]|3[01])"
          + "(?:st|nd|rd|th)\\b|\\b([1-9]|[12][0-9]|3[01])(?:st|nd|rd|th)\\b"); //TODO implement this

  private final List<PatternWithFunction<BiFunction<LocalDate, MatchResult, LocalDate>>> patterns = List.of(
      new PatternWithFunction<>(IN_X_UNIT, this::handleInXUnit),
      new PatternWithFunction<>(DAY_OF_WEEK, this::handleDayOfWeek),
      new PatternWithFunction<>(TODAY_TOMORROW, this::handleTodayTomorrow),
      new PatternWithFunction<>(DATE, this::handleDate)
  );

  public ParseResult<LocalDate> parse(LocalDate now, String command) {
    return patterns.stream()
        .flatMap(patternWithAction -> {
          Matcher matcher = patternWithAction.pattern().matcher(command);
          return matcher.find()
              ? Stream.of(new AbstractMap.SimpleEntry<>(patternWithAction, matcher.toMatchResult()))
              : Stream.empty();
        })
        .findFirst()
        .map(entry -> {
          LOG.info("Use parser: {}", entry.getKey().pattern());
          MatchResult match = entry.getValue();
          LocalDate date = entry.getKey().function().apply(now, match);
          return new ParseResult<>(date, match.start(), match.end());
        })
        .orElse(null);
  }

  private LocalDate handleDate(LocalDate now, MatchResult matchResult) {
    return LocalDate.now();
  }

  private LocalDate handleTodayTomorrow(LocalDate now, MatchResult matchResult) {
    return LocalDate.now();
  }

  private LocalDate handleDayOfWeek(LocalDate now, MatchResult matchResult) {
    var dayOfWeek = convertToDayOfWeek(matchResult.group(2));
    var newDate = now.with(TemporalAdjusters.next(dayOfWeek));

    if (matchResult.group(1) != null) {
      return newDate.plusWeeks(1);
    } else {
      return newDate;
    }
  }

  private DayOfWeek convertToDayOfWeek(String dayOfWeek) {
    return switch (dayOfWeek) {
      case "mon" -> DayOfWeek.MONDAY;
      case "tue" -> DayOfWeek.TUESDAY;
      case "wed" -> DayOfWeek.WEDNESDAY;
      case "thu" -> DayOfWeek.THURSDAY;
      case "fri" -> DayOfWeek.FRIDAY;
      case "sat" -> DayOfWeek.SATURDAY;
      case "sun" -> DayOfWeek.SUNDAY;
      default -> throw new RuntimeException("Invalid day of week: " + dayOfWeek);
    };
  }

  private LocalDate handleInXUnit(LocalDate now, MatchResult matchResult) {
    var amount = Integer.parseInt(matchResult.group(1));
    var unit = matchResult.group(2);

    return now.plus(amount, getUnit(unit));
  }

  private TemporalUnit getUnit(String unit) {
    return switch (unit) {
      case "day" -> ChronoUnit.DAYS;
      case "week" -> ChronoUnit.WEEKS;
      case "month" -> ChronoUnit.MONTHS;
      default -> throw new RuntimeException("Invalid unit " + unit);
    };
  }
}
