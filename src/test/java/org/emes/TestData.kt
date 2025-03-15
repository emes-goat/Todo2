package org.emes

import java.time.LocalDate

data class TestData(
    val now: LocalDate,
    val command: String,
    val expected: Todo?
)