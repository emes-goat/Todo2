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

class DueParserDateTest : FunSpec({
    withData(
        TestData(now, "shopping 2st", todo("shopping", "2025-03-02")),
        TestData(now, "shopping 2nd", todo("shopping", "2025-03-02")),
        TestData(now, "shopping 2rd", todo("shopping", "2025-03-02")),
        TestData(now, "shopping 2th", todo("shopping", "2025-03-02")),
        TestData(now, "shopping 31st", todo("shopping", "2025-03-31")),

        TestData(date("2025-03-15"), "shopping 14st", todo("shopping", "2025-04-14")),
        TestData(date("2025-03-15"), "shopping 15st", todo("shopping", "2025-04-15")),
        TestData(date("2025-03-15"), "shopping 16st", todo("shopping", "2025-03-16")),

        TestData(now, "shopping jan 1st", todo("shopping", "2026-01-01")),
        TestData(now, "shopping feb 1st", todo("shopping", "2026-02-01")),
        TestData(now, "shopping mar 1st", todo("shopping", "2026-03-01")),
        TestData(now, "shopping apr 1st", todo("shopping", "2025-04-01")),
        TestData(now, "shopping may 1st", todo("shopping", "2025-05-01")),
        TestData(now, "shopping jun 1st", todo("shopping", "2025-06-01")),
        TestData(now, "shopping jul 1st", todo("shopping", "2025-07-01")),
        TestData(now, "shopping aug 1st", todo("shopping", "2025-08-01")),
        TestData(now, "shopping sep 1st", todo("shopping", "2025-09-01")),
        TestData(now, "shopping oct 1st", todo("shopping", "2025-10-01")),
        TestData(now, "shopping nov 1st", todo("shopping", "2025-11-01")),
        TestData(now, "shopping dec 1st", todo("shopping", "2025-12-01")),

        TestData(date("2025-02-28"), "shopping 31st", todo("shopping", "2025-03-31")),
        TestData(date("2025-04-30"), "shopping 31st", todo("shopping", "2025-05-31")),

        TestData(now, "shopping 2stt", todo("shopping 2stt", now)),
        TestData(now, "shopping2st", todo("shopping2st", now)),
        TestData(now, "shopping 32st", todo("shopping 32st", now)),
        TestData(now, "shopping -1st", todo("shopping -1st", now)),
    )
    {
        val newTodoParser = NewTodoParser(
            Clock.fixed(it.now.atStartOfDay().toInstant(ZoneOffset.UTC), ZoneOffset.UTC)
        )
        val actual = newTodoParser.parse(it.command)
        assertEquals(it.expected, actual)
    }
})
