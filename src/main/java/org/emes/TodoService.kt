package org.emes

import java.time.Clock
import java.time.LocalDate

class TodoService(
    private val dao: TodoDao,
    private val clock: Clock
) {
    fun runMainLoop() {
        dao.init()

        while (true) {
            val todos = dao.findAllOrderByDueDesc()
            //TODO show
            //TODO read line
            val command = ""
            if (command.startsWith("create")) {
                //TODO save
            } else if (command.startsWith("complete")) {
                val id = 1
                val todo = todos.firstOrNull { it: Todo? -> it!!.id == id }
                if (todo?.recurring != null) {
                    dao.delete(todo.id!!)
                } else {
//                    val due = todo?.recurring?.first(LocalDate.now(clock))
//                    dao.updateDue(todo.id!!, due)
                }
                //TODO save
            } else {
                break
            }
        }

        dao.close()
    }

    @JvmRecord
    private data class Substring(val start: Int, val end: Int)
}
