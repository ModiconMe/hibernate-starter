package edu.popovd;

import edu.popovd.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

import java.sql.SQLException;
import java.time.LocalDate;

public class HibernateRunner {
    public static void main(String[] args) throws SQLException {
//        BlockingDeque<Connection> pool = null;
//        Connection connection = pool.take();
//        SessionFactory

//        DriverManager.getConnection(
//                "db.url",
//                "db.username",
//                "db.password");
//        Session

        Configuration configuration = new Configuration();
//        configuration.addAnnotatedClass(User.class);
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        configuration.configure();

        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()
        ) {
            session.beginTransaction();

            User user = User.builder()
                    .username("popovd")
                    .firstname("Dima")
                    .lastname("Popov")
                    .birthDate(LocalDate.of(1999, 7, 9))
                    .age(24)
                    .build();
            session.persist(user);

            session.getTransaction().commit();
        }
    }
}
