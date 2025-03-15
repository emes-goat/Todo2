package org.emes.parser

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import org.emes.TestData
import org.emes.date
import org.emes.now
import org.emes.todo
import java.time.Clock
import java.time.ZoneOffset
import kotlin.test.assertEquals

class DueParserInXUnitTest : FunSpec({
    withData(
        TestData(now, "shopping in 1 day", todo("shopping", "2025-03-02")),
        TestData(now, "shopping in 2 days", todo("shopping", "2025-03-03")),
        TestData(now, "shopping in 99 days", todo("shopping", "2025-06-08")),
        TestData(now, "shopping in 100 days", todo("shopping in 100 days", now)),
        TestData(now, "shopping in 1 week", todo("shopping", "2025-03-08")),
        TestData(now, "shopping in 2 weeks", todo("shopping", "2025-03-15")),
        TestData(now, "shopping in 99 weeks", todo("shopping", "2027-01-23")),
        TestData(now, "shopping in 100 weeks", todo("shopping in 100 weeks", now)),
        TestData(now, "shopping in 1 month", todo("shopping", "2025-04-01")),
        TestData(now, "shopping in 2 months", todo("shopping", "2025-05-01")),
        TestData(now, "shopping in 99 months", todo("shopping", "2033-06-01")),
        TestData(now, "shopping in 100 months", todo("shopping in 100 months", now)),

        TestData(date("2025-03-31"), "shopping in 1 month", todo("shopping", "2025-04-30")),
        TestData(date("2025-04-30"), "shopping in 1 month", todo("shopping", "2025-05-30")),
        TestData(date("2025-01-31"), "shopping in 1 month", todo("shopping", "2025-02-28")),

        TestData(now, "shoppingin 2 months", todo("shoppingin 2 months", now)),
        TestData(now, "shopping in2 months", todo("shopping in2 months", now)),
        TestData(now, "shopping in 2 monthss", todo("shopping in 2 monthss", now))
    ) {
        val newTodoParser = NewTodoParser(
            Clock.fixed(it.now.atStartOfDay().toInstant(ZoneOffset.UTC), ZoneOffset.UTC)
        )
        val actual = newTodoParser.parse(it.command)
        assertEquals(it.expected, actual)
    }
})
