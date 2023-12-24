package edu.popovd;

import edu.popovd.entity.Payment;
import edu.popovd.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class HibernateRunner {

    private static final Logger log = LoggerFactory.getLogger(HibernateRunner.class);

    public static void main(String[] args) throws SQLException {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();) {

            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                Payment payment = session.find(Payment.class, 1L);
                payment.setAmount(payment.getAmount() + 10);

                session.getTransaction().commit();
            }

            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                AuditReader auditReader = AuditReaderFactory.get(session);
                Payment oldPayment = auditReader.find(Payment.class, 1L, 1L);
                System.out.println(oldPayment);

                auditReader.createQuery()
                        .forEntitiesAtRevision(Payment.class, 400L)
                        .add(AuditEntity.property("amount").ge(450))
                        .addProjection(AuditEntity.property("amount"))
                        .addProjection(AuditEntity.id())
                        .getResultList();

                session.getTransaction().commit();
            }

        }
    }
}
