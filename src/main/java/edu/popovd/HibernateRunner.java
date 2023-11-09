package edu.popovd;

import edu.popovd.entity.Company;
import edu.popovd.entity.User;
import edu.popovd.util.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class HibernateRunner {

    private static final Logger log = LoggerFactory.getLogger(HibernateRunner.class);

    public static void main(String[] args) throws SQLException {
//        Company smartix = Company.builder().name("Smartix").build();

//        User user1 = User.builder()
//                .username("ivan" + System.currentTimeMillis() + "@gmail.com")
//                .personalInfo(new PersonalInfo(
//                        "Ivan", "Ivanov", new Birthday(LocalDate.now())
//                ))
//                .password("secuuure")
//                .company(smartix)
//                .build();
        User user;
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            Session session1 = sessionFactory.openSession();
            try (session1) {
                session1.beginTransaction();

                user = session1.get(User.class, 1L);
                Company company = user.getCompany();
                String name = company.getName();
//                session1.persist(smartix);
//                session1.persist(user1);

                Company unproxy = Hibernate.unproxy(company, Company.class);

                session1.getTransaction().commit();
            }

        } catch (Exception e) {
            throw e;
        }
    }
}
