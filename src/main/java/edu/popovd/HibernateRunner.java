package edu.popovd;

import edu.popovd.entity.Payment;
import edu.popovd.entity.Profile;
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
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession();
             Session session1 = sessionFactory.openSession();
        ) {

//            session.doWork(connection -> connection.setAutoCommit(true));

//            TestDataImporter.importData(sessionFactory);

//                session.createQuery("select p from Payment p", Payment.class)
//                        .setLockMode(LockModeType.PESSIMISTIC_FORCE_INCREMENT)
//                        .setHint(AvailableSettings.JAKARTA_LOCK_TIMEOUT, 5000)
//                        .setReadOnly(true)
//                        .setHint(AvailableHints.HINT_READ_ONLY, true)
//                        .list();
//            session.setDefaultReadOnly(true);
//            session.beginTransaction();

            Payment payment1 = session.find(Payment.class, 1L);
            payment1.setAmount(payment1.getAmount() + 10); // не полетит запрос на обновление
//            session.persist(payment1);
//            session.flush();

            Profile profile = Profile.builder()
                    .user(session.find(User.class, 1L))
                    .language("ru")
                    .street("street")
                    .build();
            session.persist(profile);
//
//            session.getTransaction().commit();

        }
    }
}
