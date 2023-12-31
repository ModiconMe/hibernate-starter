package edu.popovd.dao;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import edu.popovd.dto.CompanyDto;
import edu.popovd.dto.PaymentFilter;
import edu.popovd.entity.*;
import org.hibernate.Session;

import java.util.List;


public class UserDao {

    private static final UserDao INSTANCE = new UserDao();

    /**
     * Возвращает всех сотрудников
     */
    public List<User> findAll(Session session) {
        /*
        return session.createQuery("select u from User u", User.class)
                .list();
        */
        /*
        HibernateCriteriaBuilder cb = session.getCriteriaBuilder();
        JpaCriteriaQuery<User> criteria = cb.createQuery(User.class);
        JpaRoot<User> user = criteria.from(User.class);
        criteria.select(user);
        return session.createQuery(criteria).list();
        */

        return new JPAQuery<User>(session)
                .select(QUser.user)
                .from(QUser.user)
                .fetch();
    }

    /**
     * Возвращает всех сотрудников с указанным именем
     */
    public List<User> findAllByFirstName(Session session, String firstName) {
//        return session.createQuery("select u from User u " +
//                                   "where u.personalInfo.firstname = :firstName", User.class)
//                .setParameter("firstName", firstName)
//                .list();
/*
        HibernateCriteriaBuilder cb = session.getCriteriaBuilder();
        JpaCriteriaQuery<User> criteria = cb.createQuery(User.class);
        JpaRoot<User> user = criteria.from(User.class);
        criteria.select(user).where(
                cb.equal(user.get(User_.personalInfo).get(PersonalInfo_.firstname), firstName)
        );
        return session.createQuery(criteria).list();
        */

        return new JPAQuery<User>(session)
                .select(QUser.user)
                .from(QUser.user)
                .where(QUser.user.personalInfo.firstname.eq(firstName))
                .fetch();

    }

    /**
     * Возвращает первые {limit} сотрудников, упорядоченных по дате рождения (в порядке возрастания)
     */
    public List<User> findLimitedUsersOrderedByBirthday(Session session, int limit) {
//        return session.createQuery("select u from User u order by u.personalInfo.birthDate", User.class)
//                .setMaxResults(limit)
////                .setFirstResult(offset)
//                .list();
        /*
        HibernateCriteriaBuilder cb = session.getCriteriaBuilder();
        JpaCriteriaQuery<User> criteria = cb.createQuery(User.class);
        JpaRoot<User> user = criteria.from(User.class);
        criteria.select(user).orderBy(cb.asc(user.get(User_.personalInfo).get(PersonalInfo_.birthDate)));
        return session.createQuery(criteria).setMaxResults(limit).list();
        */

        return new JPAQuery<User>(session)
                .select(QUser.user)
                .from(QUser.user)
                .orderBy(QUser.user.personalInfo.firstname.asc())
                .limit(limit)
                .fetch();
    }

    /**
     * Возвращает всех сотрудников компании с указанным названием
     */
    public List<User> findAllByCompanyName(Session session, String companyName) {
        /*
        return session.createQuery("""
                                   select u from Company c
                                   join c.users u
                                   where c.name = :companyName""", User.class)
                .setParameter("companyName", companyName)
                .list();
        */
        /*
        HibernateCriteriaBuilder cb = session.getCriteriaBuilder();
        JpaCriteriaQuery<User> criteria = cb.createQuery(User.class);
        JpaRoot<Company> company = criteria.from(Company.class);
        JpaMapJoin<Company, String, User> users = company.join(Company_.users);

        criteria.select(users).where(
                cb.equal(company.get(Company_.name), companyName)
        );

        return session.createQuery(criteria).list();
        */

        return new JPAQuery<User>(session)
                .select(QUser.user)
                .from(QUser.user)
                .join(QUser.user.company)
                .where(QCompany.company.name.eq(companyName))
                .fetch();
    }

    /**
     * Возвращает все выплаты, полученные сотрудниками компании с указанными именем,
     * упорядоченные по имени сотрудника, а затем по размеру выплаты
     */
    public List<Payment> findAllPaymentsByCompanyName(Session session, String companyName) {
        /*
        return session.createQuery("""
                                select p from Payment p
                                join p.receiver u
                                join u.company c
                                where c.name = :companyName
                        """, Payment.class)
                .setParameter("companyName", companyName)
                .list();
        */
/*

        HibernateCriteriaBuilder cb = session.getCriteriaBuilder();
        JpaCriteriaQuery<Payment> criteria = cb.createQuery(Payment.class);
        JpaRoot<Payment> payment = criteria.from(Payment.class);
        JpaJoin<Payment, User> users = payment.join(Payment_.receiver);
        payment.fetch(Payment_.receiver);
        JpaJoin<User, Company> company = users.join(User_.company);

        criteria.select(payment).where(
                        cb.equal(company.get(Company_.name), companyName)
                )
                .orderBy(cb.asc(users.get(User_.personalInfo).get(PersonalInfo_.firstname)),
                        cb.asc(payment.get(Payment_.amount))
                );

        return session.createQuery(criteria).list();
*/

        return new JPAQuery<Payment>(session)
                .select(QPayment.payment)
                .from(QPayment.payment)
                .join(QPayment.payment.receiver, QUser.user)
                .join(QUser.user.company, QCompany.company)
                .where(QCompany.company.name.eq(companyName))
                .fetch();
    }

    /**
     * Возвращает среднюю зарплату сотрудника с указанными именем и фамилией
     */
    public Double findAveragePaymentAmountByFirstAndLastNames(Session session, PaymentFilter filter) {
        /*
        return session.createQuery("""
                        select avg(p.amount) from Payment p
                        join p.receiver u
                        where u.personalInfo.firstname = :firstName
                        and u.personalInfo.lastname = :lastName
                        """, Double.class)
                .setParameter("firstName", firstName)
                .setParameter("lastName", lastName)
                .uniqueResult();
        */
        /*
        HibernateCriteriaBuilder cb = session.getCriteriaBuilder();
        JpaCriteriaQuery<Double> criteria = cb.createQuery(Double.class);

        JpaRoot<Payment> payment = criteria.from(Payment.class);
        JpaJoin<Payment, User> users = payment.join(Payment_.receiver);

        List<JpaPredicate> predicates = new ArrayList<>();
        if (firstName != null) {
            predicates.add(cb.equal(users.get(User_.personalInfo).get(PersonalInfo_.firstname), firstName));
        }
        if (lastName != null) {
            predicates.add(cb.equal(users.get(User_.personalInfo).get(PersonalInfo_.lastname), lastName));
        }

        criteria.select(cb.avg(payment.get(Payment_.amount))).where(
                predicates.toArray(Predicate[]::new)
        );

        return session.createQuery(criteria).uniqueResult();
        */

        Predicate predicate = QPredicate.builder()
                .add(filter.getFirstName(), QUser.user.personalInfo.firstname::eq)
                .add(filter.getLastName(), QUser.user.personalInfo.lastname::eq)
                .buildAnd();

        return new JPAQuery<Double>(session)
                .select(QPayment.payment.amount.avg())
                .from(QPayment.payment)
                .join(QPayment.payment.receiver, QUser.user)
                .where(predicate)
                .fetchOne();
    }

    /**
     * Возвращает для каждой компании: название, среднюю зарплату всех её сотрудников. Компании упорядочены по названию.
     */
    public List<com.querydsl.core.Tuple> findCompanyNamesWithAvgUserPaymentsOrderedByCompanyName(Session session) {
        /*
        return session.createQuery("""
                                   select c.name, avg(p.amount) from Company c
                                   join c.users u
                                   join u.payments p
                                   group by c.name
                                   order by c.name""", Object[].class)
                .list();
        */
        /*
        HibernateCriteriaBuilder cb = session.getCriteriaBuilder();
        JpaCriteriaQuery<CompanyDto> criteria = cb.createQuery(CompanyDto.class);
        JpaRoot<Company> company = criteria.from(Company.class);
        JpaMapJoin<Company, String, User> user = company.join(Company_.users);
        JpaListJoin<User, Payment> payment = user.join(User_.payments);

        criteria.select(
                        cb.construct(
                                CompanyDto.class,
                                company.get(Company_.name),
                                cb.avg(payment.get(Payment_.amount))
                        )
                )
                .groupBy(company.get(Company_.name))
                .orderBy(cb.asc(company.get(Company_.name)));

        return session.createQuery(criteria).list();
        */

        return new JPAQuery<CompanyDto>(session)
                .select(QCompany.company.name, QPayment.payment.amount.avg())
                .from(QUser.user)
                .join(QUser.user.payments, QPayment.payment)
                .join(QUser.user.company)
                .groupBy(QCompany.company.name)
                .orderBy(QCompany.company.name.asc())
                .fetch();
    }

    /**
     * Возвращает список: сотрудник (объект User), средний размер выплат, но только для тех сотрудников, чей средний размер выплат
     * больше среднего размера выплат всех сотрудников
     * Упорядочить по имени сотрудника
     */
    public List<Tuple> isItPossible(Session session) {
//        return session.createQuery("""
//                        select u, avg(p.amount) from User u
//                        join u.payments p
//                        group by u
//                        having avg(p.amount) > (select avg(p.amount) from Payment p)
//                        order by u.personalInfo.firstname
//                        """, Object[].class)
//                .list();
        /*
        HibernateCriteriaBuilder cb = session.getCriteriaBuilder();
        JpaCriteriaQuery<Tuple> criteria = cb.createQuery(Tuple.class);
        JpaRoot<User> user = criteria.from(User.class);
        JpaListJoin<User, Payment> payments = user.join(User_.payments);

        JpaSubQuery<Double> sq = criteria.subquery(Double.class);
        JpaRoot<Payment> paymentSq = sq.from(Payment.class);

        criteria.select(cb.tuple(user, cb.avg(payments.get(Payment_.amount))))
                .groupBy(user.get(User_.id))
                .having(cb.gt(
                        cb.avg(payments.get(Payment_.amount)),
                        sq.select(cb.avg(paymentSq.get(Payment_.amount)))))
                .orderBy(cb.asc(user.get(User_.personalInfo).get(PersonalInfo_.firstname)));

        return session.createQuery(criteria).list();
        */

        return new JPAQuery<com.querydsl.core.Tuple>(session)
                .select(QUser.user, QPayment.payment.amount.avg())
                .from(QUser.user)
                .join(QUser.user.payments, QPayment.payment)
                .groupBy(QUser.user.id)
                .having(QPayment.payment.amount.avg().gt(
                        new JPAQuery<Double>(session)
                                .select(QPayment.payment.amount.avg())
                                .from(QPayment.payment))
                )
                .orderBy(QUser.user.personalInfo.firstname.asc())
                .fetch();
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }
}
