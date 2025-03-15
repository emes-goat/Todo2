package org.emes

import java.time.LocalDate

data class Todo(
    val id: Int?,
    val title: String,
    val due: LocalDate,
    val recurring: Recurring?
)
