package org.emes

import org.emes.parser.ParseResult
import java.time.LocalDate

object PRUtil {
    fun of(date: String, start: Int, end: Int): ParseResult<LocalDate?> {
        return ParseResult<LocalDate?>(LocalDate.parse(date), IntRange(start, end))
    }
}
