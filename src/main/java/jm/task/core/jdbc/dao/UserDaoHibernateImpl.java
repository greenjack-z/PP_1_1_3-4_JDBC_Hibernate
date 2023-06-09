package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.logging.Logger;

public class UserDaoHibernateImpl implements UserDao {

    private final Logger logger = Logger.getLogger(UserDaoHibernateImpl.class.getName());
    public UserDaoHibernateImpl() {
        //Empty constructor;
    }


    @Override
    public void createUsersTable() {
        String query = """
                    CREATE TABLE IF NOT EXISTS users (
                    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                    name VARCHAR(45) NOT NULL,
                    lastName VARCHAR(45) NOT NULL,
                    age TINYINT UNSIGNED NOT NULL
                );
                """;
        try (Session session = Util.getSession()) {
            session.beginTransaction();
            session.createNativeQuery(query).executeUpdate();
            session.getTransaction().commit();
            logger.info("Table created.");
        }
    }

    @Override
    public void dropUsersTable() {
        String query = "DROP TABLE IF EXISTS users";
        try (Session session = Util.getSession()) {
            session.beginTransaction();
            session.createNativeQuery(query).executeUpdate();
            session.getTransaction().commit();
            logger.info("Table deleted.");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try (Session session = Util.getSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            logger.info("User added.");
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSession()) {
            session.beginTransaction();
            session.delete(session.get(User.class, id));
            session.getTransaction().commit();
            logger.info("User deleted.");
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);
            CriteriaQuery<User> all = criteriaQuery.select(root);
            TypedQuery<User> typedQuery = session.createQuery(all);
            return typedQuery.getResultList();
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSession()) {
            String hql = "delete from User";
            Query<User> query = session.createQuery(hql);
            session.beginTransaction();
            query.executeUpdate();
            session.getTransaction().commit();
            logger.info("Table cleared.");
        }
    }
}
