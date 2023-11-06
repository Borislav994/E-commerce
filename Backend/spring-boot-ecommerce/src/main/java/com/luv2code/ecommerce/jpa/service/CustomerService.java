package com.luv2code.ecommerce.jpa.service;

import com.luv2code.ecommerce.entity.Customer;
import com.luv2code.ecommerce.entity.Product;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

@Component
public class CustomerService {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Customer theCustomer) {

        Customer dbCustomer = em.merge(theCustomer);

        // update with id from db ... so we can get generated id for save/insert
        theCustomer.setId(dbCustomer.getId());
    }

    public Customer findByEmail(String theEmail){

        Query q = em.createNativeQuery("SELECT * FROM customer  WHERE email = :theEmail", Customer.class);

        q.setParameter("theEmail", theEmail);

        try {
            Customer customer_with_email = (Customer) q.getSingleResult();
            return customer_with_email;
        } catch (NoResultException e) {
            return null;
        }
    }
}
