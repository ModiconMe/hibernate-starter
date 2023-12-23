package edu.popovd;

import edu.popovd.util.HibernateUtil;
import edu.popovd.util.TestDataImporter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class HibernateRunner {

    private static final Logger log = LoggerFactory.getLogger(HibernateRunner.class);

    public static void main(String[] args) throws SQLException {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory
                     .withOptions()
                     .openSession();
             Session session1 = sessionFactory.openSession();
        ) {

//            session.doWork(connection -> connection.setAutoCommit(true));


            session.beginTransaction();
            TestDataImporter.importData(sessionFactory);


//            User user = session.find(User.class, 1L);
//
//            Payment payment = Payment.builder()
//                    .amount(1000)
//                    .receiver(user)
//                    .build();
//            session.persist(payment);

            session.getTransaction().commit();

        }
    }
}
