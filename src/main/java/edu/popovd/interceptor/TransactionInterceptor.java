package edu.popovd.interceptor;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

@RequiredArgsConstructor
public class TransactionInterceptor {

    private final SessionFactory sessionFactory;

    @RuntimeType
    public Object intercept(@SuperCall Callable<Object> call, @Origin Method method) throws Exception {
        Transaction transaction = null;
        boolean transactionStarter = false;
        if (method.isAnnotationPresent(Transactional.class)) {
            transaction = sessionFactory.getCurrentSession().getTransaction();
            if (!transaction.isActive()) {
                transaction.begin();
                transactionStarter = true;
            }
        }
        Object result;
        try {
            result = call.call();
            System.out.println("Hello from interceptor");
            if (transactionStarter) {
                transaction.commit();
            }
        } catch (Exception e) {
            if (transactionStarter) {
                transaction.rollback();
            }
            throw e;
        }

        return result;
    }

}
