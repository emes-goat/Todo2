package org.emes.parser

import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters
import java.time.temporal.TemporalUnit

class DueParser {

    private val logger = KotlinLogging.logger {}

    private val inXUnit = Regex("\\bin (\\d{1,2}) (day|week|month)s?\\b")
    private val dayOfWeek = Regex("\\b(next )?(mon|tue|wed|thu|fri|sat|sun)\\b")
    private val todayTomorrow = Regex("\\b(tod|tom)\\b")
    private val date = Regex(
        "\\b(?i)(?:jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec)\\s+([1-9]|[12][0-9]|3[01])"
                + "(?:st|nd|rd|th)\\b|\\b([1-9]|[12][0-9]|3[01])(?:st|nd|rd|th)\\b"
    )

    private val patternWithHandlers = listOf<RegexWithHandler>(
        RegexWithHandler(inXUnit) { now, result -> handleInXUnit(now, result) },
        RegexWithHandler(dayOfWeek) { now, result -> handleDayOfWeek(now, result) },
        RegexWithHandler(todayTomorrow) { now, result -> handleTodayTomorrow(now, result) },
        RegexWithHandler(date) { now, result -> handleDate(now, result) },
    )

    fun parse(now: LocalDate, command: String): ParseResult<LocalDate>? {
        return patternWithHandlers
            .mapNotNull<RegexWithHandler, ParseResult<LocalDate>> { patternWithHandler ->
                patternWithHandler.regex.findAll(command).lastOrNull()?.let { match ->
                    var due = patternWithHandler.handler(now, match)
                    logger.info { "Use parser: ${patternWithHandler.regex.pattern}" }
                    return ParseResult(due, match.range)
                }
            }
            .maxByOrNull { it.range.start }
    }

    private fun handleDate(now: LocalDate, matchResult: MatchResult): LocalDate {
        return LocalDate.now()
    }

    private fun handleTodayTomorrow(now: LocalDate, matchResult: MatchResult): LocalDate {
        val day = matchResult.groupValues[1]
        return if (day == "tod") {
            now
        } else if (day == "tom") {
            now.plusDays(1)
        } else {
            error("Can't happen")
        }
    }

    private fun handleDayOfWeek(now: LocalDate, matchResult: MatchResult): LocalDate {
        val dayOfWeek = convertToDayOfWeek(matchResult.groupValues[2])
        val newDate = now.with(TemporalAdjusters.next(dayOfWeek))

        return if (matchResult.groupValues[1].isNotEmpty()) {
            newDate.plusWeeks(1)
        } else {
            newDate
        }
    }

    private fun convertToDayOfWeek(dayOfWeek: String): DayOfWeek {
        return when (dayOfWeek) {
            "mon" -> DayOfWeek.MONDAY
            "tue" -> DayOfWeek.TUESDAY
            "wed" -> DayOfWeek.WEDNESDAY
            "thu" -> DayOfWeek.THURSDAY
            "fri" -> DayOfWeek.FRIDAY
            "sat" -> DayOfWeek.SATURDAY
            "sun" -> DayOfWeek.SUNDAY
            else -> error("Can't happen")
        }
    }

    private fun handleInXUnit(now: LocalDate, matchResult: MatchResult): LocalDate {
        val amount = matchResult.groupValues[1].toInt()
        val unit = matchResult.groupValues[2]

        return now.plus(amount.toLong(), getUnit(unit))
    }

    private fun getUnit(unit: String): TemporalUnit {
        return when (unit) {
            "day" -> ChronoUnit.DAYS
            "week" -> ChronoUnit.WEEKS
            "month" -> ChronoUnit.MONTHS
            else -> error("Can't happen")
        }
    }

    private data class RegexWithHandler(
        val regex: Regex,
        val handler: (LocalDate, MatchResult) -> LocalDate
    )
}