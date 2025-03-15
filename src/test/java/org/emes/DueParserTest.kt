package org.emes

import org.emes.PRUtil.of
import org.emes.parser.DueParser
import org.emes.parser.ParseResult
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.FieldSource
import java.time.LocalDate

internal class DueParserTest {


    @ParameterizedTest(name = "Todo: {1}")
    @FieldSource("argumentsInXUnit")
    fun shouldReturnCorrectDueDateForInXUnit(
        now: LocalDate, todo: String,
        expected: ParseResult<LocalDate>?
    ) {
        val dueParser = DueParser()
        val actual = dueParser.parse(now, todo)
        Assertions.assertEquals(expected, actual)
    }

    @ParameterizedTest(name = "Todo: {1}")
    @FieldSource("argumentsDayOfWeek")
    fun shouldReturnCorrectDueDateForDayOfWeek(
        now: LocalDate, todo: String,
        expected: ParseResult<LocalDate>?
    ) {
        val dueParser = DueParser()
        val actual = dueParser.parse(now, todo)
        Assertions.assertEquals(expected, actual)
    } //create go shopping tom
    //create go shopping - explicit today
    //create go shopping tod - implicit today
    //create go shopping 27th
    //create go shopping june 27th
    //TODO implement

    companion object {
        private val NOW: LocalDate = LocalDate.parse("2025-03-01")

        private val argumentsInXUnit = listOf(
            Arguments.of(NOW, "shopping in 1 day", of("2025-03-02", 1, 1)),
            Arguments.of(NOW, "shopping in 2 days", of("2025-03-03", 1, 1)),
            Arguments.of(NOW, "shopping in 1 week", of("2025-03-08", 1, 1)),
            Arguments.of(NOW, "shopping in 2 weeks", of("2025-03-15", 1, 1)),
            Arguments.of(NOW, "shopping in 1 month", of("2025-04-01", 1, 1)),
            Arguments.of(NOW, "shopping in 2 months", of("2025-05-01", 1, 1)),

            Arguments.of(
                LocalDate.parse("2025-03-31"), "shopping in 1 month",
                of("2025-04-30", 1, 1)
            ),
            Arguments.of(
                LocalDate.parse("2025-04-30"), "shopping in 1 month",
                of("2025-05-30", 1, 1)
            ),
            Arguments.of(
                LocalDate.parse("2025-01-31"), "shopping in 1 month",
                of("2025-02-28", 1, 1)
            ),

            Arguments.of(NOW, "shoppingin 2 months", null),
            Arguments.of(NOW, "shopping in2 months", null),
            Arguments.of(NOW, "shopping in 2 monthss", null)
        )

        private val argumentsDayOfWeek = listOf(
            Arguments.of(NOW, "shopping sun", of("2025-03-02", 1, 1)),
            Arguments.of(NOW, "shopping mon", of("2025-03-03", 1, 1)),
            Arguments.of(NOW, "shopping tue", of("2025-03-04", 1, 1)),
            Arguments.of(NOW, "shopping wed", of("2025-03-05", 1, 1)),
            Arguments.of(NOW, "shopping thu", of("2025-03-06", 1, 1)),
            Arguments.of(NOW, "shopping fri", of("2025-03-07", 1, 1)),
            Arguments.of(NOW, "shopping sat", of("2025-03-08", 1, 1)),

            Arguments.of(NOW, "shopping next sun", of("2025-03-09", 1, 1)),
            Arguments.of(NOW, "shopping next mon", of("2025-03-10", 1, 1)),
            Arguments.of(NOW, "shopping next tue", of("2025-03-11", 1, 1)),
            Arguments.of(NOW, "shopping next wed", of("2025-03-12", 1, 1)),
            Arguments.of(NOW, "shopping next thu", of("2025-03-13", 1, 1)),
            Arguments.of(NOW, "shopping next fri", of("2025-03-14", 1, 1)),
            Arguments.of(NOW, "shopping next sat", of("2025-03-15", 1, 1)),

            Arguments.of(NOW, "shoppingsun", null),
            Arguments.of(NOW, "shopping nextsun", null),
            Arguments.of(NOW, "shopping next sunn", null),
            Arguments.of(NOW, "shopping sunn", null)
        )
    }
}