package org.emes.parser

import io.github.oshai.kotlinlogging.KotlinLogging
import org.emes.Recurring
import org.emes.RecurringMode

class RecurringParser {

    private val logger = KotlinLogging.logger {}

    private val everyDayOfWeek = Regex("\\bevery ${daysOfWeekGroup}\\b")
    private val everyDayOfMonth = Regex("\\bevery ${zeroToThirtyOneGroup}$ordinalNumbers\\b")
    private val everyXUnit = Regex("\\bevery $zeroToNinetyNineGroup $unitsGroup\\b")

    private val patternWithHandlers = listOf<RegexWithHandler>(
        RegexWithHandler(everyDayOfWeek) { result -> handleEveryDayOfWeek(result) },
        RegexWithHandler(everyDayOfMonth) { result -> handleEveryDayOfMonth(result) },
        RegexWithHandler(everyXUnit) { result -> handleEveryXUnit(result) },
    )

    fun parse(command: String): ParseResult<Recurring>? {
        return patternWithHandlers
            .mapNotNull<RegexWithHandler, ParseResult<Recurring>> { patternWithHandler ->
                patternWithHandler.regex.findAll(command).lastOrNull()?.let { match ->
                    var recurring = patternWithHandler.handler(match)
                    logger.info { "Use parser: ${patternWithHandler.regex.pattern}" }
                    return ParseResult(recurring, match.range)
                }
            }
            .maxByOrNull { it.range.start }
    }

    private fun handleEveryDayOfWeek(matchResult: MatchResult): Recurring {
        val dayOfWeek = matchResult.groupValues[1].toDayOfWeek()
        return Recurring(RecurringMode.EVERY_DAY_OF_WEEK, dayOfWeek = dayOfWeek)
    }

    private fun handleEveryDayOfMonth(matchResult: MatchResult): Recurring {
        val dayOfMonth = matchResult.groupValues[1].toInt()
        return Recurring(RecurringMode.EVERY_DAY_OF_MONTH, dayOfMonth = dayOfMonth)
    }

    private fun handleEveryXUnit(matchResult: MatchResult): Recurring {
        val interval = matchResult.groupValues[1].toInt()
        val unit = matchResult.groupValues[2]

        return Recurring(
            RecurringMode.EVERY_DAY_OF_WEEK,
            interval = interval,
            intervalUnit = unit
        )
    }

    private data class RegexWithHandler(
        val regex: Regex,
        val handler: (MatchResult) -> Recurring
    )
}