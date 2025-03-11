package org.emes;

import java.time.LocalDate;
import java.util.List;

public class Main {

  public static void main(String[] args) {
    var localDao = new LocalDao();
    localDao.init();

    localDao.save(new Todo("Hello", LocalDate.now()));

    List<Todo> allOrderedByDueDec = localDao.findAllOrderByDueDesc();
    System.out.printf(String.valueOf(allOrderedByDueDec.size()));
    localDao.close();
  }
}