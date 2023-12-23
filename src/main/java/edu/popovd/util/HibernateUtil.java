package edu.popovd.util;

import edu.popovd.converter.BirthdayConverter;
import edu.popovd.entity.Audit;
import edu.popovd.entity.User;
import edu.popovd.interceptor.GlobalInterceptor;
import edu.popovd.listener.AuditTableListener;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;

@UtilityClass
public class HibernateUtil {

    public static SessionFactory buildSessionFactory() {
        Configuration configuration = buildConfiguration();
        configuration.configure();
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        registerListeners(sessionFactory);

        return sessionFactory;
    }

    private static void registerListeners(SessionFactory sessionFactory) {
        SessionFactoryImpl sessionFactoryImpl = sessionFactory.unwrap(SessionFactoryImpl.class);
        EventListenerRegistry eventListenerRegistry = sessionFactoryImpl.getServiceRegistry().getService(EventListenerRegistry.class);
        AuditTableListener auditTableListener = new AuditTableListener();
        eventListenerRegistry.appendListeners(EventType.PRE_INSERT, auditTableListener);
        eventListenerRegistry.appendListeners(EventType.PRE_DELETE, auditTableListener);
    }

    public static Configuration buildConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        configuration.addAttributeConverter(new BirthdayConverter(), true);
        configuration.addAnnotatedClass(Audit.class);
        configuration.setInterceptor(new GlobalInterceptor());
        configuration.configure();
        return configuration;
    }
}
