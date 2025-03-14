package org.emes;

import java.time.Clock;
import java.time.LocalDate;

public class TodoService {

  private final TodoDao dao;
  private final DueParser dueParser;
  private final RecurringParser recurringParser;
  private final Clock clock;

  public TodoService(TodoDao dao, DueParser dueParser, RecurringParser recurringParser,
      Clock clock) {
    this.dao = dao;
    this.dueParser = dueParser;
    this.recurringParser = recurringParser;
    this.clock = clock;
  }

  public void runMainLoop() {
    dao.init();

    while (true) {
      var todos = dao.findAllOrderByDueDesc();
      //TODO show
      //TODO read line
      var command = "";
      if (command.startsWith("create")) {
        var due = dueParser.parse(LocalDate.now(clock), command);
        var recurring = recurringParser.parse(command);
        var title = ""; //TODO
        //TODO save
      } else if (command.startsWith("complete")) {
        var id = 1;
        var todo = todos.stream().filter(it -> it.id().equals(id)).findFirst().get();
        if (todo.recurring() != null) {
          dao.delete(todo.id());
        } else {
          var due = todo.recurring().nextOccurrence(LocalDate.now(clock));
          dao.updateDue(todo.id(), due);
        }
        //TODO save
      } else {
        break;
      }
    }

    dao.close();
  }
}
