package edu.popovd;

import edu.popovd.entity.Birthday;
import edu.popovd.entity.PersonalInfo;
import edu.popovd.entity.User;
import edu.popovd.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.time.LocalDate;

public class HibernateRunner {

    private static final Logger log = LoggerFactory.getLogger(HibernateRunner.class);

    public static void main(String[] args) throws SQLException {
        User user1 = User.builder()
                .username("ivan119@gmail.com")
                .personalInfo(new PersonalInfo(
                        "Ivan", "Ivanov", new Birthday(LocalDate.now())
                ))
                .build();
        log.info("User entity is in transient state, object: {}", user1);


        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()
        ) {
            Session session1 = sessionFactory.openSession();
            try (session1) {
                Transaction transaction = session1.beginTransaction();
                log.trace("Transaction is created, {}", transaction);

                session1.persist(user1);
                log.trace("User is in persist state: {}, session: {}", user1, session1);

                session1.getTransaction().commit();
            }

            log.warn("User is in detached state: {}, session is closed {}", user1, session1);
        } catch (Exception e) {
            log.error("Exception occurred ", e);
            throw e;
        }
    }
}
