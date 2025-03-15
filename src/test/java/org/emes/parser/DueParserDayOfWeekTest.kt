package org.emes.parser

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import org.emes.TestData
import org.emes.now
import org.emes.todo
import java.time.Clock
import java.time.ZoneOffset
import kotlin.test.assertEquals

class DueParserDayOfWeekTest : FunSpec({
    withData(
        TestData(now, "shopping sun", todo("shopping", "2025-03-02")),
        TestData(now, "shopping mon", todo("shopping", "2025-03-03")),
        TestData(now, "shopping tue", todo("shopping", "2025-03-04")),
        TestData(now, "shopping wed", todo("shopping", "2025-03-05")),
        TestData(now, "shopping thu", todo("shopping", "2025-03-06")),
        TestData(now, "shopping fri", todo("shopping", "2025-03-07")),
        TestData(now, "shopping sat", todo("shopping", "2025-03-08")),

        TestData(now, "shopping next sun", todo("shopping", "2025-03-09")),
        TestData(now, "shopping next mon", todo("shopping", "2025-03-10")),
        TestData(now, "shopping next tue", todo("shopping", "2025-03-11")),
        TestData(now, "shopping next wed", todo("shopping", "2025-03-12")),
        TestData(now, "shopping next thu", todo("shopping", "2025-03-13")),
        TestData(now, "shopping next fri", todo("shopping", "2025-03-14")),
        TestData(now, "shopping next sat", todo("shopping", "2025-03-15")),

        TestData(now, "shoppingsun", todo("shoppingsun", now)),
        TestData(now, "shopping nextsun", todo("shopping nextsun", now)),
        TestData(now, "shopping next sunn", todo("shopping next sunn", now)),
        TestData(now, "shopping sunn", todo("shopping sunn", now))
    )
    {
        val newTodoParser = NewTodoParser(
            Clock.fixed(it.now.atStartOfDay().toInstant(ZoneOffset.UTC), ZoneOffset.UTC)
        )
        val actual = newTodoParser.parse(it.command)
        assertEquals(it.expected, actual)
    }
})
