package org.emes

import java.time.LocalDate

fun date(date: String): LocalDate {
    return LocalDate.parse(date)
}

fun todo(title: String, date: String): Todo {
    return Todo(null, title, date(date), null)
}

fun todo(title: String, date: LocalDate): Todo {
    return Todo(null, title, date, null)
}

val now = date("2025-03-01")
