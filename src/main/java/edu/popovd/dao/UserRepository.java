package edu.popovd.dao;

import edu.popovd.entity.User;
import jakarta.persistence.EntityManager;


public class UserRepository extends RepositoryBase<Long, User> {

    public UserRepository(EntityManager entityManager) {
        super(entityManager, User.class);
    }
}
