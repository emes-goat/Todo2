package org.emes;

import java.time.LocalDate;

public record Todo(
    Integer id,
    String title,
    LocalDate due,
    Recurring recurring
) {

}
