package com.luv2code.ecommerce.jpa.service;

import com.luv2code.ecommerce.entity.ProductCategory;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class ProductCategoryService {


        @PersistenceContext
        private EntityManager em;

        public List<ProductCategory> findAll(){

            Query query = em.createNativeQuery("SELECT * FROM product_category",  ProductCategory.class);

            List<ProductCategory> product_categories = query.getResultList();

            return  product_categories;
        }

        public ProductCategory findById(int theId) {

            ProductCategory theProductCategory = em.find(ProductCategory.class, theId);

            return theProductCategory;
        }

        @Transactional
        public void save(ProductCategory theProductCategory) {

            ProductCategory dbProductCategory = null;

            dbProductCategory = em.merge(theProductCategory);

            // update with id from db ... so we can get generated id for save/insert
            theProductCategory.setId(dbProductCategory.getId());

        }

        @Transactional
        public void deleteById(int theId) {

            Query theQuery = em.createNativeQuery("delete from product_category where id=:productCatId");

            theQuery.setParameter("productCatId", theId);

            theQuery.executeUpdate();

        }

    }
