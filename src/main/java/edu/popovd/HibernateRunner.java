package edu.popovd;

import edu.popovd.dao.CompanyRepository;
import edu.popovd.dao.UserRepository;
import edu.popovd.dto.UserCreateDto;
import edu.popovd.dto.UserReadDto;
import edu.popovd.entity.PersonalInfo;
import edu.popovd.entity.Role;
import edu.popovd.interceptor.TransactionInterceptor;
import edu.popovd.mapper.CompanyReadMapper;
import edu.popovd.mapper.UserCreateMapper;
import edu.popovd.mapper.UserReadMapper;
import edu.popovd.service.UserService;
import edu.popovd.util.HibernateUtil;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.Optional;

public class HibernateRunner {

    private static final Logger log = LoggerFactory.getLogger(HibernateRunner.class);

    public static void main(String[] args) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {

            Session session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                    (o, method, objects) -> method.invoke(sessionFactory.getCurrentSession(), objects));

            CompanyRepository companyRepository = new CompanyRepository(session);
            UserRepository userRepository = new UserRepository(session);
            CompanyReadMapper companyReadMapper = new CompanyReadMapper();
            UserReadMapper userReadMapper = new UserReadMapper(companyReadMapper);
            UserCreateMapper userCreateMapper = new UserCreateMapper(companyRepository);
//            UserService userService = new UserService(userRepository, userReadMapper, userCreateMapper);

            UserService userService = new ByteBuddy()
                    .subclass(UserService.class)
                    .method(ElementMatchers.any())
                    .intercept(MethodDelegation.to(new TransactionInterceptor(sessionFactory)))
                    .make()
                    .load(UserService.class.getClassLoader())
                    .getLoaded()
                    .getDeclaredConstructor(UserRepository.class, UserReadMapper.class, UserCreateMapper.class)
                    .newInstance(userRepository, userReadMapper, userCreateMapper);

//            session.beginTransaction();

            Optional<UserReadDto> byId = userService.findById(1L);
            System.out.println(byId);

            userService.create(new UserCreateDto(new PersonalInfo("Sergei", "Ivanov", /*new Birthday(LocalDate.now()))*/null), "Serega226", "{\"who\": \"me\"}", Role.USER, 1L));

//            session.getTransaction().commit();
        }
    }
}
