package org.emes;

import static org.hibernate.cfg.JdbcSettings.FORMAT_SQL;
import static org.hibernate.cfg.JdbcSettings.HIGHLIGHT_SQL;
import static org.hibernate.cfg.JdbcSettings.JAKARTA_JDBC_PASSWORD;
import static org.hibernate.cfg.JdbcSettings.JAKARTA_JDBC_URL;
import static org.hibernate.cfg.JdbcSettings.JAKARTA_JDBC_USER;
import static org.hibernate.cfg.JdbcSettings.SHOW_SQL;
import static org.hibernate.cfg.SchemaToolingSettings.JAKARTA_HBM2DDL_DATABASE_ACTION;

import java.util.List;
import lombok.SneakyThrows;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class LocalDao {

  private SessionFactory sessionFactory;

  @SneakyThrows
  public void save(Todo todo) {
    sessionFactory.inTransaction(session -> session.persist(todo));
  }

  @SneakyThrows
  public List<Todo> findAllOrderByDueDesc() {
    return sessionFactory.fromTransaction(
        session -> session.createQuery("FROM Todo ORDER BY due DESC", Todo.class).list());
  }

  @SneakyThrows
  public void delete(Todo todo) {
    sessionFactory.inTransaction(session -> session.remove(todo));
  }

  @SneakyThrows
  public void init() {
    sessionFactory = new Configuration()
        .addAnnotatedClass(Todo.class)
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
