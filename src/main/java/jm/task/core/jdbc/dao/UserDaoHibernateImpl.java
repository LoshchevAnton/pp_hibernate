package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final SessionFactory factory = Util.getSessionFactory();
    private static final String CREATE_TABLE =
            """
            CREATE TABLE IF NOT EXISTS users (
            id INT AUTO_INCREMENT PRIMARY KEY,
            name VARCHAR(250) NOT NULL,
            lastName VARCHAR(250) NOT NULL,
            age INT NOT NULL)
            """;
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS users";
    private static final String DELETE_ALL = "DELETE FROM users";

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            Query query = session.createSQLQuery(CREATE_TABLE);
            query.executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            Query query = session.createSQLQuery(DROP_TABLE);
            query.executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            User user = new User();
            user.setId(id);
            session.delete(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            var result = session.createQuery("SELECT a from User a", User.class).list();
            session.getTransaction().commit();
            return result;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            Query query = session.createSQLQuery(DELETE_ALL);
            query.executeUpdate();
            session.getTransaction().commit();
        }
    }
}
