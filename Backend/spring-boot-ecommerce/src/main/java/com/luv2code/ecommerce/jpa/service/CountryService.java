package com.luv2code.ecommerce.jpa.service;

import com.luv2code.ecommerce.entity.Country;
import com.luv2code.ecommerce.entity.Product;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Component
public class CountryService {
    @PersistenceContext
    private EntityManager em;

    public List<Country> findAll(){

        Query query = em.createNativeQuery("SELECT * FROM country",  Country.class);

        List<Country> countries = query.getResultList();

        return  countries;
    }

    public Country findById(int theId) {

        Country theCountry = em.find(Country.class, theId);

        return theCountry;
    }


}
