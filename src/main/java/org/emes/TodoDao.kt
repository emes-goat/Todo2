package org.emes

import java.time.LocalDate

interface TodoDao {
    fun save(todo: Todo)

    fun findAllOrderByDueDesc(): MutableList<Todo>

    fun updateDue(id: Int, due: LocalDate)

    fun delete(id: Int)

    fun init()

    fun close()
}
