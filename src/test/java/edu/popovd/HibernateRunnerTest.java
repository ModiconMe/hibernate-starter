package edu.popovd;

import edu.popovd.builder.CompanyBuilder;
import edu.popovd.builder.UserBuilder;
import edu.popovd.entity.Company;
import edu.popovd.entity.User;
import edu.popovd.util.HibernateUtil;
import jakarta.persistence.FlushModeType;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jpa.AvailableHints;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

class HibernateRunnerTest {

    @Test
    void checkGetReflectionApi() throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = preparedStatement.getResultSet();
        resultSet.getString("username");
        resultSet.getString("lastname");

        Class<User> userClass = User.class;
        Constructor<User> constructor = userClass.getConstructor();
        User user = constructor.newInstance();
        Field usernameField = userClass.getDeclaredField("username");
        usernameField.setAccessible(true);
        usernameField.set(user, resultSet.getString("username"));
    }

//    @Test
//    void checkReflectionApi() throws SQLException, IllegalAccessException {
//        User user = User.builder()
//                .username("popovd")
//                .personalInfo(PersonalInfo.builder()
//                        .firstname("Dima")
//                        .lastname("popov")
//                        .birthDate(new Birthday(LocalDate.of(1999, 7, 9)))
//                        .build())
//                .build();
//
//        String sql = """
//                INSERT INTO %s (%s)
//                VALUES (%s)
//                """;
//        String tableName = Optional.ofNullable(user.getClass().getAnnotation(Table.class))
//                .map(tableAnnotation -> tableAnnotation.schema() + "." + tableAnnotation.name())
//                .orElse(user.getClass().getName());
//
//        Field[] declaredFields = user.getClass().getDeclaredFields();
//        String columnNames = Arrays.stream(declaredFields)
//                .map(field -> Optional.ofNullable(field.getAnnotation(Column.class))
//                        .map(Column::name)
//                        .orElse(field.getName()))
//                .collect(Collectors.joining(", "));
//
//        String columnValues = Arrays.stream(declaredFields)
//                .map(field -> "?")
//                .collect(Collectors.joining(", "));
//
//        System.out.println(sql.formatted(tableName, columnNames, columnValues));
//
//        Connection connection = null;
//        PreparedStatement preparedStatement = connection.prepareStatement(sql.formatted(tableName, columnNames, columnValues));
//        for (Field declaredField : declaredFields) {
//            declaredField.setAccessible(true);
//            preparedStatement.setObject(1, declaredField.get(user));
//        }
//    }
//
//    @Test
//    void oneToMany() {
//        // given
//        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
//        @Cleanup Session session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        // when
//        Company company = session.get(Company.class, 1);
////        List<User> users = company.getUsers();
//
//        // then
//        session.getTransaction().commit();
//    }
//
//    @Test
//    void addUserToCompany() {
//        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
//        @Cleanup Session session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        Company company = Company.builder()
//                .name("Uniq comp name")
//                .build();
//        User user = User.builder()
//                .personalInfo(PersonalInfo.builder()
//                        .firstname("Name")
//                        .lastname("Lastname")
//                        .birthDate(new Birthday(LocalDate.now()))
//                        .build())
//                .role(Role.USER)
//                .password("passw")
//                .build();
//
//        company.addUser(user);
//
//        session.persist(company);
//
//        session.getTransaction().commit();
//    }

    @Test
    void deleteCompany() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        Company company = session.get(Company.class, 27);
        session.remove(company);

        session.getTransaction().commit();
    }

    @Test
    void selectCompany() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        Company company = session.get(Company.class, 25);
//        session.detach(company);
//        session.merge(company);
        System.out.println(company.getUsers());

        session.getTransaction().commit();
    }

    @Test
    void checkLazyInitialization() {
        Company company = null;
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            session.beginTransaction();

            company = session.get(Company.class, 25);
//            List<User> users = company.getUsers();
//            System.out.println(users.size());
            session.getTransaction().commit();
        }
    }

    @Test
    void checkOrphanRemoval() {

        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        Company company = session.get(Company.class, 25);
        company.getUsers().remove(0); // если orphanRemoval = true, то удалит из базы User при коммите

        session.getTransaction().commit();
    }

    @Test
    void checkOneToOne() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        User user = session.get(User.class, 1L);
        System.out.println(user);

//        User user = User.builder().build();
//
//        Profile profile = Profile.builder()
//                .street("Kolasa 18")
//                .language("RU")
//                .user(user)
//                .build();
//
//        session.persist(user);
//        session.persist(profile);

        session.getTransaction().commit();
    }

//    @Test
//    void checkManyToMany() {
//        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
//        @Cleanup Session session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        User user = User.builder().build();
//        Chat chat = Chat.builder().build();
//
//        UserChat userChat = UserChat.builder()
////                .createdAt(Instant.now())
////                .createdBy(user.getUsername())
//                .build();
//
//        userChat.addUser(user);
//        userChat.addChat(chat);
//
//        session.persist(user);
//        session.persist(chat);
//        session.persist(userChat);
//
//        session.getTransaction().commit();
//    }

//    @Test
//    void testLocaleInfo() {
//        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
//        @Cleanup Session session = sessionFactory.openSession();
//        session.beginTransaction();
//
////        Company company = Company.builder()
////                .name("Uniq comp name")
////                .build();
////        session.persist(company);
////        company.getLocales().add(LocaleInfo.of("RU", "Описание на русском"));
////        company.getLocales().add(LocaleInfo.of("EN", "English description"));
//        Company company = session.get(Company.class, 1);
//        User user = User.builder().build();
//        session.persist(user);
//        company.addUser(user);
//        System.out.println(company.getLocales());
//        System.out.println(company.getUsers());
//
//        session.getTransaction().commit();
//    }

//    @Test
//    void inheritanceTablePerClass() {
//        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
//        @Cleanup Session session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        Company company = Company.builder()
//                .name("Uniq comp name")
//                .build();
//        session.persist(company);
//
//        Programmer programmer = Programmer.builder()
//                .username(UUID.randomUUID().toString())
//                .language(Language.GO)
//                .company(company)
//                .build();
//        session.persist(programmer);
//
//        Manager manager = Manager.builder()
//                .username(UUID.randomUUID().toString())
//                .projectName("project name")
//                .company(company)
//                .build();
//        session.persist(manager);
//
//        session.flush();
//        session.clear();
//
//        Programmer programmer1 = session.get(Programmer.class, 1L);
//        Manager manager1 = session.get(Manager.class, 2L);
//        User user = session.get(User.class, 2L);
//        System.out.println();
//
//        session.getTransaction().commit();
//    }

    @Test
    void hqlTest() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        Company company = new CompanyBuilder().build();
        session.persist(company);

        User user = new UserBuilder().build();
        session.persist(user);

        session.flush();
        session.clear();

        String queryString = """
                select u from User u
                 join u.company c""";
        Query<User> hqlUser = session.createQuery(queryString, User.class);
        List<User> namedUser = session.createNamedQuery("findUserByFirstName", User.class)
                .setParameter("firstname", user.getPersonalInfo().getFirstname())
                .setFlushMode(FlushModeType.COMMIT)
                .setHint(AvailableHints.HINT_FETCH_SIZE, "50")
                .list();
        System.out.println(hqlUser.getResultList());
        System.out.println(namedUser);

        int id = session.createQuery("update User u set u.role = 'USER' where id = :id")
                .setParameter("id", user.getId())
                .executeUpdate();

        List<User> nativeUsers = session.createNativeQuery("select * from users", User.class).list();

        session.getTransaction().commit();
    }
}