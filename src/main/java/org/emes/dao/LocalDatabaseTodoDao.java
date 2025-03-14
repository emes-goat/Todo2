package org.emes.dao;

import static org.hibernate.cfg.JdbcSettings.FORMAT_SQL;
import static org.hibernate.cfg.JdbcSettings.HIGHLIGHT_SQL;
import static org.hibernate.cfg.JdbcSettings.JAKARTA_JDBC_PASSWORD;
import static org.hibernate.cfg.JdbcSettings.JAKARTA_JDBC_URL;
import static org.hibernate.cfg.JdbcSettings.JAKARTA_JDBC_USER;
import static org.hibernate.cfg.JdbcSettings.SHOW_SQL;
import static org.hibernate.cfg.SchemaToolingSettings.JAKARTA_HBM2DDL_DATABASE_ACTION;

import java.util.List;
import lombok.SneakyThrows;
import org.emes.Todo;
import org.emes.TodoDao;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class LocalDatabaseTodoDao implements TodoDao {

  private SessionFactory sessionFactory;

  @SneakyThrows
  public void save(Todo todoSql) {
    sessionFactory.inTransaction(session -> session.persist(todoSql));
  }

  @SneakyThrows
  public List<Todo> findAllOrderByDueDesc() {
//    return sessionFactory.fromTransaction(
//        session -> session.createQuery("FROM TodoSql ORDER BY due DESC", TodoSql.class).list());
    return List.of();
  }

  @SneakyThrows
  public void delete(Integer id) {
//    sessionFactory.inTransaction(session -> session.remove(todoSql));
  }

  @SneakyThrows
  public void init() {
    sessionFactory = new Configuration()
        .addAnnotatedClass(TodoSql.class)
        .setProperty(JAKARTA_JDBC_URL, "jdbc:hsqldb:file:./db/db.file")
        .setProperty(JAKARTA_JDBC_USER, "sa")
        .setProperty(JAKARTA_JDBC_PASSWORD, "")
        .setProperty(JAKARTA_HBM2DDL_DATABASE_ACTION, "update")
        .setProperty("hibernate.agroal.maxSize", 1)
        .setProperty(SHOW_SQL, true)
        .setProperty(FORMAT_SQL, true)
        .setProperty(HIGHLIGHT_SQL, true)
        .buildSessionFactory();
  }

  @SneakyThrows
  public void close() {
    sessionFactory.close();
  }
}
