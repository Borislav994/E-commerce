package com.luv2code.ecommerce.jpa.service;

import com.luv2code.ecommerce.entity.Order;
import com.luv2code.ecommerce.entity.PagedData;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Component
public class OrderService {

    @PersistenceContext
    private EntityManager em;

    public PagedData<Order> findByCustomerEmail(String theEmail, int page, int size){

        Query q = em.createNativeQuery("SELECT * FROM orders LEFT OUTER JOIN customer ON orders.customer_id=customer.id WHERE customer.email=:theEmail ORDER BY orders.date_created DESC LIMIT :limit OFFSET :offset", Order.class);

        q.setParameter("theEmail", theEmail);
        q.setParameter("offset", (page - 1) * size);
        q.setParameter("limit", size);

        List<Order> theOrders = q.getResultList();
        int totalElements =  countByEmail(theEmail);
        int totalPagesSize = size;
        return  new PagedData(theOrders, page, totalPagesSize, totalElements);


    }

    public int countByEmail(String theEmail) {
        Query theQuery = em.createNativeQuery("SELECT COUNT(*) FROM orders  LEFT OUTER JOIN customer  ON orders.customer_id=customer.id WHERE customer.email=:theEmail");
        theQuery.setParameter("theEmail", theEmail);
        int count = ((Number) theQuery.getSingleResult()).intValue();
        return count;
    }
}
