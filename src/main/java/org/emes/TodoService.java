package org.emes;

public class TodoService {

  private final LocalDao dao;

  public TodoService(LocalDao dao) {
    this.dao = dao;
  }

  public void runMainLoop() {
    dao.init();

    while (true) {
      //create task + recurring

      //create go shopping every thu
      //create go shopping every 1st
      //create go shopping every 2 months
      //create go shopping every 3 weeks
      //create go shopping every 10 days



      //complete/delete task
      //list tasks - all or just upcoming
    }

    dao.close();
  }
}
