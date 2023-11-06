package com.luv2code.ecommerce.jpa.service;

import com.luv2code.ecommerce.entity.PagedData;
import com.luv2code.ecommerce.entity.Product;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class ProductService {

    @PersistenceContext
    private EntityManager em;

    public List<Product> findAll(){

        Query query = em.createNativeQuery("SELECT * FROM product",  Product.class);

        List<Product> products = query.getResultList();

        return  products;
    }

    public Product findById(int theId) {

        Product theProduct = em.find(Product.class, theId);

        return theProduct;
    }

    @Transactional
    public void save(Product theProduct) {

        Product dbProduct = em.merge(theProduct);

        // update with id from db ... so we can get generated id for save/insert
        theProduct.setId(dbProduct.getId());
    }

    @Transactional
    public void deleteById(int theId) {

        Query theQuery = em.createNativeQuery("delete from product where id=:productId");

        theQuery.setParameter("productId", theId);

        theQuery.executeUpdate();

    }

   public PagedData<Product> findByCategoryId(int theId, int page, int size){

       Query theQuery = em.createNativeQuery("SELECT * FROM product where category_id=:categoryId LIMIT :limit OFFSET :offset", Product.class);
       theQuery.setParameter("categoryId", theId);
       theQuery.setParameter("offset", (page - 1) * size);
       theQuery.setParameter("limit", size);
       List<Product> theProducts = theQuery.getResultList();
       int totalElements =  countByCategoryId(theId);
       int totalPagesSize = size;
       return  new PagedData(theProducts, page, totalPagesSize, totalElements);

   }

    public int countByCategoryId(int theId) {
        Query theQuery = em.createQuery("SELECT COUNT(p) FROM Product p where p.category.id=:categoryId");
        theQuery.setParameter("categoryId", theId);
        int count = ((Number) theQuery.getSingleResult()).intValue();
        return count;
    }

    public int countByName(String name){

        Query q = em.createNativeQuery("SELECT COUNT(*) FROM product p where p.name ILIKE CONCAT('%', :name , '%')");
        q.setParameter("name", name);
        int count = ((Number) q.getSingleResult()).intValue();
        return  count;
    }

    public PagedData<Product> findByNameContaining(String name, int page, int size) {

        Query theQuery = em.createNativeQuery("SELECT * FROM Product P WHERE p.name ILIKE CONCAT('%', :name , '%')  LIMIT :limit OFFSET :offset", Product.class);
        theQuery.setParameter("name", name);
        theQuery.setParameter("offset", (page - 1) * size);
        theQuery.setParameter("limit", size);



        List<Product> theProducts = theQuery.getResultList();
        int totalElements =  countByName(name) ;
        int totalPagesSize = size;
        System.out.println(totalElements);
        return  new PagedData(theProducts, page, totalPagesSize, totalElements);
    }









/*
    public Page<Product> findByCategoryId(int theId, Pageable pageable) {

        Query theQuery = em.createNativeQuery("SELECT * FROM product where category_id=:categoryId", Product.class);
        theQuery.setParameter("categoryId", theId);
        theQuery.setFirstResult((int)pageable.getOffset());
        theQuery.setMaxResults(pageable.getPageSize());

        List<Product> theProducte = theQuery.getResultList();

        return new PageImpl<>(theProducte, pageable, countByCategoryId(theId));
    }*/


    /* public List<Product> findByCategoryId(int theId) {

        Query theQuery = em.createNativeQuery("SELECT * FROM product where category_id=:categoryId", Product.class);
        theQuery.setParameter("categoryId", theId);

        List<Product> theProducte = theQuery.getResultList();

        return theProducte;
    }*/

     /*public List<Product> findByNameContaining(String name) {

        Query theQuery = em.createNativeQuery("SELECT * FROM product P WHERE p.name ILIKE CONCAT('%', :name , '%')", Product.class);
        theQuery.setParameter("name", name);

        List<Product> theProducts = theQuery.getResultList();

        return  theProducts;
    }*/


}
