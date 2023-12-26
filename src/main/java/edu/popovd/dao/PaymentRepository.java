package edu.popovd.dao;

import edu.popovd.entity.Payment;
import jakarta.persistence.EntityManager;


public class PaymentRepository extends RepositoryBase<Long, Payment> {

    public PaymentRepository(EntityManager entityManager) {
        super(entityManager, Payment.class);
    }
}
