package org.emes.parser

import java.time.DayOfWeek
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalUnit

fun convertToDayOfWeek(dayOfWeek: String): DayOfWeek {
    return when (dayOfWeek) {
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

fun convertToMonth(month: String): Int {
    return when (month) {
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

fun getUnit(unit: String): TemporalUnit {
    return when (unit) {
        "day" -> ChronoUnit.DAYS
        "week" -> ChronoUnit.WEEKS
        "month" -> ChronoUnit.MONTHS
        else -> error("Can't happen")
    }
}