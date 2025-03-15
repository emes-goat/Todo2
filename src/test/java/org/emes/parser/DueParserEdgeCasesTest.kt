package org.emes.parser

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import org.emes.TestData
import org.emes.now
import org.emes.todo
import java.time.Clock
import java.time.ZoneOffset
import kotlin.test.assertEquals

class DueParserEdgeCasesTest : FunSpec({
    withData(
        TestData(now, "", null),
        TestData(now, "shopping in 2 days in 3 days", todo("shopping in 2 days", "2025-03-04")),
    ) {
        val newTodoParser = NewTodoParser(
            Clock.fixed(it.now.atStartOfDay().toInstant(ZoneOffset.UTC), ZoneOffset.UTC)
        )
        val actual = newTodoParser.parse(it.command)
        assertEquals(it.expected, actual)
    }
})
