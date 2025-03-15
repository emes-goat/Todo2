package org.emes;

import java.time.LocalDate;
import java.util.List;

public interface TodoDao {

  void save(Todo todo);

  List<Todo> findAllOrderByDueDesc();

  void updateDue(Integer id, LocalDate due);

  void delete(Integer id);

  void init();

  void close();
}
