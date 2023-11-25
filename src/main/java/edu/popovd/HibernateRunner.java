package edu.popovd;

import edu.popovd.entity.User;
import edu.popovd.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class HibernateRunner {

    private static final Logger log = LoggerFactory.getLogger(HibernateRunner.class);

    public static void main(String[] args) throws SQLException {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            Session session1 = sessionFactory.openSession();
            try (session1) {
                session1.beginTransaction();

                User user = session1.get(User.class, 1L);
                session1.evict(user);

                session1.getTransaction().commit();
            }

        } catch (Exception e) {
            throw e;
        }
    }
}
