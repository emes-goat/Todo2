package org.emes;

import java.util.AbstractMap;
import java.util.List;
import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//create go shopping every thu //MODE EVERY WEEK
//create go shopping every 1st //MODE EVERY MONTH DAY
//create go shopping every 2 months //MODE EVERY X MONTHS - NEXT STARTING
//create go shopping every 3 weeks //MODE EVERY X WEEKS - NEXT STARTING
//create go shopping every 10 days // MODE EVERY X DAYS - NEXT STARTING
public class RecurringParser {

  private static final Logger LOG = LogManager.getLogger();

  private final static Pattern EVERY_DAY_OF_WEEK = Pattern.compile(
      "\\bevery (mon|tue|wed|thu|fri|sat|sun)\\b");
  private final static Pattern EVERY_DAY_OF_MONTH = Pattern.compile(
      "\\bevery (\\d)+ \\b"); //TODO
  private final static Pattern EVERY_X_DAYS = Pattern.compile("\\bevery (\\d) day(s)?\\b");
  private final static Pattern EVERY_X_WEEKS = Pattern.compile("\\bevery (\\d) week(s)?\\b");
  private final static Pattern EVERY_X_MONTHS = Pattern.compile("\\bevery (\\d) month(s)?\\b");

  private final List<PatternWithFunction<Function<MatchResult, Recurring>>> patterns = List.of(
      new PatternWithFunction<>(EVERY_DAY_OF_WEEK, this::handleEveryDayOfWeek),
      new PatternWithFunction<>(EVERY_DAY_OF_MONTH, this::handleEveryDayOfMonth),
      new PatternWithFunction<>(EVERY_X_DAYS, this::handleEveryXDays),
      new PatternWithFunction<>(EVERY_X_WEEKS, this::handleEveryXWeeks),
      new PatternWithFunction<>(EVERY_X_MONTHS, this::handleEveryXMonths)
  );

  public ParseResult<Recurring> parse(String command) {
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
          Recurring date = entry.getKey().function().apply(match);
          return new ParseResult<>(date, match.start(), match.end());
        })
        .orElse(null);
  }

  private Recurring handleEveryDayOfWeek(MatchResult matchResult) {
    return new Recurring(RecurringMode.EVERY_DAY_OF_WEEK, null, null, null, null);
  }

  private Recurring handleEveryDayOfMonth(MatchResult matchResult) {
    return new Recurring(RecurringMode.EVERY_DAY_OF_WEEK, null, null, null, null);
  }

  private Recurring handleEveryXDays(MatchResult matchResult) {
    return new Recurring(RecurringMode.EVERY_DAY_OF_WEEK, null, null, null, null);
  }

  private Recurring handleEveryXWeeks(MatchResult matchResult) {
    return new Recurring(RecurringMode.EVERY_DAY_OF_WEEK, null, null, null, null);
  }

  private Recurring handleEveryXMonths(MatchResult matchResult) {
    return new Recurring(RecurringMode.EVERY_DAY_OF_WEEK, null, null, null, null);
  }
}
