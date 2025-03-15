package org.emes.parser

import java.time.DayOfWeek
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalUnit

fun String.toDayOfWeek(): DayOfWeek {
    return when (this) {
        "mon" -> DayOfWeek.MONDAY
        "tue" -> DayOfWeek.TUESDAY
        "wed" -> DayOfWeek.WEDNESDAY
        "thu" -> DayOfWeek.THURSDAY
        "fri" -> DayOfWeek.FRIDAY
        "sat" -> DayOfWeek.SATURDAY
        "sun" -> DayOfWeek.SUNDAY
        else -> error("Can't happen")
    }
}

fun String.toMonthValue(): Int {
    return when (this) {
        "jan" -> 1
        "feb" -> 2
        "mar" -> 3
        "apr" -> 4
        "may" -> 5
        "jun" -> 6
        "jul" -> 7
        "aug" -> 8
        "sep" -> 9
        "oct" -> 10
        "nov" -> 11
        "dec" -> 12
        else -> error("Can't happen")
    }
}

fun String.toTemporalUnit(): TemporalUnit {
    return when (this) {
        "day" -> ChronoUnit.DAYS
        "week" -> ChronoUnit.WEEKS
        "month" -> ChronoUnit.MONTHS
        else -> error("Can't happen")
    }
}

const val zeroToNinetyNineGroup = "(\\d{1,2})"
const val daysOfWeekGroup = "(mon|tue|wed|thu|fri|sat|sun)"
const val zeroToThirtyOneGroup = "([1-9]|[12][0-9]|3[01])"
const val unitsGroup = "(day|week|month)s?"
const val ordinalNumbers = "(?:st|nd|rd|th)"