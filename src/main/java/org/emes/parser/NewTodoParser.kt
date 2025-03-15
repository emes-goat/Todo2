package org.emes.parser

import io.github.oshai.kotlinlogging.KotlinLogging
import org.emes.Todo
import java.time.Clock
import java.time.LocalDate

class NewTodoParser(private val clock: Clock) {
    private val logger = KotlinLogging.logger {}
    private val dueParser = DueParser()
    private val recurringParser = RecurringParser()

    fun parse(command: String): Todo? {
        logger.info { "Parse command: $command" }
        if (command.isBlank()) {
            return null
        }

        val now = LocalDate.now(clock)

        var dueParsed = dueParser.parse(now, command)
        if (dueParsed == null) {
            logger.info { "No due regex match. Implicit today" }
        }

        val recurringParsed = recurringParser.parse(command)
        if (recurringParsed == null) {
            logger.info { "No recurring regex match" }
        }

        val title = removeMatched(
            command = command,
            dueRange = dueParsed?.range ?: IntRange.EMPTY,
            recurringRange = recurringParsed?.range ?: IntRange.EMPTY
        )

        return Todo(
            id = null,
            title = title,
            due = dueParsed?.result ?: now,
            recurring = recurringParsed?.result
        )
    }

    private fun removeMatched(
        command: String, dueRange: IntRange, recurringRange: IntRange
    ): String {
        return command.filterIndexed { i, _ -> i !in dueRange && i !in recurringRange }.trim()
    }
}