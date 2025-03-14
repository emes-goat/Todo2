package org.emes;

import java.util.List;
import org.emes.dao.LocalDao;
import org.emes.dao.Todo;

public class Main {

  public static void main(String[] args) {
    var localDao = new LocalDao();
    localDao.init();

//    localDao.save(new Todo("Hello", LocalDate.now()));

    List<Todo> allOrderedByDueDec = localDao.findAllOrderByDueDesc();
    System.out.printf(String.valueOf(allOrderedByDueDec.size()));
    localDao.close();
  }
}