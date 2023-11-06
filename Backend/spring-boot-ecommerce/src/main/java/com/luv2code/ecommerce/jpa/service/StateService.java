package com.luv2code.ecommerce.jpa.service;

import com.luv2code.ecommerce.entity.Country;
import com.luv2code.ecommerce.entity.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Component
public class StateService {

    @PersistenceContext
    private EntityManager em;


    public List<State> findAll(){

        Query query = em.createNativeQuery("SELECT * FROM state",  State.class);

        List<State> states = query.getResultList();

        return  states;
    }

    public State findById(int theId) {

        State theState = em.find(State.class, theId);

        return theState;
    }

    public List<State> findByCountryCode(String code) {

        Query theQuery = em.createNativeQuery("SELECT * FROM country where code=:code", Country.class);
        theQuery.setParameter("code", code);

        Country country = (Country) theQuery.getSingleResult();

         return country.getStates();

    }
}
