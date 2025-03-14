package org.emes;

import java.util.List;
import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
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

  private final List<Pair<Pattern, Function<MatchResult, Recurring>>> patterns = List.of(
      new Pair<>(EVERY_DAY_OF_WEEK, this::handleEveryDayOfWeek),
      new Pair<>(EVERY_DAY_OF_MONTH, this::handleEveryDayOfMonth),
      new Pair<>(EVERY_X_DAYS, this::handleEveryXDays),
      new Pair<>(EVERY_X_WEEKS, this::handleEveryXWeeks),
      new Pair<>(EVERY_X_MONTHS, this::handleEveryXMonths)
  );

  public Recurring parse(String command) {
    return patterns.stream()
        .filter(it -> it.a().matcher(readLine).find())
        .findFirst()
        .map(it -> {
          LOG.info("Use parser: {}", it.a());
          var matchResult = it.a().matcher(readLine);
          matchResult.find();
          return it.b().apply(matchResult.toMatchResult());
        })
        .orElse(null);
  }

  private Recurring handleEveryDayOfWeek(MatchResult matchResult) {
    return new Recurring(RecurringMode.NONE, null, null, null, null);
  }

  private Recurring handleEveryDayOfMonth(MatchResult matchResult) {
    return new Recurring(RecurringMode.NONE, null, null, null, null);
  }

  private Recurring handleEveryXDays(MatchResult matchResult) {
    return new Recurring(RecurringMode.NONE, null, null, null, null);
  }

  private Recurring handleEveryXWeeks(MatchResult matchResult) {
    return new Recurring(RecurringMode.NONE, null, null, null, null);
  }

  private Recurring handleEveryXMonths(MatchResult matchResult) {
    return new Recurring(RecurringMode.NONE, null, null, null, null);
  }
}
