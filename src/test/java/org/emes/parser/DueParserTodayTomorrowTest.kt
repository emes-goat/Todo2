package org.emes.parser

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import org.emes.TestData
import org.emes.now
import org.emes.todo
import java.time.Clock
import java.time.ZoneOffset
import kotlin.test.assertEquals

class DueParserTodayTomorrowTest : FunSpec({
    withData(
        TestData(now, "shopping tod", todo("shopping", now)),
        TestData(now, "shopping tom", todo("shopping", "2025-03-02")),

        TestData(now, "shoppingtod", todo("shoppingtod", now)),
        TestData(now, "shopping todd", todo("shopping todd", now)),
        TestData(now, "shoppingtom", todo("shoppingtom", now)),
        TestData(now, "shopping tomm", todo("shopping tomm", now))
    ) {
        val newTodoParser = NewTodoParser(
            Clock.fixed(it.now.atStartOfDay().toInstant(ZoneOffset.UTC), ZoneOffset.UTC)
        )
        val actual = newTodoParser.parse(it.command)
        assertEquals(it.expected, actual)
    }
})