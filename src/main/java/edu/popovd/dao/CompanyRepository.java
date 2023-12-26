package edu.popovd.dao;

import edu.popovd.entity.Company;
import jakarta.persistence.EntityManager;


public class CompanyRepository extends RepositoryBase<Long, Company> {

    public CompanyRepository(EntityManager entityManager) {
        super(entityManager, Company.class);
    }
}
