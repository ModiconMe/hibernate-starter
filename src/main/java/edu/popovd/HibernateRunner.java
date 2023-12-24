package edu.popovd;

import edu.popovd.entity.Payment;
import edu.popovd.entity.User;
import edu.popovd.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class HibernateRunner {

    private static final Logger log = LoggerFactory.getLogger(HibernateRunner.class);

    public static void main(String[] args) throws SQLException {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();) {

            User user = null;
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                user = session.find(User.class, 1L);
                User user1 = session.find(User.class, 1L);
                user.getCompany().getName();
                user.getUserChats().size();

                List<Payment> payments = session.createQuery("select p from Payment p where p.receiver.id = :userId", Payment.class)
                        .setParameter("userId", 1L)
                        .setCacheable(true)
//                        .setCacheRegion("")
                        .getResultList();

                System.out.println(sessionFactory.getStatistics().getCacheRegionStatistics("Users"));

                session.getTransaction().commit();
            }
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                User user2 = session.find(User.class, 1L);
                user2.getCompany().getName();
                user2.getUserChats().size();


                List<Payment> payments = session.createQuery("select p from Payment p where p.receiver.id = :userId", Payment.class)
                        .setParameter("userId", 1L)
                        .setCacheable(true)
                        .getResultList();

                System.out.println(sessionFactory.getStatistics().getCacheRegionStatistics("Users"));

                session.getTransaction().commit();
            }
        }
    }
}
