package org.emes.parser

data class ParseResult<T>(
    val result: T,
    val range: IntRange
)