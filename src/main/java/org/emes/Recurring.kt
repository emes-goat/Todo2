package org.emes

import java.time.DayOfWeek
import java.time.LocalDate

data class Recurring(
    val mode: RecurringMode,
    val dayOfWeek: DayOfWeek? = null,
    val dayOfMonth: Int? = null,
    val interval: Int? = null,
    val intervalUnit: String? = null
) {
    fun nextOccurrence(now: LocalDate): LocalDate {
        return when (mode) {
            RecurringMode.EVERY_DAY_OF_WEEK -> now
            RecurringMode.EVERY_DAY_OF_MONTH -> now
            RecurringMode.EVERY_X_DAYS -> now
            RecurringMode.EVERY_X_WEEKS -> now
            RecurringMode.EVERY_X_MONTHS -> now
        }
    }
}
