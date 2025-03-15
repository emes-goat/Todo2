package org.emes.parser

import io.github.oshai.kotlinlogging.KotlinLogging
import org.emes.parser.ParseResult
import org.emes.Recurring
import org.emes.RecurringMode

class RecurringParser {

    private val logger = KotlinLogging.logger {}

    private val everyDayOfWeek = Regex("\\bin (\\d)+ (day|week|month)s?\\b")
    private val everyDayOfMonth = Regex("\\bin (\\d)+ (day|week|month)s?\\b")
    private val everyXDays = Regex("\\bin (\\d)+ (day|week|month)s?\\b")
    private val everyXWeeks = Regex("\\bin (\\d)+ (day|week|month)s?\\b")
    private val everyXMonths = Regex("\\bin (\\d)+ (day|week|month)s?\\b")

    private val patternWithHandlers = listOf<RegexWithHandler>(
        RegexWithHandler(everyDayOfWeek) { result -> handleEveryDayOfWeek(result) },
        RegexWithHandler(everyDayOfMonth) { result -> handleEveryDayOfMonth(result) },
        RegexWithHandler(everyXDays) { result -> handleEveryXDays(result) },
        RegexWithHandler(everyXWeeks) { result -> handleEveryXWeeks(result) },
        RegexWithHandler(everyXMonths) { result -> handleEveryXMonths(result) }
    )

    fun parse(command: String): ParseResult<Recurring>? {
        return patternWithHandlers
            .mapNotNull { patternWithHandler ->
                patternWithHandler.regex.findAll(command).lastOrNull()?.let { match ->
                    var recurring = patternWithHandler.handler(match)
                    logger.info { "Use parser: ${patternWithHandler.regex.pattern}" }
                    return ParseResult(recurring, match.range)
                }
            }
            .firstOrNull()
        //TODO handle case when multiple matches
    }

    private fun handleEveryDayOfWeek(matchResult: MatchResult): Recurring {
        return Recurring(RecurringMode.EVERY_DAY_OF_WEEK, null, null, null, null)
    }

    private fun handleEveryDayOfMonth(matchResult: MatchResult): Recurring {
        return Recurring(RecurringMode.EVERY_DAY_OF_WEEK, null, null, null, null)
    }

    private fun handleEveryXDays(matchResult: MatchResult): Recurring {
        return Recurring(RecurringMode.EVERY_DAY_OF_WEEK, null, null, null, null)
    }

    private fun handleEveryXWeeks(matchResult: MatchResult): Recurring {
        return Recurring(RecurringMode.EVERY_DAY_OF_WEEK, null, null, null, null)
    }

    private fun handleEveryXMonths(matchResult: MatchResult): Recurring {
        return Recurring(RecurringMode.EVERY_DAY_OF_WEEK, null, null, null, null)
    }

    private data class RegexWithHandler(
        val regex: Regex,
        val handler: (MatchResult) -> Recurring
    )
}